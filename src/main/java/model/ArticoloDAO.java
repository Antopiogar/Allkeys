package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArticoloDAO {
	private static Connection con;
	
	public ArticoloDAO() {
		con=DBConnection.getConnection();
	}
	
	public synchronized ArrayList<BeanArticolo> loadAllDistinctArticles() {
		String query = "SELECT DISTINCT * FROM ARTICOLO ";
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
				articoli.add(articolo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnection.releseConnection(con);
		return articoli;
	}

	

}