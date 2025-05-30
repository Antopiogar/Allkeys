package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;

@WebServlet("/DeleteRecensioneServlet")
public class DeleteRecensioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteRecensioneServlet() {
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

        request.setAttribute("result", result);
        request.setAttribute("recensioni", recensioni);
        request.setAttribute("articoloInfo", articoloInfo);
        request.setAttribute("idUser", idUser);
        request.setAttribute("isAdmin", isAdmin);
        request.setAttribute("deleteMessage", message);

        RequestDispatcher rd = request.getRequestDispatcher("dettagliArticolo.jsp");
        rd.forward(request, response);
    }
}
