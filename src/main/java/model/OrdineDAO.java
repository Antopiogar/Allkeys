package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class OrdineDAO {

	
private static Connection con;
	
	public OrdineDAO() {
	}
	
	
	public static synchronized boolean CreateOrder(ArrayList<ArticoliCarrello> articoli, BeanUtente user) {
		String query = "{call createOrdine(?)}";
	    int idOrder = 0;
	    boolean controlloErroriComposizione = false;
	    try {
			con=DBConnection.getConnection();

	        CallableStatement cs = con.prepareCall(query);
	        cs.setInt(1, user.getIdUtente());
	        ResultSet rs = cs.executeQuery();
	        if(rs.next()) {
		        idOrder = rs.getInt("last_Id");

	        }
	        controlloErroriComposizione = ComposizioneDAO.AggiungiProdotti(con, articoli, idOrder,user.getIdUtente());
	        if(controlloErroriComposizione) {
	        	return false;
	        }
	        
	        con.commit();
	        return true;

	    } catch (SQLException e) {
	    	System.out.println("MORTO IN ORDINE");
	        e.printStackTrace();
	        try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
	    } finally {
	        DBConnection.releseConnection(con);
	    }
	    return false;
	}
	
	public static synchronized Carrello LoadCarrelByUser(int idUser){
		Carrello c = new Carrello();
		
		try {
			int idOrdine = -1;
			con = DBConnection.getConnection();
			String query ="SELECT o.idOrdine from Ordine as o where o.conferma = false and o.fkUtente = ? limit 1";
			PreparedStatement ps = con.prepareStatement(query);
			BeanArticolo art;
			ps.setInt(1, idUser);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				idOrdine = rs.getInt("idOrdine");
			}
			if(idOrdine == -1) {
				return null;
			}
			
			query = """
					SELECT 
					    a.idArticolo,
					    a.logo,
					    a.nome,
					    a.piattaforma, 
					    a.prezzo,
					    c.qta 
					FROM composizione AS c
					JOIN articolo AS a ON c.FkArticolo = a.idArticolo 
					where c.FkOrdine = ?
					GROUP BY 
					    a.idArticolo, a.logo, a.nome, a.piattaforma, a.prezzo
					""";
			ps = con.prepareStatement(query);
			ps.setInt(1, idOrdine);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				art = new BeanArticolo();
				art.setIdArticolo(rs.getInt("idArticolo"));
				art.setLogo(rs.getString("logo"));
				art.setNome(rs.getString("nome"));
				art.setPiattaforma(rs.getString("piattaforma"));
				art.setPrezzo(rs.getFloat("prezzo"));
				c.AddArticolo(art);
				c.setQta(art, rs.getInt("qta"));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
	        DBConnection.releseConnection(con);
	    }
		
		return c;
		
	}
	
	
	//SE VA A BUON FINE RITORNA 0
	//SE MANCANO PRODOTTI SUL DB RITORNA ID DI QUEL PRODOTTO
	//SE CI SONO ALTRI ERRORI RITORNA -1
	public static synchronized int ConfirmOrder(int IdUtente, int IdCartaPagamento) {
		String query = """
				UPDATE 
					Ordine
				Set conferma = True, fkCarta= ?
				where
				idOrdine = ?
					
				""";
	    con = DBConnection.getConnection();
		int idOrder = getIdCarrello(IdUtente);
	    boolean risultato = false;
	    int risultatoUpdateChiavi = -2;
	    try {
	    	PreparedStatement ps = con.prepareStatement(query);
	    	ps.setInt(2, idOrder);
	    	ps.setInt(1, IdCartaPagamento);
	    	risultato = ps.executeUpdate() == 1 ? true : false;
	    	if(risultato) {
	    		System.out.println("QUI VA");
	    		risultatoUpdateChiavi = ChiaveDAO.confermaChiaviOrdinate(con, IdUtente, idOrder);
	    		System.out.println("risultatoUpdateChiavi"+risultatoUpdateChiavi);
	    		if(risultatoUpdateChiavi != 0) {
	    			return risultatoUpdateChiavi;
	    		}
	    	}
	    	ps.close();
			
    		System.out.println("QUI VA");
    		
	        con.commit();
	        System.out.println("COMMIT?");
    		
	        return 0;

	    } catch (SQLException e) {
	    	System.out.println("MORTO IN ORDINE");
	        e.printStackTrace();
	        try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
	    } finally {
	        DBConnection.releseConnection(con);
	    }
	    return -1;
	}
	
	public static synchronized boolean addArticoloToCarrelloDB(int idUser,ArticoliCarrello art) {
		System.out.println("ARRIVA QUI");

		try {

			int idOrdine = -1,risultatoInserimento=-1;
			con = DBConnection.getConnection();
			String query ="SELECT o.idOrdine from Ordine as o where o.conferma = false and o.fkUtente = ? limit 1";
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idUser);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				idOrdine = rs.getInt("idOrdine");
				System.out.println("idOrdine add articolo" + idOrdine);
			}
			else {
				CreateOrder(null, UtenteDAO.loadUserById(idUser));
				System.out.println("entra qui");
			}
			System.out.println("ARRIVA QUI");

			if(idOrdine == -1) {
				return false;
			}
			query = """
					Insert into 
						composizione (prezzoPagato, qta,FkArticolo,FkOrdine) 
						value(
							(Select prezzo from Articolo where idArticolo =?),
							?,?,?
						);
					""";
			ps = con.prepareStatement(query);
			ps.setInt(1, art.getArticolo().getIdArticolo());
			ps.setInt(2, art.getQta());
			ps.setInt(3, art.getArticolo().getIdArticolo());
			ps.setInt(4, idOrdine);
			risultatoInserimento = ps.executeUpdate();
			if(risultatoInserimento == 1) {
				con.commit();
				DBConnection.releseConnection(con);
				return true;
			}
			ps.close();

		}catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			System.out.println("BOOOOM");

		}finally {
			DBConnection.releseConnection(con);
		}
		
		return false;
	}


	public static synchronized int getIdCarrello(int idUser) {
		int idOrdine = 0;
		Connection con = DBConnection.getConnection();
		String query ="SELECT o.idOrdine from Ordine as o where o.conferma = false and o.fkUtente = ? limit 1";
		try {
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setInt(1, idUser);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next()) {
	        	idOrdine = rs.getInt("idOrdine");
	        }
	        //se non ha ancora il carrello DB lo crea e restituisce quello
	        if(idOrdine == 0){
	        	CreateOrder(null, UtenteDAO.loadUserById(idUser));
	        	idOrdine = getIdCarrello(idUser);
	        }
	        ps.close();
	        rs.close();
	    } catch (SQLException e) {
	    	System.out.println("MORTO IN OTTIENI ID CARRELLO");
	        e.printStackTrace();

	    } finally {
	        DBConnection.releseConnection(con);
	    }
		return idOrdine;
	}
	
	//CONTROLLARE
	public static synchronized ArrayList<BeanOrdine> loadAllOrdersByIdUtente(int idUtente){
		ArrayList<BeanOrdine> ordini = new ArrayList<BeanOrdine>();
		Connection con = DBConnection.getConnection();
		try {
			BeanOrdine ordine;
			String query="""
					SELECT *
					FROM Ordine
					WHERE
						FkUtente = ? and
						conferma = True
					""";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idUtente);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ordine = new BeanOrdine();
				ordine.setConferma(true);
				ordine.setDataAcquisto(rs.getTimestamp("dataAcquisto").toLocalDateTime());
				ordine.setIdOrdine(rs.getInt("IdOrdine"));
				ordine.setPagamento(CartaPagamentoDAO.loadCartaById(rs.getInt("fkCarta")));
				ordini.add(ordine);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MORTO IN LOAD CARTE");
		}
		DBConnection.releseConnection(con);
		return ordini;
	}
	
	public static synchronized ArrayList<Acquisto> loadAllOrders(int idUtente){
		return null;
	}
	
	public static synchronized Acquisto loadOrderByIdOrder(int idOrder) {
		Connection con = DBConnection.getConnection();
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MORTO IN CARICA ORDINE BY ID");
		}finally {
			DBConnection.releseConnection(con);
		}
		return null;
	}
	
}