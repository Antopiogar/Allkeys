package control;

import java.io.IOException;
import java.util.ArrayList;

import model.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateArticolo")
public class UpdateArticoloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateArticoloServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idArticolo = request.getParameter("articolo");
        if (idArticolo == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        BeanArticolo articolo = ArticoloDAO.getArticoloById(idArticolo);
        if (articolo == null || articolo.getNome() == null || articolo.getPiattaforma() == null || articolo.getInfo() == null || articolo.getPrezzo() < 0) {
            
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

        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminLogged/editArticolo.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
