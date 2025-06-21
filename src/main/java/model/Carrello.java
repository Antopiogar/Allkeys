package model;

import java.util.ArrayList;

public class Carrello {
	ArrayList<ArticoliCarrello> articoli;
	
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
	public boolean isEmpty() {
		if(articoli.size()==0)
			return true;
		return false;
	}
	
	public float prezzoTotale() {
		float s=0;
		for (ArticoliCarrello art : articoli) {
			s+= art.getQta()*art.getArticolo().getPrezzo();
		}
		s= (float) ((int) (s * 100)) / 100; //tronca alla seconda cifra decimale
		return s;
	}
	
	@Override
	public String toString() {
		return "Carrello [articoli=" + articoli + "]";
	}
	
	public static int isInCarrello (Carrello c,BeanArticolo art) {
		for(int i =0; i<c.getArticoli().size();i++) {
			if(c.getArticoli().get(i).getArticolo().equalsById(art))
				return i;
		}
		return -1;
	}
	
	//DA OTTIMIZZARE
	public static Carrello MergeCarrelli(Carrello c1, Carrello c2) {
		Carrello ris = new Carrello();
		if(c1 ==null && c2!=null) {
			ris = c2;
		}
		else if(c1!=null && c2==null) {
			ris = c1;
		}
		else if(c1 != null && c2 != null){
			for(ArticoliCarrello art : c1.getArticoli() ) {
				ris.AddArticolo(art.getArticolo());
				ris.setQta(art.getArticolo(), art.getQta());			
			}
			for(ArticoliCarrello art : c2.getArticoli() ) {
				for(int i =0;i<art.getQta();i++)
					ris.AddArticolo(art.getArticolo());
							
			}
		}
		
		return ris;
	}
	
	public static boolean confrontaCarrelli(Carrello c1, Carrello c2) {
	    // Se il numero di articoli è diverso, i carrelli non possono essere uguali
	    if (c1.getArticoli().size() != c2.getArticoli().size()) {
	        return false;
	    }

	    // Creiamo due copie delle liste per evitare modifiche sugli oggetti originali
	    ArrayList<ArticoliCarrello> lista1 = new ArrayList<>(c1.getArticoli());
	    ArrayList<ArticoliCarrello> lista2 = new ArrayList<>(c2.getArticoli());

	    // Per ogni articolo nel primo carrello, cerchiamo un articolo uguale nel secondo
	    for (ArticoliCarrello art1 : lista1) {
	        boolean found = false;

	        for (ArticoliCarrello art2 : lista2) {
	            if (art1.getArticolo().equalsById(art2.getArticolo()) && art1.getQta() == art2.getQta()) {
	                lista2.remove(art2); // Rimuoviamo l'articolo corrispondente dalla seconda lista
	                found = true;
	                break;
	            }
	        }

	        // Se non abbiamo trovato una corrispondenza, i carrelli non sono uguali
	        if (!found) {
	            return false;
	        }
	    }

	    return true;
	}
	public ArticoliCarrello getArticoloById(int id) {
		ArticoliCarrello ac = null;
		for(ArticoliCarrello prod : this.articoli) {
			if(prod.getArticolo().getIdArticolo() == id)
				ac = prod;
		}
		return ac;
	}
	
	public static Carrello differenzaCarrelli(Carrello c1, Carrello c2) {
	    Carrello risultato = new Carrello();

	    for (ArticoliCarrello artC1 : c1.getArticoli()) {
	        BeanArticolo articolo = artC1.getArticolo();
	        boolean presenteInC2 = false;

	        for (ArticoliCarrello artC2 : c2.getArticoli()) {
	            if (articolo.equalsById(artC2.getArticolo())) {
	                presenteInC2 = true;
	                break;
	            }
	        }

	        if (!presenteInC2) {
	            ArticoliCarrello nuovo = new ArticoliCarrello(articolo);
	            nuovo.setQta(artC1.getQta()); // mantieni la quantità originale da c1
	            risultato.getArticoli().add(nuovo);
	        }
	    }

	    return risultato;
	}
	
	public static Carrello intersezioneConQuantita(Carrello c1, Carrello c2) {
		Carrello risultato = new Carrello();

	    for (ArticoliCarrello artC1 : c1.getArticoli()) {
	        BeanArticolo articolo = artC1.getArticolo();
	        int qtaC1 = artC1.getQta();
	        int qtaC2 = 0;

	        for (ArticoliCarrello artC2 : c2.getArticoli()) {
	            if (articolo.equalsById(artC2.getArticolo())) {
	                qtaC2 = artC2.getQta();
	                break;
	            }
	        }

	        int differenza = qtaC1 - qtaC2;

	        if (differenza > 0) {
	            ArticoliCarrello nuovo = new ArticoliCarrello(articolo);
	            nuovo.setQta(differenza);
	            risultato.getArticoli().add(nuovo);
	        }
	    }

	    return risultato;
	}


	
	
	
	
}
