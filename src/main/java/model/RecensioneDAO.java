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
					r.voto,
					r.dataRecensione as data,
					r.testo,
					u.nome
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
				user.setNome(rs.getString("nome"));
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
		
	
}
