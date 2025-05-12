package control;

import java.io.IOException;
import model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DettagliArticoloServlet")
public class DettagliArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public DettagliArticoloServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idArticolo = request.getParameter("articolo");
		if(idArticolo == null) response.sendRedirect(request.getContextPath() + "/index.jsp"); //se la servlet non viene usata correttamente.
		BeanArticolo articolo = null;
		if(idArticolo != null) {
			articolo = ArticoloDAO.getArticoloById(idArticolo);
			request.setAttribute("articoloInfo", articolo);
			RequestDispatcher view = request.getRequestDispatcher("dettagliArticolo.jsp");
			view.forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
