package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/AllKeys"; // Modifica con il tuo database
    private static final String USER = "root"; // Modifica con il tuo username
    private static final String PASSWORD = "root"; // Modifica con la tua password
    
 // Oggetto statico per la connessione
    private static Connection conn = null;

    // Metodo per ottenere la connessione
    public static synchronized Connection getConnection() {
        if (conn == null) { // Crea la connessione solo se non esiste già
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                System.err.println("❌ Driver JDBC non trovato!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("❌ Errore di connessione al database!");
                e.printStackTrace();
            }
        }
        return conn;
    }

    // Metodo per chiudere la connessione
    public static synchronized void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null; // Reset della connessione
            } catch (SQLException e) {
                System.err.println("❌ Errore durante la chiusura della connessione!");
                e.printStackTrace();
            }
        }
    }
}
