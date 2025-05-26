package model;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/AllKeys";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USER);
            config.setPassword(PASSWORD);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.setAutoCommit(false);
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(30000);
            config.setMaxLifetime(1800000);

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            System.err.println("❌ Errore durante l'inizializzazione di HikariCP:");
            e.printStackTrace();
        }
    }

    public static synchronized Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("❌ Errore ottenendo la connessione dal pool:");
            e.printStackTrace();
            return null;
        }
    }

    public static synchronized void releseConnection(Connection con) {
        if (con != null) {
            try {
                con.close(); // Torna automaticamente al pool
            } catch (SQLException e) {
                System.err.println("❌ Errore nel rilascio della connessione:");
                e.printStackTrace();
            }
        }
    }
}
