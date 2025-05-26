package model;

import java.time.LocalDateTime;

public class BeanOrdine {
	private int idOrdine;
	private LocalDateTime dataAcquisto;
	private Boolean conferma;
	private BeanCartaPagamento pagamento;
	private String fattura;

	private BeanUtente utente;
	
	public BeanUtente getUtente() {
		return utente;
	}
	
	public double getTotale() {
		return 0.0;
	}

	public void setUtente(BeanUtente utente) {
		this.utente = utente;
	}

	public BeanOrdine() {
		// TODO Auto-generated constructor stub
	}

	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}

	public LocalDateTime getDataAcquisto() {
		return dataAcquisto;
	}

	public void setDataAcquisto(LocalDateTime dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}

	public Boolean getConferma() {
		return conferma;
	}

	public void setConferma(Boolean conferma) {
		this.conferma = conferma;
	}

	public BeanCartaPagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(BeanCartaPagamento pagamento) {
		this.pagamento = pagamento;
	}
	public String getFattura() {
		return fattura;
	}

	public void setFattura(String fattura) {
		this.fattura = fattura;
	}

	@Override
	public String toString() {
		return "BeanOrdine [idOrdine=" + idOrdine + ", dataAcquisto=" + dataAcquisto + ", conferma=" + conferma

				+", fattura ="+fattura+ ", pagamento=" + pagamento + ", utente=" + utente + "]";

	}
}
