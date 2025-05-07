package model;

import java.util.ArrayList;

public class Acquisto {
	private BeanUtente utente;
	private BeanCartaPagamento carta;
	private BeanOrdine ordine;
	private ArrayList<BeanArticolo> articoli;
	private ArrayList<BeanChiave> chiavi;
	
	
	public Acquisto(int idUtente) {
		
		this.utente = UtenteDAO.loadUserById(idUtente);
		
	}
	
	public void AddProdottto(BeanArticolo art,BeanChiave chiave) {
		if(this.articoli == null)
			this.articoli = new ArrayList<BeanArticolo>();
		if(this.chiavi== null)
			this.chiavi = new ArrayList<BeanChiave>();
		articoli.add(art);
		chiavi.add(chiave);
	}

	public BeanUtente getUtente() {
		return utente;
	}

	public void setUtente(int idUtente) {
		this.utente = UtenteDAO.loadUserById(idUtente);
	}

	public BeanCartaPagamento getCarta() {
		return carta;
	}

	public void setCarta(BeanCartaPagamento carta) {
		this.carta = carta;
	}

	public BeanOrdine getOrdine() {
		return ordine;
	}

	public void setOrdine(BeanOrdine ordine) {
		this.ordine = ordine;
	}

	public ArrayList<BeanArticolo> getArticoli() {
		return articoli;
	}

	public void setArticoli(ArrayList<BeanArticolo> articoli) {
		this.articoli = articoli;
	}

	public ArrayList<BeanChiave> getChiavi() {
		return chiavi;
	}

	public void setChiavi(ArrayList<BeanChiave> chiavi) {
		this.chiavi = chiavi;
	}

	public int getIdOrdine() {
		return ordine.getIdOrdine();
	}
	
	@Override
	public String toString() {
		return String.format("Acquisto [utente=%s, carta=%s, ordine=%s, articoli=%s, chiavi=%s]", utente, carta, ordine,
				articoli, chiavi);
	}
	
	public static int existsOrder(ArrayList <Acquisto> acquisti, int idOrdine) {
		for(int i=0;i<acquisti.size();i++) {
			if(acquisti.get(i).getIdOrdine() == idOrdine) {
				return i;
			}
		}
		return -1;
	}
	
	public static Acquisto getAcquistoByIdOrder(ArrayList <Acquisto> acquisti, int idOrdine) {
		for(int i=0;i<acquisti.size();i++) {
			if(acquisti.get(i).getIdOrdine() == idOrdine) {
				return acquisti.get(i);
			}
		}
		return null;
	}
}
