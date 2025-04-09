package model;

import java.util.ArrayList;

public class Carrello {
	ArrayList<ArticoliCarrello> articoli;
	
	
	//DA FARE NON TOCCARE TODO
	public synchronized boolean SaveOrder() {
		//OrdineDAO.CreateOrder(articoli);
		return false;
	}
	public Carrello() {
		articoli= new ArrayList<>();
	}
	
	public boolean AddArticolo(BeanArticolo art) {
		try{
			for (ArticoliCarrello carrelProd : articoli) {
				if(art.getIdArticolo() == carrelProd.getArticolo().getIdArticolo()) {
					carrelProd.addQta();
					return true;
				}
				
			}
			
			ArticoliCarrello prod = new ArticoliCarrello(art);
			articoli.add(prod);
			return true;
	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean removeArticolo(BeanArticolo art) {
		try{
			for (ArticoliCarrello carrelProd : articoli) {
				if(art.getIdArticolo() == carrelProd.getArticolo().getIdArticolo()) {
					articoli.remove(carrelProd);
					return true;
				}
				
			}
			
		}
		catch (Exception e) {
		
		}
		return false;
	}
	public boolean setQta(BeanArticolo art,int qta) {
		if (qta < 1)	
			return false;
		
		for (ArticoliCarrello cartProd : articoli) {
			if(art.getIdArticolo() == cartProd.getArticolo().getIdArticolo()) {
				cartProd.setQta(qta);
				return true;
			}
		}
		return false;
	}
	public int getQta(BeanArticolo art) {
		
		for (ArticoliCarrello cartProd : articoli) {
			if(art.getIdArticolo() == cartProd.getArticolo().getIdArticolo()) {
				return cartProd.getQta();

			}
		}
		return -1;
	}
	
	
	public ArrayList<ArticoliCarrello> getArticoli() {
		return articoli;
	}
	@Override
	public String toString() {
		return "Carrello [articoli=" + articoli + "]";
	}
	
	
	
}
