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
	
	//BOMBA!
	public static synchronized boolean ConfirmOrder(int IdUtente, int IdCartaPagamento) {
		String query = "SELECT o.idOrdine from Ordine as o where o.conferma = false and o.fkUtente = ? limit 1";
	    int idOrder = 0;
	    boolean controlloErroriComposizione = false;
	    try {
			con=DBConnection.getConnection();

	        CallableStatement cs = con.prepareCall(query);
	        //cs.setInt(1, user.getIdUtente());
	        cs.registerOutParameter(2, Types.INTEGER);

	        cs.execute();
	        idOrder = cs.getInt(2);
	        //controlloErroriComposizione = ComposizioneDAO.CreateComposizione(con, articoli, idOrder);
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
	
	public static synchronized boolean addArticoloToCarrelloDB(int idUser,ArticoliCarrello art) {
		
		try {
			int idOrdine = -1,risultatoInserimento=-1;
			con = DBConnection.getConnection();
			String query ="SELECT o.idOrdine from Ordine as o where o.conferma = false and o.fkUtente = ? limit 1";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idUser);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				idOrdine = rs.getInt("idOrdine");
			}
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
			
		}catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
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
	    } catch (SQLException e) {
	    	System.out.println("MORTO IN OTTIENI ID CARRELLO");
	        e.printStackTrace();

	    } finally {
	        DBConnection.releseConnection(con);
	    }
		return idOrdine;
	}
	
	
}