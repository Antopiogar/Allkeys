package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class OrdineDAO {

	
private static Connection con;
	
	public OrdineDAO() {
	}
	
	
	public static synchronized boolean CreateOrder(ArrayList<ArticoliCarrello> articoli, BeanUtente user) {
		String query = "{call CreazioneOrdine(?,?)}";
	    int idOrder = 0;
	    boolean controlloErroriComposizione = false;
	    try {
			con=DBConnection.getConnection();

	        CallableStatement cs = con.prepareCall(query);
	        cs.setInt(1, user.getIdUtente());
	        cs.registerOutParameter(2, Types.INTEGER);

	        cs.execute();
	        idOrder = cs.getInt(2);
	        controlloErroriComposizione = ComposizioneDAO.CreateComposizione(con, articoli, idOrder);
	        if(controlloErroriComposizione) {
	        	return false;
	        }
	        
	        con.commit();
	        return true;

	    } catch (SQLException e) {
	    	System.out.println("MORTO IN ORDINE");
	        e.printStackTrace();
	        try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
	    } finally {
	        DBConnection.releseConnection(con);
	    }
	    return false;
	}
	
	public static synchronized Carrello LoadCarrelByUser(int idUser){
		Carrello c = null;
		
		try {
			int idOrdine = -1;
			con = DBConnection.getConnection();
			String query ="SELECT o.idOrdine from Ordine as o where o.conferma = false and o.fkUtente = ? limit 1";
			PreparedStatement ps = con.prepareStatement(query);
			BeanArticolo art;
			ps.setInt(1, idUser);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				idOrdine = rs.getInt("idOrdine");
			}
			if(idOrdine == -1) {
				return null;
			}
			c = new Carrello();
			query = """
					SELECT 
					    a.idArticolo,
					    a.logo,
					    a.nome,
					    a.piattaforma, 
					    a.prezzo,
					    COUNT(*) AS qta
					FROM composizione AS c
					JOIN articolo AS a ON c.FkArticolo = a.idArticolo 
					where c.FkOrdine = ?
					GROUP BY 
					    a.idArticolo, a.logo, a.nome, a.piattaforma, a.prezzo
					""";
			ps = con.prepareStatement(query);
			ps.setInt(1, idOrdine);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				art = new BeanArticolo();
				art.setIdArticolo(rs.getInt("idArticolo"));
				art.setLogo(rs.getString("logo"));
				art.setNome(rs.getString("nome"));
				art.setPiattaforma(rs.getString("piattaforma"));
				art.setPrezzo(rs.getFloat("prezzo"));
				c.AddArticolo(art);
				c.setQta(art, rs.getInt("qta"));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
	        DBConnection.releseConnection(con);
	    }
		
		return c;
		
	}
	
	//BOMBA!
	public static synchronized boolean ConfirmOrder(int IdUtente, int IdCartaPagamento) {
		String query = "SELECT o.idOrdine from Ordine as o where o.conferma = false and o.fkUtente = ? limit 1";
	    int idOrder = 0;
	    boolean controlloErroriComposizione = false;
	    try {
			con=DBConnection.getConnection();

	        CallableStatement cs = con.prepareCall(query);
	        //cs.setInt(1, user.getIdUtente());
	        cs.registerOutParameter(2, Types.INTEGER);

	        cs.execute();
	        idOrder = cs.getInt(2);
	        //controlloErroriComposizione = ComposizioneDAO.CreateComposizione(con, articoli, idOrder);
	        if(controlloErroriComposizione) {
	        	return false;
	        }
	        
	        con.commit();
	        return true;

	    } catch (SQLException e) {
	    	System.out.println("MORTO IN ORDINE");
	        e.printStackTrace();
	        try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
	    } finally {
	        DBConnection.releseConnection(con);
	    }
	    return false;
	}
	
}