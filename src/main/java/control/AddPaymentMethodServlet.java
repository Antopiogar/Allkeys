package control;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BeanCartaPagamento;
import model.CartaPagamentoDAO;
import model.UtenteDAO;

@WebServlet("/AddPaymentMethodServlet")
public class AddPaymentMethodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddPaymentMethodServlet() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sessione = request.getSession();
		Object objectID = sessione.getAttribute("idUser");
		Integer id = -1;
		if (objectID != null) id = (Integer) objectID;
		String titolare= request.getParameter("titolare");
		String cvc = (request.getParameter("cvc"));

		String scadenzaStr = request.getParameter("scadenza");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate scadenza = LocalDate.parse(scadenzaStr, formatter);
		
		String numeroCarta = (request.getParameter("numeroCarta"));
		
		if(cvc.length() == 3 && numeroCarta.length() == 16 && id!=-1) {
			BeanCartaPagamento carta = new BeanCartaPagamento();
			carta.setTitolare(titolare);
			carta.setCodiceCVC(cvc);
			carta.setScadenza(scadenza);
			carta.setnCarta(numeroCarta);
			CartaPagamentoDAO.AddCartaPagamento(UtenteDAO.loadUserById(id), carta);
		}
		response.sendRedirect(request.getContextPath() + "/userLogged/profilo.jsp");
		
		
	}

}
