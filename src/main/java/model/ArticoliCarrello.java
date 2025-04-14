package model;


public class ArticoliCarrello {
	BeanArticolo articolo;
	int qta;
	
	
	public ArticoliCarrello(BeanArticolo articolo) {
		this.articolo=articolo;
		qta =1;
	}
	
	public BeanArticolo getArticolo() {
		return articolo;
	}

	public void setArticolo(BeanArticolo articolo) {
		this.articolo = articolo;
	}

	public void addQta() {
		this.qta++;
	}
	public void removeQta() {
		if(qta>1) {
			qta--;
		}
		else {
			qta=0;
		}
	}
	
	public int getQta() {
		return qta;
	}
	public void setQta(int qta) {
		this.qta = qta;
	}

	
	
}
