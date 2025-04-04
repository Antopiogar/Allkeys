package model;

import java.time.LocalTime;

public class BeanCartaPagamento {
	private int idCarta;
	private String titolare;
	private String nCarta;
	private LocalTime scadenza;
	private String codiceCVC;
	private BeanUtente fkUtente;
	
	public BeanCartaPagamento() {
	}
	
	public int getIdCarta() {
		return idCarta;
	}
	public void setIdCarta(int idCarta) {
		this.idCarta = idCarta;
	}
	public String getTitolare() {
		return titolare;
	}
	public void setTitolare(String titolare) {
		this.titolare = titolare;
	}
	public String getnCarta() {
		return nCarta;
	}
	public void setnCarta(String nCarta) {
		this.nCarta = nCarta;
	}
	public LocalTime getScadenza() {
		return scadenza;
	}
	public void setScadenza(LocalTime scadenza) {
		this.scadenza = scadenza;
	}
	public String getCodiceCVC() {
		return codiceCVC;
	}
	public void setCodiceCVC(String codiceCVC) {
		this.codiceCVC = codiceCVC;
	}
	public BeanUtente getFkUtente() {
		return fkUtente;
	}
	public void setFkUtente(BeanUtente fkUtente) {
		this.fkUtente = fkUtente;
	}
	
	@Override
	public String toString() {
		return "BeanCartaPagamento [idCarta=" + idCarta + ", titolare=" + titolare + ", nCarta=" + nCarta
				+ ", scadenza=" + scadenza + ", codiceCVC=" + codiceCVC + ", fkUtente=" + fkUtente + "]";
	}
	
}

