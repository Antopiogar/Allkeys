package control;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.*;


@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CartServlet() {
        super();
        
    }
    
    //ADD
    
    private void addArticolo(HttpServletRequest request, Carrello cart) {
    	Object id= request.getSession().getAttribute("idUser");
    	BeanArticolo articolo = (BeanArticolo) ArticoloDAO.getArticoloById(request.getParameter("idArticolo"));
    	ArticoliCarrello ac;

    	cart.AddArticolo(articolo);
		ac = cart.getArticoloById(articolo.getIdArticolo());
    	if(id!= null) {		
    		OrdineDAO.addArticoloToCarrelloDB((int)id, ac);

    	}
    	
		//serve per ottenere anche la quantità dal carrello sessione per poterla inserire in DB
		
			
    }
    
    private void changeQta(HttpServletRequest request, Carrello cart) {
    	BeanArticolo articolo = (BeanArticolo) ArticoloDAO.getArticoloById(request.getParameter("idArticolo"));
    	Object idUser= request.getSession().getAttribute("idUser");
    	int n = Integer.parseInt(request.getParameter("quantita"));
    	//esegue aggiornamento su sessione
    	if(n>0) {
        	cart.setQta(articolo, n);
    	}
    	else if(n==0) {
        	cart.removeArticolo(articolo);

    	}
    	//aggiornamento lato DB
    	if(idUser != null) {
    		int idUtente = (int) idUser; 
    		int idOrdine;
        	ArticoliCarrello ac;
    		idOrdine = OrdineDAO.getIdCarrello(idUtente);

    		if(n>0) {
    			
        		ac = cart.getArticoloById(articolo.getIdArticolo());
        		System.out.println(ac);
        		ComposizioneDAO.cambiaQuantita(idOrdine, ac);
        	}
        	else if(n==0) {
            	cart.removeArticolo(articolo);
            	ComposizioneDAO.removeArticolo(idOrdine,articolo);

        	}
    	}
    }
    
    private void delete(HttpServletRequest request, Carrello cart) {
    	BeanArticolo articolo = (BeanArticolo) ArticoloDAO.getArticoloById(request.getParameter("idArticolo"));
    	Object idUser=  request.getSession().getAttribute("idUser");
    	cart.removeArticolo(articolo);
    	if(idUser != null) {
    		int idOrdine = OrdineDAO.getIdCarrello((int)idUser);
    		ComposizioneDAO.removeArticolo(idOrdine, articolo);
    	}
    }
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
			response.sendRedirect("index.jsp");
		} catch (IOException e) {
		}
    }


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessione = request.getSession();
		String action = request.getParameter("action");
		Carrello cart = (Carrello) sessione.getAttribute("cart");
		if(cart == null) {
			cart = new Carrello();
		}
		
		if(action.equalsIgnoreCase("add")) {
			addArticolo(request,cart);
		}
		
		if(action.equalsIgnoreCase("changeQta")) {
			changeQta(request,cart);
		}
		
		if(action.equalsIgnoreCase("delete")) {
			delete(request,cart);
		}
		
		sessione.setAttribute("cart", cart);
		response.sendRedirect("carrello.jsp");
	}

}
