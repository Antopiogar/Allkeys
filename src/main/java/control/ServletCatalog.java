package control;

import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.ArticoloDAO;
import model.BeanArticolo;

/**
 * Servlet implementation class ServletCatalog
 */
@WebServlet("/ViewCatalog")
public class ServletCatalog extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String piattaforma = request.getParameter("piattaforma");
		ArrayList<BeanArticolo> articoli = null;
		if(piattaforma == null) {
			 articoli = ArticoloDAO.loadAllAvailableArticles();
			
		}
		else {
			request.setAttribute("Filtro", piattaforma);
			articoli = ArticoloDAO.loadAvailableArticlesFromPiattaforma(piattaforma);
		}
		request.setAttribute("result", articoli);
		RequestDispatcher view = request.getRequestDispatcher("result.jsp");
		view.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
