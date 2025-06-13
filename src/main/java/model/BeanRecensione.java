package model;

import java.time.LocalDate;

public class BeanRecensione {
	private int idRecensione;
	private int voto;
	private String testo;
	private LocalDate data;
	private BeanUtente utenteRecensione;
	
	public int getVoto() {
		return voto;
	}
	
	public void setVoto(int voto) {
		this.voto = voto;
	}
	
	public String getTesto() {
		return testo;
	}
	
	public void setTesto(String testo) {
		this.testo = testo;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public BeanUtente getUtenteRecensione() {
		return utenteRecensione;
	}
	
	public void setUtenteRecensione(BeanUtente utenteRecensione) {
		this.utenteRecensione = utenteRecensione;
	}
	public int getIdRecensione() {
		return idRecensione;
	}

	public void setIdRecensione(int idRecensione) {
		this.idRecensione = idRecensione;
	}
	
	@Override
	public String toString() {
		return String.format("BeanRecensione [id=%s, voto=%s, testo=%s, data=%s]", idRecensione,voto, testo, data);
	}
	
	
	
	
}
