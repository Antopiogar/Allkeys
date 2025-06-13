package model;

import java.sql.*;
import java.util.ArrayList;

public class ArticoloDAO {

	public ArticoloDAO() {}

	public static synchronized String getNextLogo() {
		String query = "SELECT CONCAT('logo', MAX(idArticolo) + 1) AS nextLogo FROM Articolo";
		String result = null;

		try (Connection con = DBConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement(query);
			 ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				result = rs.getString("nextLogo");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result + ".png";
	}

	public static synchronized ArrayList<BeanArticolo> loadAllDistinctArticles() {
		ArrayList<BeanArticolo> articoli = new ArrayList<>();
		String query = "SELECT DISTINCT * FROM ARTICOLO";

		try (Connection con = DBConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement(query);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				BeanArticolo articolo = new BeanArticolo();
				articolo.setIdArticolo(rs.getInt("idArticolo"));
				articolo.setNome(rs.getString("nome"));
				articolo.setLogo(rs.getString("logo"));
				articolo.setPiattaforma(rs.getString("piattaforma"));
				articolo.setDescrizione(rs.getString("descrizione"));
				articoli.add(articolo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return articoli;
	}

	public static synchronized ArrayList<BeanArticolo> loadAllAvailableArticles() {
		ArrayList<BeanArticolo> articoli = new ArrayList<>();
		String query = "SELECT * FROM VIEWCATALOGO";

		try (Connection con = DBConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement(query);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
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
			e.printStackTrace();
		}

		return articoli;
	}

	public static synchronized ArrayList<BeanArticolo> loadAvailableArticlesFromPiattaforma(String piattaforma) {
		ArrayList<BeanArticolo> articoli = new ArrayList<>();
		String query = "SELECT * FROM VIEWCATALOGO WHERE PIATTAFORMA = ?";

		try (Connection con = DBConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, piattaforma);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					BeanArticolo articolo = new BeanArticolo();
					articolo.setIdArticolo(rs.getInt("idArticolo"));
					articolo.setNome(rs.getString("nome"));
					articolo.setLogo(rs.getString("logo"));
					articolo.setPiattaforma(rs.getString("piattaforma"));
					articolo.setPrezzo(rs.getFloat("prezzo"));
					articolo.setDescrizione(rs.getString("descrizione"));
					articoli.add(articolo);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return articoli;
	}

	public static BeanArticolo getArticoloById(String id) {
		BeanArticolo articolo = new BeanArticolo();
		String query = "SELECT * FROM ARTICOLO WHERE IdArticolo = ?";

		try (Connection con = DBConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement(query)) {

			ps.setInt(1, Integer.parseInt(id));

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					articolo.setIdArticolo(rs.getInt("idArticolo"));
					articolo.setNome(rs.getString("nome"));
					articolo.setLogo(rs.getString("logo"));
					articolo.setPiattaforma(rs.getString("piattaforma"));
					articolo.setPrezzo(rs.getFloat("prezzo"));
					articolo.setDescrizione(rs.getString("descrizione"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return articolo;
	}

	public static synchronized ArrayList<String> getPiattaforme() {
		ArrayList<String> piattaforme = new ArrayList<>();
		String query = "SELECT DISTINCT piattaforma FROM ARTICOLO";

		try (Connection con = DBConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement(query);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				piattaforme.add(rs.getString("piattaforma"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return piattaforme;
	}

	public static synchronized int createArticolo(Connection con, BeanArticolo art) {
		String query = "INSERT INTO Articolo (logo, nome, prezzo, piattaforma, descrizione) VALUES (?, ?, ?, ?, ?)";
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
						System.out.println("newIdArticolo = " + idArt);
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
		if (art == null) return false;

		String query = "UPDATE Articolo SET nome = ?, prezzo = ?, piattaforma = ?, descrizione = ? WHERE idArticolo = ?";

		try (Connection con = DBConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement(query)) {

			ps.setString(1, art.getNome());
			ps.setFloat(2, art.getPrezzo());
			ps.setString(3, art.getPiattaforma());
			ps.setString(4, art.getDescrizione());
			ps.setInt(5, art.getIdArticolo());

			int result = ps.executeUpdate();
			if (result != 1) return false;

			con.commit();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static synchronized boolean deleteArticolo(int idArt) {
		if (idArt < 1) {
			System.out.println("Errore id articolo");
			return false;
		}

		String query = "DELETE FROM Chiave WHERE fkArticolo = ? AND fkOrdine IS NULL";

		try (Connection con = DBConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement(query)) {

			ps.setInt(1, idArt);
			int result = ps.executeUpdate();
			System.out.println("result =" + result);

			if (result < 1) {
				con.rollback();
				return false;
			} else {
				con.commit();
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static synchronized int ExistArticolo(BeanArticolo art) {
		int ris = -1;
		String query = "SELECT * FROM ARTICOLO WHERE nome = ? AND prezzo = ? AND piattaforma = ? AND descrizione = ?";

		try (Connection con = DBConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement(query)) {

			ps.setString(1, art.getNome());
			ps.setFloat(2, art.getPrezzo());
			ps.setString(3, art.getPiattaforma());
			ps.setString(4, art.getDescrizione());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					ris = rs.getInt("idArticolo");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ris;
	}

	public static synchronized ArrayList<BeanArticolo> fastSearch(String search) {
		ArrayList<BeanArticolo> articoli = new ArrayList<>();
		String query = "SELECT * FROM VIEWCATALOGO WHERE nome LIKE ?";

		try (Connection con = DBConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement(query)) {

			ps.setString(1, "%" + search + "%");

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					BeanArticolo articolo = new BeanArticolo();
					articolo.setIdArticolo(rs.getInt("idArticolo"));
					articolo.setNome(rs.getString("nome"));
					articolo.setLogo(rs.getString("logo"));
					articolo.setPiattaforma(rs.getString("piattaforma"));
					articolo.setPrezzo(rs.getFloat("prezzo"));
					articolo.setDescrizione(rs.getString("descrizione"));
					articoli.add(articolo);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return articoli;
	}
}
