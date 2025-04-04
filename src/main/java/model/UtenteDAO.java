package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAO {
	private static Connection con;
	
	public UtenteDAO() {
		con=DBConnection.getConnection();
	}
	
	public BeanUtente loadUserById(int id) {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return utente;
		
	}
}