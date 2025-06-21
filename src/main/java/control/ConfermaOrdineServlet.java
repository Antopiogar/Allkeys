package control;

import model.Carrello;
import model.OrdineDAO;
import model.UtenteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ConfermaOrdineServlet")
public class ConfermaOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int status = -1; // valore di default: errore generico
        int idUtente = -1;

        Object ido = session.getAttribute("idUser");
        if (ido instanceof Integer) {
            idUtente = (Integer) ido;
        }

        String idCartaStr = request.getParameter("idCartaSelezionata");

        if (idCartaStr != null && !idCartaStr.isEmpty()) {
            try {
                int idCarta = Integer.parseInt(idCartaStr);

                // Chiamata al DAO per confermare l'ordine
                
                status = OrdineDAO.ConfirmOrder(idUtente, idCarta);
                
                // Svuota il carrello solo se ordine riuscito
                if (status == 0) {
                	
                    session.setAttribute("cart", new Carrello());
                    OrdineDAO.CreateOrder(null, UtenteDAO.loadUserById(idUtente));
                }

            } catch (NumberFormatException e) {
                // idCarta non valido
                status = -2;
            }
        } else {
            // Carta non selezionata
            status = -3;
        }

        request.setAttribute("status", status);
        request.getRequestDispatcher("userLogged/esitoOrdine.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
			response.sendRedirect("index.jsp");
		} catch (IOException e) {
		}
    }

}
