package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/AllKeys"; // Modifica con il tuo database
    private static final String USER = "root"; // Modifica con il tuo username
    private static final String PASSWORD = "root"; // Modifica con la tua password
    
 // Oggetto statico per la connessione
    private static ArrayList<Connection> connectionList = new ArrayList<Connection>();
    

    // Metodo per creare la connessione
    private static Connection createConnection() throws SQLException{
		Connection nuovaConnessione = null;

    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver"); 
        	nuovaConnessione = DriverManager.getConnection(URL,USER,PASSWORD);
        	nuovaConnessione.setAutoCommit(false);
        	
            
        } 
		catch (ClassNotFoundException e) {
            System.err.println("❌ Driver JDBC non trovato!");
            e.printStackTrace();
        }
    	return nuovaConnessione;
    }
    
    public static synchronized Connection getConnection(){
    	Connection con = null;
    	try {
    		if(!connectionList.isEmpty()) {
    			con = (Connection) DBConnection.connectionList.getFirst();
    			DBConnection.connectionList.remove(con);
    		}
    		else {
    			
    			con = createConnection();
    		}
    	}
    	catch (Exception e) {
    	    System.err.println("Errore nella creazione della connessione:");
    		e.printStackTrace();
		}
    	return con;
		
	}
    // Metodo per chiudere la connessione
    public static synchronized void releseConnection(Connection con) {
        if (con != null) {
            connectionList.add(con);
        }
    }
}