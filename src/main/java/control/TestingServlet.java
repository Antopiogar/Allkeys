package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ArticoloDAO;
import model.BeanArticolo;
import model.BeanUtente;
import model.Carrello;
import model.UtenteDAO;

/**
 * Servlet implementation class TestingServlet
 */
@WebServlet("/TestingServlet")
public class TestingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;  
    
    public TestingServlet() {
        super();
        
    }

	@SuppressWarnings("unused")
	private void testUserById() {
		UtenteDAO utenteDao = new UtenteDAO();
		BeanUtente user = utenteDao.loadUserById(1);
		System.out.println(user);
	}
	
	@SuppressWarnings("unused")
	private void testAllArticles() {
		ArticoloDAO articoloDao= new ArticoloDAO();
		ArrayList<BeanArticolo> articoli = articoloDao.loadAllDistinctArticles();
		System.out.println(articoli);
	}
	
	
	private void testCarrello() {
		ArticoloDAO articoloDao= new ArticoloDAO();
		ArrayList<BeanArticolo> articoli = articoloDao.loadAllDistinctArticles();
		int qta;
		
		Carrello carrello = new Carrello();
		carrello.AddArticolo(articoli.get(0)); //restituisce true se l'inserimento va a buon fine
		carrello.AddArticolo(articoli.get(1)); 
		carrello.removeArticolo(articoli.get(0)); //restituisce true se l'elemento viene rimosso, se ha più quantità le rimuove tutte
		carrello.setQta(articoli.get(1), 5); //restituisce true se riesce ad impostare la quantità<qta> al prodotto <art> 
		qta = carrello.getQta(articoli.get(1));
		System.out.println("quantità articolo = " +qta);
		System.out.println(carrello);
		
		
		
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		testCarrello();
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
