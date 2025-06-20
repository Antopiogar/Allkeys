package control;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.*;
import java.util.ArrayList;

@WebServlet("/VisualizzaOrdiniServlet")
public class VisualizzaOrdiniServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int idUser = -1;
        Object idUtenteObj = session.getAttribute("idUser");
        int idUtente = -1;
        if(request.getParameter("idUser")!= null){
        	idUser = Integer.parseInt(request.getParameter("idUser"));
        }
        if(idUser == -1) {
            if (idUtenteObj instanceof Integer) {
                idUtente = (Integer) idUtenteObj;
            }
        }
        else {
        	idUtente = idUser;
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


        String dataInizioStr = request.getParameter("dataInizio");
        String dataFineStr = request.getParameter("dataFine");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        LocalDate dataInizio = (dataInizioStr != null && !dataInizioStr.isEmpty())
                ? LocalDate.parse(dataInizioStr, formatter)
                : LocalDate.of(2000, 1, 1);

        LocalDate dataFine = (dataFineStr != null && !dataFineStr.isEmpty())
                ? LocalDate.parse(dataFineStr, formatter)
                : LocalDate.now();

        LocalDateTime t1 = dataInizio.atStartOfDay(); // 00:00:00
        LocalDateTime t2 = dataFine.atTime(23, 59, 59); // 23:59:59


        ArrayList<Acquisto> acquisti = OrdineDAO.loadOrdersByIdUserAndTime(idUtente, t1, t2);


        session.setAttribute("ordini", acquisti);


        request.setAttribute("dataInizio", dataInizio.toString());
        request.setAttribute("dataFine", dataFine.toString());


        RequestDispatcher dispatcher = request.getRequestDispatcher("userLogged/ordini.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
