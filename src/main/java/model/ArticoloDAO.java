package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArticoloDAO {
<<<<<<< HEAD
	private Connection con;
	
	public ArticoloDAO() {
		
	}
	
	public synchronized ArrayList<BeanArticolo> loadAllDistinctArticles() {
		con = DBConnection.getConnection();
=======
	private static Connection con;
	
	public ArticoloDAO() {
		con=DBConnection.getConnection();
	}
	
	public synchronized ArrayList<BeanArticolo> loadAllDistinctArticles() {
>>>>>>> origin/cartCreation
		String query = "SELECT DISTINCT * FROM ARTICOLO ";
		ResultSet rs = null;
		ArrayList<BeanArticolo> articoli = new ArrayList<BeanArticolo>();
		try {
<<<<<<< HEAD
=======
			con = DBConnection.getConnection();
>>>>>>> origin/cartCreation
			PreparedStatement ps = con.prepareStatement(query);
			rs=ps.executeQuery();
			while(rs.next()) {
				BeanArticolo articolo = new BeanArticolo();
				articolo.setIdArticolo(rs.getInt("idArticolo"));
				articolo.setNome(rs.getString("nome"));
				articolo.setLogo(rs.getString("logo"));
				articolo.setPiattaforma(rs.getString("piattaforma"));
				articoli.add(articolo);
			}
<<<<<<< HEAD
			DBConnection.releseConnection(con);
=======
>>>>>>> origin/cartCreation
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
<<<<<<< HEAD
		return articoli;
	}
=======
		DBConnection.releseConnection(con);
		return articoli;
	}
	
	public synchronized ArrayList<BeanArticolo> loadAllAvailableArticles() {
		String query = "select distinct * from articolo as a join chiave as c on a.idArticolo = c.fkArticolo where c.fkOrdine is null";
		ResultSet rs = null;
		ArrayList<BeanArticolo> articoli = new ArrayList<BeanArticolo>();
		try {
			con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			rs=ps.executeQuery();
			while(rs.next()) {
				BeanArticolo articolo = new BeanArticolo();
				
				articolo.setIdArticolo(rs.getInt("idArticolo"));
				articolo.setNome(rs.getString("nome"));
				articolo.setLogo(rs.getString("logo"));
				articolo.setPiattaforma(rs.getString("piattaforma"));
				articolo.setPrezzo(rs.getFloat("prezzo"));
				
				articoli.add(articolo);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnection.releseConnection(con);
		return articoli;
	}

	public static BeanArticolo getArticoloById(String id) {
		
		String query = "SELECT * FROM ARTICOLO WHERE IdArticolo = ?";
		ResultSet rs = null;
		BeanArticolo articolo = new BeanArticolo();
		try {
			con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(id));
			rs=ps.executeQuery();
			while(rs.next()) {
				articolo.setIdArticolo(rs.getInt("idArticolo"));
				articolo.setNome(rs.getString("nome"));
				articolo.setLogo(rs.getString("logo"));
				articolo.setPiattaforma(rs.getString("piattaforma"));
				articolo.setPrezzo(rs.getFloat("prezzo"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnection.releseConnection(con);
		return articolo;
	}
>>>>>>> origin/cartCreation

	

}