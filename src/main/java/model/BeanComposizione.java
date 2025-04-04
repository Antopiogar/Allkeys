package model;

public class BeanComposizione {
	private int idComposizione;
	private float prezzo;
	private BeanArticolo fkArticolo;
	private BeanOrdine fkOrdine;
	
	public BeanComposizione() {
		// TODO Auto-generated constructor stub
	}

	public int getIdComposizione() {
		return idComposizione;
	}

	public void setIdComposizione(int idComposizione) {
		this.idComposizione = idComposizione;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public BeanArticolo getFkArticolo() {
		return fkArticolo;
	}

	public void setFkArticolo(BeanArticolo fkArticolo) {
		this.fkArticolo = fkArticolo;
	}

	public BeanOrdine getFkOrdine() {
		return fkOrdine;
	}

	public void setFkOrdine(BeanOrdine fkOrdine) {
		this.fkOrdine = fkOrdine;
	}

	@Override
	public String toString() {
		return "BeanComposizione [idComposizione=" + idComposizione + ", prezzo=" + prezzo + ", fkArticolo="
				+ fkArticolo + ", fkOrdine=" + fkOrdine + "]";
	}
	
}
