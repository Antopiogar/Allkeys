package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;




public class ChiaveDAO {
	
	public ChiaveDAO() {
	}
	
	
	//SE VA A BUON FINE RITORNA 0
	//SE MANCANO PRODOTTI SUL DB RITORNA ID DI QUEL PRODOTTO
	//SE CI SONO ALTRI ERRORI RITORNA -1
	public static int confermaChiaviOrdinate(Connection con, int idUtente, int idOrdine){
		Carrello c = OrdineDAO.LoadCarrelByUser(idUtente);
		ArrayList<ChiaviDisponibili> disp = loadDisponibilita();
		boolean acquistoEffettuato = false;
		int idErrore=-1;
		//controlla se è possibile acquistare tutte le chiavi dell'ordine
		//se non è possibile acquistare indica l'id del primo articolo non acquistabile
		
			System.out.println("CARRELLO CONFERMA "+ c);
			System.out.println("\ndisp");
			System.out.println(disp);
			for(ArticoliCarrello art : c.getArticoli()) {
				System.out.println(c.getArticoli());
				idErrore = ChiaviDisponibili.canBeBuy(disp, art);
				//se da un id valido seganla problema su quel prodotto
				if(idErrore != -1) {
					return idErrore;
				}
			}

		acquistoEffettuato = ordinaChiavi(con,c,idOrdine);
		if(!acquistoEffettuato) {
			return -1;
		}
		
		return 0;
	
	}



	private static synchronized boolean ordinaChiavi(Connection con,Carrello c, int idOrdine) {
		String query = "Update chiave set fkOrdine = ? where fkArticolo = ? and fkOrdine is null limit ? ";
		try {
			int r=0;
			for(ArticoliCarrello ac : c.getArticoli()) {
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, idOrdine);
				ps.setInt(2,ac.getArticolo().getIdArticolo());
				ps.setInt(3, ac.getQta());
				r= ps.executeUpdate();
				System.out.println("r = "+r +"qta"+ac.getQta());
				if(r != ac.getQta()) {
					System.out.println("Rollback eseguito, qualcosa non va!");

					con.rollback();
					return false;
				}
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MORTO IN SALVA CHIAVI");
			return false;
		}
		try {
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
		
	}



	private static synchronized ArrayList<ChiaviDisponibili> loadDisponibilita() {
		ArrayList<ChiaviDisponibili> disp = new ArrayList<ChiaviDisponibili>();
		Connection con = DBConnection.getConnection();
		try {
			ChiaviDisponibili cd = new ChiaveDAO.ChiaviDisponibili();
			String query="""
					SELECT * FROM N_CHIAVI_DISPONIBILI
					""";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				cd = new ChiaviDisponibili();
				cd.setDisponibilita(rs.getInt("qta"));
				cd.setIdArticolo(rs.getInt("idArticolo"));
				cd.setIdChiave(rs.getInt("IdChiave"));
				cd.setNome(rs.getString("nome"));
				disp.add(cd);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERRORE IN CARICAMENTO DISPONIBILITA");
		}
		
		System.out.println("DISP = " + disp);
		return disp;
	}
	
	public static class ChiaviDisponibili {
		int idArticolo,idChiave,disponibilita;
		String nome;

		
		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public ChiaviDisponibili() {
			
		}
		
		public ChiaviDisponibili(int idArticolo, int idChiave, int disponibilita) {
			this.idArticolo = idArticolo;
			this.idChiave = idChiave;
			this.disponibilita = disponibilita;
		}

		public int getIdArticolo() {
			return idArticolo;
		}

		public void setIdArticolo(int idArticolo) {
			this.idArticolo = idArticolo;
		}

		public int getIdChiave() {
			return idChiave;
		}

		public void setIdChiave(int idChiave) {
			this.idChiave = idChiave;
		}

		public int getDisponibilita() {
			return disponibilita;
		}

		public void setDisponibilita(int disponibilita) {
			this.disponibilita = disponibilita;
		}

	
		
		
	

		@Override
		public String toString() {
			return String.format("ChiaviDisponibili [idArticolo=%s, nome=%s, idChiave=%s, disponibilita=%s]",
					idArticolo, nome, idChiave, disponibilita);
		}

		//controlla se è possibile acquistare tutte le chiavi dell'ordine
		//se non è possibile acquistare indica l'id del primo articolo non acquistabile
		public static synchronized int canBeBuy(ArrayList<ChiaviDisponibili> cd, ArticoliCarrello art){
			for(int i = 1; i <= cd.size(); i++) {
				if(cd.get(i-1).getIdArticolo() == art.getArticolo().getIdArticolo()) {
					if(cd.get(i-1).getDisponibilita() < art.getQta()){
						return i;
					}		
				}
			}
			return -1;
		}
		
		
		
	}


	

}