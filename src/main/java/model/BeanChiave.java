package model;

public class BeanChiave {
	private int idChiave;
	private String codice;
<<<<<<< HEAD
	private float prezzo;
=======
>>>>>>> origin/cartCreation
	private BeanOrdine fkOrdine;
	private BeanArticolo fkArticolo;
	
	public BeanChiave() {
		// TODO Auto-generated constructor stub
	}

	public int getIdChiave() {
		return idChiave;
	}

	public void setIdChiave(int idChiave) {
		this.idChiave = idChiave;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

<<<<<<< HEAD
	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
=======
>>>>>>> origin/cartCreation

	public BeanOrdine getFkOrdine() {
		return fkOrdine;
	}

	public void setFkOrdine(BeanOrdine fkOrdine) {
		this.fkOrdine = fkOrdine;
	}

	public BeanArticolo getFkArticolo() {
		return fkArticolo;
	}

	public void setFkArticolo(BeanArticolo fkArticolo) {
		this.fkArticolo = fkArticolo;
	}

	@Override
	public String toString() {
<<<<<<< HEAD
		return "BeanChiave [idChiave=" + idChiave + ", codice=" + codice + ", prezzo=" + prezzo + ", fkOrdine="
=======
		return "BeanChiave [idChiave=" + idChiave + ", codice=" + codice + ", fkOrdine="
>>>>>>> origin/cartCreation
				+ fkOrdine + ", fkArticolo=" + fkArticolo + "]";
	}
	
}
