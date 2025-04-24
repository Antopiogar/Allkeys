package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class OrdineDAO {

	
private static Connection con;
	
	public OrdineDAO() {
	}
	
	//NON TESTATO
	public static synchronized boolean CreateOrder(ArrayList<ArticoliCarrello> articoli, BeanUtente user) {
		String query = "{call CreazioneOrdine(?,?)}";
	    int idOrder = 0;
	    boolean controlloErroriComposizione = false;
	    try {
			con=DBConnection.getConnection();

	        CallableStatement cs = con.prepareCall(query);
	        cs.setInt(1, user.getIdUtente());
	        cs.registerOutParameter(2, Types.INTEGER);

	        cs.execute();
	        idOrder = cs.getInt(2);
	        controlloErroriComposizione = ComposizioneDAO.CreateComposizione(con, articoli, idOrder);
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
	
	
}