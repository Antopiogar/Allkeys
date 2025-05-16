package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ArticoloDAO {

	private static Connection con;
	
	public ArticoloDAO() {
	}
	
	public static synchronized String getNextLogo() {
	    String query = "SELECT CONCAT('logo', MAX(idArticolo) + 1) AS nextLogo FROM Articolo";
	    String result = null;

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            result = rs.getString("nextLogo"); // può essere null se la tabella è vuota
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return result+".png";
	}

	
	
	public static synchronized ArrayList<BeanArticolo> loadAllDistinctArticles() {
		con=DBConnection.getConnection();

		String query = "SELECT DISTINCT * FROM ARTICOLO ";
		ResultSet rs = null;
		ArrayList<BeanArticolo> articoli = new ArrayList<BeanArticolo>();
		con = DBConnection.getConnection();
		try(PreparedStatement ps = con.prepareStatement(query);) {
			rs=ps.executeQuery();
			while(rs.next()) {
				BeanArticolo articolo = new BeanArticolo();
				articolo.setIdArticolo(rs.getInt("idArticolo"));
				articolo.setNome(rs.getString("nome"));
				articolo.setLogo(rs.getString("logo"));
				articolo.setPiattaforma(rs.getString("piattaforma"));
				articolo.setDescrizione(rs.getString("descrizione"));
				articoli.add(articolo);
			}
			DBConnection.releseConnection(con);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnection.releseConnection(con);
		return articoli;
	}
	
	public static synchronized ArrayList<BeanArticolo> loadAllAvailableArticles() {
		con=DBConnection.getConnection();

		String query = "SELECT * FROM VIEWCATALOGO";
		ResultSet rs = null;
		ArrayList<BeanArticolo> articoli = new ArrayList<BeanArticolo>();
		con = DBConnection.getConnection();
		try (PreparedStatement ps = con.prepareStatement(query);){
			
			rs=ps.executeQuery();
			while(rs.next()) {
				BeanArticolo articolo = new BeanArticolo();
				
				articolo.setIdArticolo(rs.getInt("idArticolo"));
				articolo.setNome(rs.getString("nome"));
				articolo.setLogo(rs.getString("logo"));
				articolo.setPiattaforma(rs.getString("piattaforma"));
				articolo.setPrezzo(rs.getFloat("prezzo"));
				articolo.setDescrizione(rs.getString("descrizione"));

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
		con=DBConnection.getConnection();
		String query = "SELECT * FROM ARTICOLO WHERE IdArticolo = ?";
		ResultSet rs = null;
		BeanArticolo articolo = new BeanArticolo();
		con = DBConnection.getConnection();

		try(PreparedStatement ps = con.prepareStatement(query);) {
			
			ps.setInt(1, Integer.parseInt(id));
			rs=ps.executeQuery();
			while(rs.next()) {
				articolo.setIdArticolo(rs.getInt("idArticolo"));
				articolo.setNome(rs.getString("nome"));
				articolo.setLogo(rs.getString("logo"));
				articolo.setPiattaforma(rs.getString("piattaforma"));
				articolo.setPrezzo(rs.getFloat("prezzo"));
				articolo.setDescrizione(rs.getString("descrizione"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnection.releseConnection(con);
		return articolo;
	}
	
	public static synchronized ArrayList<String> getPiattaforme(){
		con=DBConnection.getConnection();
		String query = "SELECT DISTINCT piattaforma FROM ARTICOLO";
		ArrayList<String> piattaforme = new ArrayList<String>();
		con = DBConnection.getConnection();

		try(PreparedStatement ps = con.prepareStatement(query)) {
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				piattaforme.add(rs.getString("piattaforma"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnection.releseConnection(con);
		return piattaforme;
	}

	
	//se non ci sono errori restituisce l'id dell'articolo creato
	//se ci sono errori restituisce -1 come segnale d'errore
	public static synchronized int createArticolo(Connection con, BeanArticolo art) {
	    String query = """
	        INSERT INTO Articolo (logo, nome, prezzo, piattaforma, descrizione)
	        VALUES (?, ?, ?, ?, ?)
	    """;

	    int idArt = -1;

	    try (PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	        ps.setString(1, art.getLogo());
	        ps.setString(2, art.getNome());
	        ps.setFloat(3, art.getPrezzo());
	        ps.setString(4, art.getPiattaforma());
	        ps.setString(5, art.getDescrizione());

	        int rowsAffected = ps.executeUpdate();

	        if (rowsAffected == 1) {
	            try (ResultSet rs = ps.getGeneratedKeys()) {
	                if (rs.next()) {
	                    idArt = rs.getInt(1);
	                }
	            }
	        }

	    } catch (SQLException e) {
	        System.out.println("ERRORE NELL'INSERIMENTO ARTICOLO");
	        e.printStackTrace();
	    }

	    return idArt;
	}
	
	public static synchronized boolean updateArticolo(BeanArticolo art) {
		if (art == null) {
			return false;
		}
		int result = -1;
		String query = """
				UPDATE Articolo set 
				nome = ?, prezzo = ?, piattaforma=?, descrizione =? where idArticolo = ?
				""";
		Connection con = DBConnection.getConnection();
		try(PreparedStatement ps = con.prepareStatement(query)){
			ps.setString(1, art.getNome());
			ps.setFloat(2, art.getPrezzo());
			ps.setString(3, art.getPiattaforma());
			ps.setString(4, art.getDescrizione());
			ps.setInt(5,art.getIdArticolo());
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
	
	public static synchronized boolean deleteArticolo(int idArt) {
		if (idArt < 1 ) {
			System.out.println("Errore id articolo");

			return false;
		}
		int result = -1;
		String query = """
				delete from Chiave where fkArticolo = ? and fkOrdine is null
				""";
		Connection con = DBConnection.getConnection();
		try(PreparedStatement ps = con.prepareStatement(query)){
			ps.setInt(1, idArt);
			result = ps.executeUpdate();
			System.out.println("result ="+ result);

			if(result <1){
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