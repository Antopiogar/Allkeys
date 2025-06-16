package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrdineDAO {

	
	public OrdineDAO() {
	}
	
	
	public static synchronized boolean CreateOrder(ArrayList<ArticoliCarrello> articoli, BeanUtente user) {
		String query = "{call createOrdine(?)}";
	    int idOrder = 0;
	    boolean controlloErroriComposizione = false;
	    Connection con = null;
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
	        	DBConnection.releseConnection(con);
	        	return false;
	        }
	        
	        DBConnection.releseConnection(con);
	        return true;

	    } catch (SQLException e) {
	    	System.out.println("MORTO IN ORDINE");
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public static synchronized Carrello LoadCarrelByUser(int idUser){
		Carrello c = new Carrello();
		Connection con = null;
		try {
			int idOrdine = -1;

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
	
		Connection con = null;
		String query = """
				UPDATE 
					Ordine
				Set conferma = True, fkCarta= ?, dataAcquisto = now(), fattura = ?
				where
				idOrdine = ?
					
				""";
	    con = DBConnection.getConnection();
		int idOrder = getIdCarrello(IdUtente);
	    boolean risultato = false;
	    int risultatoUpdateChiavi = -2;
	    
	    
	    try {
	    	
	    	risultatoUpdateChiavi = ChiaveDAO.confermaChiaviOrdinate(con, IdUtente, idOrder);
			System.out.println("risultato chiavi " + risultatoUpdateChiavi);

    		if(risultatoUpdateChiavi != 0) {
    			con.rollback();
    			DBConnection.releseConnection(con);
    			return risultatoUpdateChiavi;
    		}
	    	
	    	
	    	PreparedStatement ps = con.prepareStatement(query);
	    	ps.setInt(1, IdCartaPagamento);
	    	ps.setString(2,"fattura"+idOrder+".pdf");
	    	ps.setInt(3, idOrder);
	    	
	    	risultato = ps.executeUpdate() == 1 ? true : false;
	    	if(!risultato) {
	    		ps.close();
	    		DBConnection.releseConnection(con);
	    		return -1;
	    	}
	    	ps.close();
			
    		System.out.println("QUI VA");
    		
	        con.commit();
	        System.out.println("COMMIT?");
    		
	        return 0;

	    } catch (SQLException e) {
	    	System.out.println("MORTO IN ORDINE");
	        e.printStackTrace();
	    } finally {
	        DBConnection.releseConnection(con);
	    }
	    return -1;
	}
	
	public static synchronized boolean addArticoloToCarrelloDB(int idUser,ArticoliCarrello art) {
		System.out.println("ARRIVA QUI");
		Connection con = null;
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
	

	
	
	public static synchronized ArrayList<Acquisto> loadOrdersByIdUserAndTime(int idUtente,LocalDateTime t1, LocalDateTime t2){
		ArrayList<Acquisto> acquisti = new ArrayList<>();
		Connection con = DBConnection.getConnection();
		try {
			String query="""
					SELECT DISTINCT 
    o.idOrdine,
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
FROM 
    ordine as o
    JOIN chiave as c ON c.FkOrdine = o.idOrdine
    JOIN articolo as a ON a.idArticolo = c.FkArticolo
    JOIN composizione as co ON co.FkOrdine = o.idOrdine
    JOIN carta_pagamento as cp ON cp.idCarta = o.fkCarta
WHERE 
    o.fkUtente = ? 
    AND o.conferma = 1 
    AND a.idArticolo = co.FkArticolo
    AND o.dataAcquisto BETWEEN ? AND ?""";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idUtente);
			if(t1.isBefore(t2)) {
				ps.setTimestamp(2, Timestamp.valueOf(t1));
				ps.setTimestamp(3, Timestamp.valueOf(t2));
			}
			else {
				ps.setTimestamp(3, Timestamp.valueOf(t1));
				ps.setTimestamp(2, Timestamp.valueOf(t2));
			}
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
	
	public static synchronized ArrayList<Acquisto> loadOrdersByTime(LocalDateTime t1, LocalDateTime t2){
		ArrayList<Acquisto>	acquisti = new ArrayList<Acquisto>();
		Connection con = DBConnection.getConnection();
		try {
			String query="""
					SELECT DISTINCT 
	o.fkUtente as idUtente,
    o.idOrdine,
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
FROM 
    ordine as o
    JOIN chiave as c ON c.FkOrdine = o.idOrdine
    JOIN articolo as a ON a.idArticolo = c.FkArticolo
    JOIN composizione as co ON co.FkOrdine = o.idOrdine
    JOIN carta_pagamento as cp ON cp.idCarta = o.fkCarta
WHERE 
    o.conferma = 1 
    AND a.idArticolo = co.FkArticolo
    AND o.dataAcquisto BETWEEN ? AND ?
    
 Order by idOrdine asc""";
			PreparedStatement ps = con.prepareStatement(query);
			if(t1.isBefore(t2)) {
				ps.setTimestamp(1, Timestamp.valueOf(t1));
				ps.setTimestamp(2, Timestamp.valueOf(t2));
			}
			else {
				ps.setTimestamp(2, Timestamp.valueOf(t1));
				ps.setTimestamp(1, Timestamp.valueOf(t2));
			}
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
	        		int idUtente = rs.getInt("idUtente");
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
	
	public static synchronized ArrayList<Acquisto> loadAllOrdersByIdUtente(int idUtente){
		ArrayList<Acquisto> acquisti = new ArrayList<>();
		Connection con = DBConnection.getConnection();
		try {
			String query="""
					select distinct 
					    o.idOrdine ,
					    o.dataAcquisto,
					    o.fattura as fattura,
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
			        ordine.setFattura(rs.getString("fattura"));
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


	public static synchronized Acquisto loadOrderByIdOrder(int idOrdine) {
		ArrayList<Acquisto> acquisti = new ArrayList<>();
		Connection con = DBConnection.getConnection();
		try {
			String query="""
					select distinct 
						o.fkUtente as idUtente,
					    o.idOrdine ,
					    o.dataAcquisto,
					    o.fattura as fattura,
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
					where o.idOrdine = ? and o.conferma = 1 and a.idArticolo = co.FkArticolo
					""";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idOrdine);
			ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
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
			        Acquisto ac = new Acquisto(rs.getInt("idUtente"));
			        BeanCartaPagamento carta = new BeanCartaPagamento();
			        carta.setIdCarta(rs.getInt("idCarta"));
			        carta.setnCarta(rs.getString("nCarta"));
			        carta.setTitolare(rs.getString("titolare"));
			        
			        
			        BeanOrdine ordine = new BeanOrdine();
			        ordine.setConferma(true);
			        ordine.setDataAcquisto(rs.getTimestamp("dataAcquisto").toLocalDateTime());
			        ordine.setIdOrdine(rs.getInt("idOrdine"));
			        ordine.setPagamento(carta);
			        ordine.setFattura(rs.getString("fattura"));
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
		return acquisti.get(0);
	}
	
}