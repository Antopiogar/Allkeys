package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteDAO {

	
	public UtenteDAO() {
	}
	
	public static synchronized boolean checkEmailOnDB(String email) {
		boolean ris = false;
		Connection con = DBConnection.getConnection();
		String query = "SELECT email FROM UTENTE WHERE EMAIL = ?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getString("email").equalsIgnoreCase(email)) {
					ris = true;
				}
			}
			ps.close();
			rs.close();
		}catch (Exception e) {	
		}
		
		DBConnection.releseConnection(con);
		return ris;
	}
	
	public static synchronized ArrayList<BeanUtente> getUsers(){
		ArrayList<BeanUtente> users = new ArrayList<BeanUtente>();
		Connection con = DBConnection.getConnection();
		String query = "SELECT * FROM utente";
		ResultSet rs = null;
		try {
			PreparedStatement ps = con.prepareStatement(query);
			rs=ps.executeQuery();
			while(rs.next()) {
				BeanUtente utente = new BeanUtente();
				utente.setNome(rs.getString("nome"));
				utente.setCf(rs.getString("cf"));
				utente.setDataNascita(rs.getDate("dataNascita").toLocalDate());
				utente.setEmail(rs.getString("email"));
				utente.setIdUtente(rs.getInt("IdUtente"));
				utente.setCognome(rs.getString("cognome"));
				utente.setPass(rs.getString("password"));
				utente.setIsAdmin(rs.getBoolean("isAdmin"));
				users.add(utente);
			}
		}
		catch(SQLException e) {
		}
		return users;
	}
	
	public static synchronized BeanUtente loadUserById(int id) {
		Connection con=DBConnection.getConnection();
		String query = "SELECT * FROM UTENTE WHERE idUtente = ?";
		ResultSet rs = null;
		BeanUtente utente = null;
		try {
			utente = new BeanUtente();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			while(rs.next()) {
				utente.setNome(rs.getString("nome"));
				utente.setCf(rs.getString("cf"));
				utente.setDataNascita(rs.getDate("dataNascita").toLocalDate());
				utente.setEmail(rs.getString("email"));
				utente.setIdUtente(rs.getInt("IdUtente"));
				utente.setCognome(rs.getString("cognome"));
				utente.setPass(rs.getString("password"));
				utente.setIsAdmin(rs.getBoolean("isAdmin"));
			}
			DBConnection.releseConnection(con);

		} catch (SQLException e) {
		}
		return utente;
		
	}
	
	public synchronized int login(String email, String pass) {
		Connection con=DBConnection.getConnection();
		String query = "SELECT idUtente, email FROM UTENTE WHERE email = ? and password = ?";
		ResultSet rs = null;
		int id =-1;
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, pass);
			rs=ps.executeQuery();
			while(rs.next()) {
				if(rs.getString("email").equalsIgnoreCase(email))
					id= rs.getInt("idUtente");
			}
			DBConnection.releseConnection(con);

		} catch (SQLException e) {
		}
		return id;
	}
	
	
	public static String toSHA256(String input) {
        try {
            // Crea l'istanza dell'algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Calcola l'hash come array di byte
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            
            // Converte i byte in una stringa esadecimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // In pratica non succede mai, SHA-256 è sempre disponibile
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
	
	
	public synchronized String register(BeanUtente user) {
		
		Connection con=DBConnection.getConnection();
		String query = "INSERT INTO UTENTE (nome,cognome,dataNascita,email,cf,password) value (?,?,?,?,?,?);";
		
		ResultSet rs = null;
		String risultato = "Errore generico";
		
		try {
			PreparedStatement ps = con.prepareStatement("Select idUtente from utente where email = ?");
			ps.setString(1, user.getEmail());
			rs=ps.executeQuery();
			while(rs.next()) {
				if(rs.getInt("idUtente") >0) {
					return "Utente gia registrato";
				}
			}
		}
		catch (SQLException e) {
		}
		
		try {
			int risultatoInserimento;
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user.getNome());
			ps.setString(2, user.getCognome());
			ps.setDate(3, Date.valueOf(user.getDataNascita()));
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getCf());
			ps.setString(6, user.getPass());
			risultatoInserimento=ps.executeUpdate();
			
				if(risultatoInserimento == 1) {
					con.commit();
					return "Registrazione eseguita";
					
				}
			
			
		} catch (SQLException e) {
		}
		DBConnection.releseConnection(con);

		return risultato;
		
	}
	public synchronized String loadNameById(int id) {
		Connection con=DBConnection.getConnection();
		String query = "SELECT Utente.nome FROM UTENTE WHERE idUtente = ?";
		ResultSet rs = null;
		String utente = null;
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			while(rs.next()) {
				utente = rs.getString("nome");
			}
			DBConnection.releseConnection(con);

		} catch (SQLException e) {
		}
		return utente;
		
	}

	public static synchronized String modificaUtenteById(BeanUtente user) {
		Connection con = DBConnection.getConnection();
		int risQuery = 0;
		String result = "";
		String query ="""
				UPDATE UTENTE 
				SET nome = ?,cognome = ?, dataNascita = ?, email = ?, cf = ?
				WHERE idUtente = ?
				""";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user.getNome());
			ps.setString(2, user.getCognome());
			ps.setDate(3, Date.valueOf(user.getDataNascita()));
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getCf());
			ps.setInt(6, user.getIdUtente());
			risQuery = ps.executeUpdate();
			if(risQuery == 1) {
				result = "Modifica Eseguita!";
			}
			else {
				result = "Errore nella modifica";
			}
			con.commit();

		} 
		catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
			}
		}
		
		DBConnection.releseConnection(con);
		return result;
	}
	
	public synchronized int loginAdmin(String email, String pass) {
		Connection con=DBConnection.getConnection();
		String query = "SELECT idUtente, email FROM UTENTE WHERE email = ? and password = ? and isAdmin=1";
		ResultSet rs = null;
		int id =-1;
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, pass);
			rs=ps.executeQuery();
			while(rs.next()) {
				if(rs.getString("email").equalsIgnoreCase(email))
					id= rs.getInt("idUtente");
			}
			DBConnection.releseConnection(con);

		} catch (SQLException e) {
		}
		return id;
	}
	
	
}