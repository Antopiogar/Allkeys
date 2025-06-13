package control;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;

@WebServlet("/AcquistaServlet")
public class AcquistaServlet extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 1L;

    public AcquistaServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Object nome = session.getAttribute("Nome");
        Object idUserObj = session.getAttribute("idUser");

        if (nome == null || idUserObj == null || (int) idUserObj == -1) {
            session.setAttribute("ConfermaOrdineRedirect", "true");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int idUser = (int) idUserObj;
        ArrayList<BeanCartaPagamento> carte = CartaPagamentoDAO.loadCartaPagamentoByIdUtente(idUser);

        request.setAttribute("carte", carte);
        request.getRequestDispatcher("/userLogged/acquista.jsp").forward(request, response);
    }
}
