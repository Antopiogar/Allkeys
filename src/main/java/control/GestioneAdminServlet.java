package control;

import java.io.IOException;
import model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GestioneAdminServlet")
public class GestioneAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GestioneAdminServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean aggiunta = false;
		String AdminAction = request.getParameter("AdminAction");
		if (AdminAction == null) response.sendRedirect(request.getContextPath() + "/index.jsp");
		else {
			BeanChiave chiave = new BeanChiave();
			BeanArticolo articolo = new BeanArticolo();
 
			if(AdminAction.equals("addArticolo")) response.sendRedirect(request.getContextPath() + "/adminLogged/aggiungiArticolo.jsp");
			else if(AdminAction.equals("addKey")) response.sendRedirect(request.getContextPath() + "/adminLogged/aggiungiChiave.jsp");
			else if(AdminAction.equals("addSettedKey")) {
				chiave.setCodice(request.getParameter("codice"));
				//CONTINUARE CON ID GIOCO.....
			}
			else if(AdminAction.equals("addSettedArticolo")) {
				articolo.setLogo(ArticoloDAO.getNextLogo());
				articolo.setNome(request.getParameter("nome"));
				articolo.setPrezzo(Float.parseFloat(request.getParameter("prezzo")));
				articolo.setDescrizione(request.getParameter("descrizione")); //DA IMPLEMENTARE
			}
			
			//funziona per entrambi i casi, se l'articolo esiste già allora avrà id
			//se non lo ha vuol dire che deve essere creato e di conseguenza lo crea
			aggiunta = ChiaveDAO.saveKey(articolo, chiave);
			//va a buon fine l'aggiunta
			if(aggiunta) {
				
			}
			//muore qualcosa
			else {
				
			}

		}
		
	}

}
