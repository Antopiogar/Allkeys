package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalTime;
import java.util.ArrayList;

import com.mysql.cj.jdbc.CallableStatement;

public class OrdineDAO {

	
private static Connection con;
	
	public OrdineDAO() {
	}
	
	//NON TESTATO
	public static synchronized boolean CreateOrder(ArrayList<ArticoliCarrello> articoli, BeanUtente user) {
		con=DBConnection.getConnection();

		String query = "call {createOrder(?,?,?)};";
		int idOrder=0;
		ResultSet rs = null;
		try {
			LocalTime localTime = LocalTime.now();
	        // Converte in java.util.Date
	        Date date = Date.valueOf(localTime.toString());
			con = DBConnection.getConnection();
			//creazione dell'ordine
			CallableStatement procedureCreation = (CallableStatement) con.prepareCall(query);
			PreparedStatement ps = null;
			procedureCreation.setInt(1,user.getIdUtente());
			procedureCreation.setDate(2,date);
			procedureCreation.setInt(3,0);
			procedureCreation.registerOutParameter(4, Types.INTEGER);
			rs=procedureCreation.executeQuery();
			if(rs.rowInserted()) {
				idOrder = rs.getInt(4);
				//inserimento elementi in composizione
				for (ArticoliCarrello articolo : articoli) {
					query ="""
							INSERT INTO Composizione (prezzoPagato,FkArticolo,FKOrdine) value
						   (
						   			(select c.prezzo from Chiave as c where (c.FkArticolo = p_IdArticlo) and Fk_ordine is NULL order by c.id asc limit 1),
						   			?,?
						   	)
							""";
					for(int i = 0; i< articolo.getQta();i++) {
						ps = (CallableStatement) con.prepareCall(query);
						ps.setInt(1, articolo.getArticolo().getIdArticolo());
						ps.setInt(2, idOrder);
						rs = ps.executeQuery();
						if(!rs.rowInserted()) {
							con.rollback();
							DBConnection.releseConnection(con);
							return false;
						}
					}
					
				}
			}
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnection.releseConnection(con);
		return false;
	}
	
	
}


