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
		String AdminAction = request.getParameter("AdminAction");
		if (AdminAction == null) response.sendRedirect(request.getContextPath() + "/index.jsp");
		else {
			if(AdminAction.equals("addArticolo")) response.sendRedirect(request.getContextPath() + "/adminLogged/aggiungiArticolo.jsp");
			else if(AdminAction.equals("addKey")) response.sendRedirect(request.getContextPath() + "/adminLogged/aggiungiChiave.jsp");
			else if(AdminAction.equals("addSettedKey")) {
				BeanChiave chiave = new BeanChiave();
				chiave.setCodice(request.getParameter("codice"));
				//CONTINUARE CON ID GIOCO.....
			}
			else if(AdminAction.equals("addSettedArticolo")) {
				BeanArticolo articolo = new BeanArticolo();
				articolo.setNome(request.getParameter("nome"));
				articolo.setPrezzo(Float.parseFloat(request.getParameter("prezzo")));
				articolo.setDescrizione(request.getParameter("descrizione")); //DA IMPLEMENTARE
			}
		}
	}

}
