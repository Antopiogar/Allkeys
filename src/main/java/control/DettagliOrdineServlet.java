package control;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;


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
        @SuppressWarnings("unchecked")
        ArrayList<Acquisto> ordini = (ArrayList<Acquisto>) request.getSession().getAttribute("ordini");
        
        Acquisto acquisto = Acquisto.getAcquistoByIdOrder(ordini, idOrdine);
        System.out.println("Acquisto "+acquisto);
        if(acquisto == null)
        	response.sendRedirect("VisualizzaOrdiniServlet");
        request.setAttribute("acquisto", acquisto);
        RequestDispatcher dispatcher = request.getRequestDispatcher("userLogged/dettagliOrdine.jsp");
        dispatcher.forward(request, response);
    }
}
