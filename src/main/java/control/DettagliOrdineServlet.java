package control;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;

import java.io.IOException;

@WebServlet("/DettagliOrdineServlet")
public class DettagliOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public DettagliOrdineServlet() {
		super();
	}
	
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	String idOrdineStr = request.getParameter("idOrdine");
        if (idOrdineStr == null) {
            response.sendRedirect("VisualizzaOrdiniServlet");
            return;
        }

        int idOrdine = Integer.parseInt(idOrdineStr);
        Acquisto acquisto = OrdineDAO.loadOrderByIdOrder(idOrdine);
        request.setAttribute("acquisto", acquisto);
        RequestDispatcher dispatcher = request.getRequestDispatcher("userLogged/dettagliOrdine.jsp");
        dispatcher.forward(request, response);
    }
}
