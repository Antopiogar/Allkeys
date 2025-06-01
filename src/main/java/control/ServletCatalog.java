package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ArticoloDAO;
import model.BeanArticolo;

/**
 * Servlet implementation class ServletCatalog
 */
@WebServlet("/ViewCatalog")
public class ServletCatalog extends HttpServlet {
	public ServletCatalog() {
	}
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String piattaforma = request.getParameter("piattaforma");
		System.out.println(piattaforma);
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
