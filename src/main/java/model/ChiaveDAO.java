package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChiaveDAO {
	private static Connection con;
	
	public ChiaveDAO() {
		con=DBConnection.getConnection();
	}
	
	
	public static float getPriceByIdArticolo(String id) {
		String query = "SELECT c.prezzo FROM CHIAVE WHERE FKArticolo = ? AND FkOrdine = null ORDER BY PREZZO ASC LIMIT 1";
		ResultSet rs = null;
		float prezzo=0;
		try {
			con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(id));
			rs=ps.executeQuery();
			while(rs.next()) {
				prezzo = rs.getFloat("prezzo");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnection.releseConnection(con);
		return prezzo;
	}

	

}