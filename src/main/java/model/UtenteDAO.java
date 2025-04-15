package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAO {

	private static Connection con;
	
	public UtenteDAO() {
	}
	
	public synchronized BeanUtente loadUserById(int id) {
		con=DBConnection.getConnection();
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
			}
			DBConnection.releseConnection(con);

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return utente;
		
	}
	
	public synchronized boolean login(String email, String pass) {
		con=DBConnection.getConnection();
		String query = "SELECT * FROM UTENTE WHERE email = ? and password = sha2(?,256)";
		ResultSet rs = null;
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, pass);
			rs=ps.executeQuery();
			while(rs.next()) {
				if(rs.getString("email").equalsIgnoreCase(email))
					return true;
			}
			DBConnection.releseConnection(con);

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	public synchronized boolean register(BeanUtente user) {
		
		con=DBConnection.getConnection();
		String query = "INSERT INTO UTENTE (nome,cognome,dataNascita,email,cf,password) value (?,?,?,?,?,sha2(?,256);";
		ResultSet rs = null;
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user.getNome());
			ps.setString(2, user.getCognome());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getCf());
			ps.setString(6, user.getPass());
			rs=ps.executeQuery();
			while(rs.next()) {
				if(rs.rowInserted()) {
					return true;
				}
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		DBConnection.releseConnection(con);

		return false;
		
	}
}