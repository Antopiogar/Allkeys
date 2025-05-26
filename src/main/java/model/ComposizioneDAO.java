package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class ComposizioneDAO {

	
	public static synchronized boolean AggiungiProdotti(Connection con,ArrayList<ArticoliCarrello> articoli, int FkOrdine, int fkUtente) {
		try {
			int risultatoInserimento;
			String insertQuery = """
				    INSERT INTO Composizione (prezzoPagato,qta,FkArticolo,FKOrdine) 
				    VALUE((select a.prezzo from articolo as a where idArticolo = ?),?,?,?);
				""";
			String updateQuery = """
				    UPDATE Composizione
					SET qta = ?
					WHERE fkArticolo = ? and fkOrdine = ?;
				""";
			Carrello DB = OrdineDAO.LoadCarrelByUser(fkUtente);
	
			if(articoli != null) {
				for(ArticoliCarrello ac : articoli) {
					//se non cè nel carrello lo inserisce
					if(Carrello.isInCarrello(DB,ac.getArticolo())==-1) {
						PreparedStatement ps = con.prepareStatement(insertQuery);
						ps.setInt(1, ac.getArticolo().getIdArticolo());
						ps.setInt(2, ac.getQta());
						ps.setInt(3, ac.getArticolo().getIdArticolo());
						ps.setInt(4, FkOrdine);
						risultatoInserimento = ps.executeUpdate();
						if(risultatoInserimento != 1) {
							System.out.println("ERRORE IN INSERIMENTO");
				        	con.rollback();
						}
					}
					//se c'è aggiunge quantita
					else {
						int newQta = (DB.getArticoloById(ac.getArticolo().getIdArticolo()).getQta())+1;
						PreparedStatement ps = con.prepareStatement(updateQuery);
						ps.setInt(1, newQta);
						ps.setInt(2, ac.getArticolo().getIdArticolo());
						ps.setInt(3, FkOrdine);
						risultatoInserimento = ps.executeUpdate();
						if(risultatoInserimento != 1) {
				        	con.rollback();
						}

					}

					
					
				}
				con.commit();

			}

		}
	     catch (SQLException e) {
	        e.printStackTrace();
	        try {
	        	con.rollback(); 
	        	} 
	        catch (SQLException ex) {
	        	ex.printStackTrace();
	        	System.out.println("MORTO IN COMPOSIZIONE");
	        }
	    } finally {
	        DBConnection.releseConnection(con);
	    }
	    return false;
	}
	
	public static synchronized boolean cambiaQuantita(int idOrdine, ArticoliCarrello art) {
		Connection con = DBConnection.getConnection();

		try {
			int risultatoInserimento;
			String insertQuery = """
				    UPDATE composizione
					SET qta = ?
					WHERE fkArticolo = ? and fkOrdine = ?;
				""";
			
			PreparedStatement ps = con.prepareStatement(insertQuery);
			ps.setInt(1, art.getQta());
			ps.setInt(2, art.getArticolo().getIdArticolo());
			ps.setInt(3, idOrdine);
			risultatoInserimento = ps.executeUpdate();
			if(risultatoInserimento != 1) {
	        	con.rollback();
			}
			con.commit();
		}
	    catch (SQLException e) {
	        e.printStackTrace();
	        try {
	        	con.rollback(); 
	        	} 
	        catch (SQLException ex) {
	        	ex.printStackTrace();
	        	System.out.println("MORTO IN UPDATE");
	        }
	    } finally {
	        DBConnection.releseConnection(con);
	    }
	    return false;
	}

	public static synchronized boolean removeArticolo(int idOrdine,BeanArticolo art) {
		Connection con = DBConnection.getConnection();

		try {
			int risultatoInserimento;
			String insertQuery = """
				   DELETE FROM Composizione WHERE fkOrdine = ? and fkArticolo = ? ;
				""";
			
			PreparedStatement ps = con.prepareStatement(insertQuery);
			ps.setInt(1, idOrdine);
			ps.setInt(2, art.getIdArticolo());
			risultatoInserimento = ps.executeUpdate();
			if(risultatoInserimento != 1) {
	        	con.rollback();
			}
			con.commit();
			

		}
	     catch (SQLException e) {
	        e.printStackTrace();
	        try {
	        	con.rollback(); 
	        	} 
	        catch (SQLException ex) {
	        	ex.printStackTrace();
	        	System.out.println("MORTO IN RIMUOVI");
	        }
	    } finally {
	        DBConnection.releseConnection(con);
	    }
	    return false;
	}
	
	/*
	 questo metodo prende il nuovo carrello formato dal merge di quello Sessione e quello DB 
	 e sincronizza il DB con le modifiche apportate prima del login
	*/
	public static synchronized boolean SincronizzaCarrelli(int idUtente, Carrello newCarrello) {
		boolean ris = true;
		Carrello old = OrdineDAO.LoadCarrelByUser(idUtente);
		int idOrdine = OrdineDAO.getIdCarrello(idUtente);
		//METODI DA CONTROLLARE
		Carrello newProductToSave = Carrello.differenzaCarrelli(newCarrello,old);
		Carrello productToUpdate = Carrello.intersezioneConQuantita(old, newProductToSave);
		Connection con = DBConnection.getConnection();
		ris = AggiungiProdotti(con,newProductToSave.getArticoli(),idOrdine,idUtente);
		DBConnection.releseConnection(con);
		for (ArticoliCarrello art : productToUpdate.getArticoli()) {
			if(!ris) {
				return ris;
			}
			ris = cambiaQuantita(idOrdine, art);
		}
		return ris;
	}


}