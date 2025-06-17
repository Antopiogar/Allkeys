package control;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.BeanCartaPagamento;
import model.CartaPagamentoDAO;
import model.UtenteDAO;

@WebServlet("/AddPaymentMethod")
public class AddPaymentMethodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sessione = request.getSession();
		Object objectID = sessione.getAttribute("idUser");
		Integer id = -1; 
		if (objectID != null) id = (Integer) objectID;
		String titolare= request.getParameter("titolare");
		String cvc = request.getParameter("cvc");
		String scadenza = request.getParameter("scadenza");
		String numeroCarta = (request.getParameter("numeroCarta"));
		
		
		System.out.println("cvc " + cvc + " titolare " + titolare + " nCarta " + numeroCarta + " " + numeroCarta.length());
		boolean ris = false;
		Gson g = new Gson();
		JsonObject obj = new JsonObject();
		if(cvc.length() == 3 && numeroCarta.length() == 16 && id!=-1) {
			BeanCartaPagamento carta = new BeanCartaPagamento();
			carta.setTitolare(titolare);
			carta.setCodiceCVC(cvc);
			carta.setScadenza(scadenza);
			carta.setnCarta(numeroCarta);
			ris = CartaPagamentoDAO.AddCartaPagamento(UtenteDAO.loadUserById(id), carta);
		}
		else {
			System.out.println("DATI SMINCHI");
		}
		if(ris) {
			obj.addProperty("result", "success");
		}
		else {
			obj.addProperty("result", "errore");
			obj.addProperty("message", "errore generico");
		}
		response.getWriter().write(g.toJson(obj));

		
		
	}

}
