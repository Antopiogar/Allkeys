package control;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;
import java.util.ArrayList;

@WebServlet("/VisualizzaOrdiniServlet")
public class VisualizzaOrdiniServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public VisualizzaOrdiniServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        Object idUtenteObj = session.getAttribute("idUser");
        int idUtente = -1;

        if (idUtenteObj instanceof Integer) {
            idUtente = (Integer) idUtenteObj;
        }

        if (idUtente <= 0) {
            response.sendRedirect("login.jsp");
            return;
        }

        BeanUtente utente = UtenteDAO.loadUserById(idUtente);
        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        ArrayList<Acquisto> ordini = OrdineDAO.loadAllOrdersByIdUtente(utente.getIdUtente());
        request.setAttribute("ordini", ordini);

        RequestDispatcher dispatcher = request.getRequestDispatcher("userLogged/ordini.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
