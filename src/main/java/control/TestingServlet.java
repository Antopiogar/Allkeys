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

	private void testUserById() {
		UtenteDAO utenteDao = new UtenteDAO();
		BeanUtente user = utenteDao.loadUserById(1);
		System.out.println(user);
	}
	
	private void testAllArticles() {
		ArticoloDAO articoloDao= new ArticoloDAO();
		ArrayList<BeanArticolo> articoli = articoloDao.loadAllDistinctArticles();
		System.out.println(articoli);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		testUserById();
		testAllArticles();
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
