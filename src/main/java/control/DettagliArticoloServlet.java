package control;

import java.io.IOException;
import java.util.ArrayList;

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
        if (idArticolo == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        BeanArticolo articolo = ArticoloDAO.getArticoloById(idArticolo);
        if (articolo == null) {
            
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        ArrayList<BeanRecensione> recensioni = RecensioneDAO.getRecensioniByIdArticolo(idArticolo);

        
        request.setAttribute("articoloInfo", articolo);
        request.setAttribute("recensioni", recensioni);

       
        Integer idUser = (Integer) request.getSession().getAttribute("idUser");
        Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");

        request.setAttribute("idUser", (idUser != null) ? idUser : -1);
        request.setAttribute("isAdmin", (isAdmin != null) ? isAdmin : false);

        
        RequestDispatcher dispatcher = request.getRequestDispatcher("dettagliArticolo.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
