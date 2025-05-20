package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecensioneDAO {

	public static synchronized ArrayList<BeanRecensione> getRecensioniByIdArticolo(String idArt){
		ArrayList<BeanRecensione> recensioni = null;
		Connection con=DBConnection.getConnection();
		int idArticolo = Integer.parseInt(idArt);
		String query = """
				
				SELECT
					r.idRecensione,
					r.voto,
					r.dataRecensione as data,
					r.testo,
					u.nome,
					u.idUtente
				FROM RECENSIONE as r
					join Utente as u on r.FkUtente = u.idUtente 
				WHERE r.FkArticolo = ?""";
		ResultSet rs = null;
		try {
			con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1,idArticolo);
			rs=ps.executeQuery();
			recensioni = new ArrayList<>();
			while(rs.next()) {
				BeanUtente user = new BeanUtente();
				BeanRecensione rec = new BeanRecensione();
				user.setIdUtente(rs.getInt("idUtente"));
				user.setNome(rs.getString("nome"));
				rec.setIdRecensione(rs.getInt("idRecensione"));
				rec.setTesto(rs.getString("testo"));
				rec.setVoto(rs.getInt("voto"));
				rec.setData(rs.getDate("data").toLocalDate());
				
				rec.setUtenteRecensione(user);
				recensioni.add(rec);
			}
			ps.close();

			DBConnection.releseConnection(con);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnection.releseConnection(con);
		return recensioni;
	}
	
	public static synchronized boolean saveRecensione(BeanRecensione rec, int idArt){
		Connection con = DBConnection.getConnection();
		String query= """
				INSERT INTO RECENSIONE (voto,dataRecensione,testo,fkUtente,fkArticolo) value
					(?,?,?,?,?);
				""";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, rec.getVoto());
			ps.setDate(2,Date.valueOf(rec.getData()));
			ps.setString(3, rec.getTesto());
			ps.setInt(4, rec.getUtenteRecensione().getIdUtente());
			ps.setInt(5, idArt);
			int risultato = ps.executeUpdate();
			ps.close();
			//verifica che sia andato tutto bene
			if(risultato < 0) {
				con.rollback();
				DBConnection.releseConnection(con);
				return false;
			}
			con.commit();
		
		}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
				System.out.println("ROLLBACK IN CREAZIONE RECENSIONE");
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
		
		DBConnection.releseConnection(con);
		return true;
	}
	
	public static synchronized boolean updateRecensione(BeanRecensione rec) {
		if (rec == null) {
			return false;
		}
		int result = -1;
		String query = """
				UPDATE Recensione set testo = ?, voto = ? where idRecensione = ?
				""";
		Connection con = DBConnection.getConnection();
		try(PreparedStatement ps = con.prepareStatement(query)){
			ps.setString(1, rec.getTesto());
			ps.setInt(2, rec.getVoto());
			ps.setInt(3, rec.getIdRecensione());
			result = ps.executeUpdate();
			
			if(result !=1){
				DBConnection.releseConnection(con);
				return false;
			}
			else {
				con.commit();
				DBConnection.releseConnection(con);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static synchronized boolean deleteRecensione(int idRec) {
		if (idRec < 1 ) {
			return false;
		}
		int result = -1;
		String query = """
				delete from Recensione where idRecensione = ?
				""";
		Connection con = DBConnection.getConnection();
		try(PreparedStatement ps = con.prepareStatement(query)){
			ps.setInt(1, idRec);
			result = ps.executeUpdate();
			if(result !=1){
				con.rollback();
				DBConnection.releseConnection(con);
				return false;
			}
			else {
				con.commit();
				DBConnection.releseConnection(con);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
