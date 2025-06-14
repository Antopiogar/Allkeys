package control;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.*;

@WebServlet("/DeleteRecensioneServlet")
public class DeleteRecensioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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

        int idRec = -1;
        int idArticolo = -1;
        try {
            if (request.getParameter("idRec") != null) {
                idRec = Integer.parseInt(request.getParameter("idRec"));
            }
            if (request.getParameter("idArticolo") != null) {
                idArticolo = Integer.parseInt(request.getParameter("idArticolo"));
            }
        } catch (NumberFormatException e) {
            idRec = -1;
            idArticolo = -1;
        }
        

        boolean result = false;
        String message = "";

        if (idRec > 0) {
            result = RecensioneDAO.deleteRecensione(idRec);
            message = result ? "Recensione eliminata con successo." : "Errore durante l'eliminazione della recensione.";
        } else {
        	response.sendRedirect(request.getContextPath()+"/index.jsp");
        	return;
        }

        ArrayList<BeanRecensione> recensioni = RecensioneDAO.getRecensioniByIdArticolo(String.valueOf(idArticolo));
        BeanArticolo articoloInfo = ArticoloDAO.getArticoloById(String.valueOf(idArticolo));

        request.setAttribute("recensioni", recensioni);
        request.setAttribute("articoloInfo", articoloInfo);
        request.setAttribute("idUser", idUser);
        request.setAttribute("isAdmin", isAdmin);
        request.setAttribute("deleteMessage", message);

        RequestDispatcher rd = request.getRequestDispatcher("dettagliArticolo.jsp");
        rd.forward(request, response);
    }
}
