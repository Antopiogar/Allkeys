package control;

import model.Carrello;
import model.OrdineDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ConfermaOrdineServlet")
public class ConfermaOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public ConfermaOrdineServlet() {
		super();
	}

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
                System.out.println("INIZIO ORDINE");
                
                status = OrdineDAO.ConfirmOrder(idUtente, idCarta);
                System.out.println("FINE ORDINE");
                // Svuota il carrello solo se ordine riuscito
                if (status == 0) {
                	
                    session.setAttribute("cart", new Carrello());
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
