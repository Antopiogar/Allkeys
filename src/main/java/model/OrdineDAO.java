package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			System.out.println("ARRIVA?");

			con = DBConnection.getConnection();
			idOrdine = getIdCarrello(idUser);
			
			String query = """
					SELECT 
					    a.idArticolo,
					    a.logo,
					    a.nome,
					    a.piattaforma, 
					    a.prezzo,
					    c.qta
					FROM composizione AS c
					JOIN articolo AS a ON c.FkArticolo = a.idArticolo 
					WHERE c.FkOrdine = ?
					GROUP BY 
					    a.idArticolo, a.logo, a.nome, a.piattaforma, a.prezzo, c.qta
					""";
			PreparedStatement ps;
			ResultSet rs;
			BeanArticolo art;
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
				System.out.println(art);
				c.AddArticolo(art);
				c.setQta(art, rs.getInt("qta"));
			}
		}catch (Exception e) {
			e.printStackTrace();
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
				Set conferma = True, fkCarta= ?, dataAcquisto = now()
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
	

	
	
	public static synchronized ArrayList<Acquisto> loadAllOrdersByIdUtente(int idUtente){
		ArrayList<Acquisto> acquisti = new ArrayList<>();
		Connection con = DBConnection.getConnection();
		try {
			String query="""
					select distinct 
					    o.idOrdine ,
					    o.dataAcquisto,
					    a.idArticolo as idArticolo,
					    a.logo as logo,
					    a.nome as nome,
					    a.piattaforma,
					    co.prezzoPagato as prezzoPagato,
					    c.codice,
					    c.idChiave,
					    cp.idCarta,
					    cp.numeroCarta as nCarta,
					    cp.titolare
					    
					from 
						ordine as o
					    	join chiave as c on c.FkOrdine = o.idOrdine
					        join articolo as a on a.idArticolo = c.FkArticolo
					        join composizione as co on co.FkOrdine = o.idOrdine
							join carta_pagamento as cp on cp.idCarta = o.fkCarta
					where o.fkUtente = ? and o.conferma = 1 and a.idArticolo = co.FkArticolo
					""";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idUtente);
			ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	int idOrdine = rs.getInt("idOrdine");
	        	int risRicerca = -1;
	        	//Carica articolo dal DB
	        	BeanArticolo art = new BeanArticolo();
	        	art.setIdArticolo(rs.getInt("idArticolo"));
	        	art.setLogo(rs.getString("logo"));
	        	art.setNome(rs.getString("nome"));
	        	art.setPiattaforma(rs.getString("piattaforma"));
	        	art.setPrezzo(rs.getFloat("prezzoPagato"));
	        	
	        	//Carica chiave dal DB
	        	BeanChiave chiave = new BeanChiave();
	        	chiave.setCodice(rs.getString("codice"));
	        	chiave.setIdChiave(rs.getInt("idChiave"));
	        	chiave.setFkArticolo(art);
	        	
	        	//se la chiave fa parte di un acquisto gia inizializzato la aggiunge
	        	risRicerca = Acquisto.existsOrder(acquisti, idOrdine);
	        	if(risRicerca>=0) {
	    			acquisti.get(risRicerca).AddProdottto(art, chiave);

	        	}
	        	//altrimenti inizializza un nuovo acquisto con all'interno il prodotto
	        	else {
			        Acquisto ac = new Acquisto(idUtente);
			        BeanCartaPagamento carta = new BeanCartaPagamento();
			        carta.setIdCarta(rs.getInt("idCarta"));
			        carta.setnCarta(rs.getString("nCarta"));
			        carta.setTitolare(rs.getString("titolare"));
			        
			        
			        BeanOrdine ordine = new BeanOrdine();
			        ordine.setConferma(true);
			        ordine.setDataAcquisto(rs.getTimestamp("dataAcquisto").toLocalDateTime());
			        ordine.setIdOrdine(rs.getInt("idOrdine"));
			        ordine.setPagamento(carta);
			        
			        ac.setOrdine(ordine);
			        ac.setCarta(carta);
			        ac.AddProdottto(art, chiave);
			        acquisti.add(ac);
	        	}
		        

	        }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MORTO LOAD ALL ORDERS BY ID UTENTE");
		}
		DBConnection.releseConnection(con);
		return acquisti;
	}
	
	/*
	public static synchronized Acquisto loadOrderByIdOrder(int idOrdine){
		Acquisto acquisto = null;

		Connection con = DBConnection.getConnection();
		try {
			String query="""
					SELECT *
					FROM 
						Chiave as c
					    join Articolo as a on c.fkArticolo = a.idArticolo
					    join Ordine as o on o.IdOrdine = c.fkOrdine
					    join Utente as u on u.IdUtente = o.fkUtente
					    join carta_pagamento as cp on o.fkCarta = cp.idCarta
					where o.idOrdine = ?
					""";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idOrdine);
			ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	        	acquisto = new Acquisto();
	            BeanUtente utente = new BeanUtente();
	            utente.setIdUtente(rs.getInt(15));
	            utente.setNome(rs.getString(16));
	            utente.setCognome(rs.getString(17));
	            utente.setDataNascita(rs.getDate(18).toLocalDate());
	            utente.setEmail(rs.getString(19));
	            utente.setCf(rs.getString(20));
	            utente.setPass(rs.getString(21));

	            BeanCartaPagamento carta = new BeanCartaPagamento();
	            carta.setIdCarta(rs.getInt(22));
	            carta.setTitolare(rs.getString(23));
	            carta.setnCarta(rs.getString(24));
	            carta.setScadenza(rs.getDate(25).toLocalDate());
	            carta.setCodiceCVC(rs.getString(26));
	            carta.setFkUtente(utente);

	            BeanOrdine ordine = new BeanOrdine();
	            ordine.setIdOrdine(rs.getInt(10));
	            ordine.setDataAcquisto(rs.getTimestamp(11).toLocalDateTime());
	            ordine.setConferma(rs.getInt(12) == 1);
	            ordine.setUtente(utente);
	            ordine.setPagamento(carta);

	            BeanArticolo articolo = new BeanArticolo();
	            articolo.setIdArticolo(rs.getInt(5));
	            articolo.setLogo(rs.getString(6));
	            articolo.setNome(rs.getString(7));
	            articolo.setPrezzo(rs.getFloat(8));
	            articolo.setPiattaforma(rs.getString(9));

	            BeanChiave chiave = new BeanChiave();
	            chiave.setIdChiave(rs.getInt(1));
	            chiave.setCodice(rs.getString(2));
	            chiave.setFkOrdine(ordine);
	            chiave.setFkArticolo(articolo);

	            acquisto.setUtente(utente);
	            acquisto.setCarta(carta);
	            acquisto.setOrdine(ordine);
	            acquisto.AddProdottto(articolo, chiave);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MORTO IN LOAD CARTE");
		}
		DBConnection.releseConnection(con);
		return acquisto;
	}
	*/
	
}