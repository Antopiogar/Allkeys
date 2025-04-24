package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class ComposizioneDAO {

	
	public static synchronized boolean CreateComposizione(Connection con,ArrayList<ArticoliCarrello> articoli, int FkOrdine) {
		try {
			int risultatoInserimento;
			String insertQuery = """
				    INSERT INTO Composizione (prezzoPagato,FkArticolo,FKOrdine) 
				    VALUE((select a.prezzo from articolo as a where idArticolo = ?),?,?);
				""";
			for(ArticoliCarrello ac : articoli) {
				PreparedStatement ps = con.prepareStatement(insertQuery);
				ps.setInt(1, ac.getArticolo().getIdArticolo());
				ps.setInt(2, ac.getArticolo().getIdArticolo());
				ps.setInt(3, FkOrdine);
				risultatoInserimento = ps.executeUpdate();
				if(risultatoInserimento != 1) {
		        	con.rollback();
				}
				
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
}