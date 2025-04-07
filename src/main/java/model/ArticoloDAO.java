package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArticoloDAO {
	private Connection con;
	
	public ArticoloDAO() {
		
	}
	
	public synchronized ArrayList<BeanArticolo> loadAllDistinctArticles() {
		con = DBConnection.getConnection();
		String query = "SELECT DISTINCT * FROM ARTICOLO ";
		ResultSet rs = null;
		ArrayList<BeanArticolo> articoli = new ArrayList<BeanArticolo>();
		try {
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
			DBConnection.releseConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articoli;
	}

	

}