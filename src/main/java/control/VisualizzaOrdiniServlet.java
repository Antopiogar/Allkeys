package control;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
