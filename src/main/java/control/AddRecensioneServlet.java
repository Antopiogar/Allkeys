package control;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

@WebServlet("/AddRecensioneServlet")
public class AddRecensioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddRecensioneServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        int idUser = -1;
        if (session.getAttribute("idUser") != null) {
            idUser = (int) session.getAttribute("idUser");
        }

        boolean isAdmin = false;
        if (session.getAttribute("isAdmin") != null) {
            isAdmin = (boolean) session.getAttribute("isAdmin");
        }

        String recensione = request.getParameter("recensione");
        int voto = -1;
        int idArticolo = -1;

        try {
            if (request.getParameter("voto") != null) {
                voto = Integer.parseInt(request.getParameter("voto"));
            }
            if (request.getParameter("idArticolo") != null) {
                idArticolo = Integer.parseInt(request.getParameter("idArticolo"));
            }
        } catch (NumberFormatException e) {
            voto = -1;
            idArticolo = -1;
        }

        if (idUser == -1 || recensione == null || recensione.trim().isEmpty() || voto == -1 || idArticolo == -1) {
            response.sendRedirect(request.getContextPath() + "/dettagliArticolo.jsp?idArticolo=" + idArticolo);
            return;
        }

        BeanRecensione rec = new BeanRecensione();
        BeanUtente utente = UtenteDAO.loadUserById(idUser);
        rec.setData(LocalDate.now());
        rec.setTesto(recensione);
        rec.setUtenteRecensione(utente);
        rec.setVoto(voto);

        boolean result = RecensioneDAO.saveRecensione(rec, idArticolo);

        ArrayList<BeanRecensione> recensioni = RecensioneDAO.getRecensioniByIdArticolo(String.valueOf(idArticolo));
        BeanArticolo articoloInfo = ArticoloDAO.getArticoloById(String.valueOf(idArticolo));

        request.setAttribute("result", result);
        request.setAttribute("recensioni", recensioni);
        request.setAttribute("articoloInfo", articoloInfo);
        request.setAttribute("idUser", idUser);
        request.setAttribute("isAdmin", isAdmin);

        RequestDispatcher rd = request.getRequestDispatcher("dettagliArticolo.jsp");
        rd.forward(request, response);
    }
}
