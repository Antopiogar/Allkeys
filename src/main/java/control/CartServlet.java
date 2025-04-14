package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;


@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CartServlet() {
        super();
        
    }
    
    private void addArticolo(HttpServletRequest request, Carrello cart) {
    	BeanArticolo articolo = (BeanArticolo) ArticoloDAO.getArticoloById(request.getParameter("idArticolo"));
    	System.out.println("articolo DB" + articolo);
		cart.AddArticolo(articolo);
    }
    
    private void changeQta(HttpServletRequest request, Carrello cart) {
    	BeanArticolo articolo = (BeanArticolo) ArticoloDAO.getArticoloById(request.getParameter("idArticolo"));
    	int n = Integer.parseInt(request.getParameter("quantita"));
    	if(n>0) {
        	cart.setQta(articolo, n);
    	}
    	else if(n==0) {
        	cart.removeArticolo(articolo);

    	}
    }
    
    private void delete(HttpServletRequest request, Carrello cart) {
    	BeanArticolo articolo = (BeanArticolo) ArticoloDAO.getArticoloById(request.getParameter("idArticolo"));
    	cart.removeArticolo(articolo);
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request,response);
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
