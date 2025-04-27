package control;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Acquisto;
import model.ArticoliCarrello;
import model.ArticoloDAO;
import model.BeanArticolo;
import model.BeanCartaPagamento;
import model.BeanChiave;
import model.BeanUtente;

import model.Carrello;
import model.CartaPagamentoDAO;
import model.ChiaveDAO;
import model.ComposizioneDAO;
import model.DBConnection;
import model.OrdineDAO;
import model.UtenteDAO;

/**
 * Servlet implementation class TestingServlet
 */
@WebServlet("/TestingServlet")
public class TestingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;  
    
    public TestingServlet() {
        super();
        
    }


	@SuppressWarnings("unused")

	private void testUserById() {
		UtenteDAO utenteDao = new UtenteDAO();
		BeanUtente user = UtenteDAO.loadUserById(1);
		System.out.println(user);
	}
	
	@SuppressWarnings("unused")

	private void testAllArticles() {
		ArticoloDAO articoloDao= new ArticoloDAO();
		ArrayList<BeanArticolo> articoli = ArticoloDAO.loadAllDistinctArticles();
		System.out.println(articoli);
	}

	
	@SuppressWarnings("unused")
	private void testCarrello() {
		ArticoloDAO articoloDao= new ArticoloDAO();
		ArrayList<BeanArticolo> articoli = articoloDao.loadAllDistinctArticles();
		int qta;
		
		Carrello carrello = new Carrello();
		carrello.AddArticolo(articoli.get(0)); //restituisce true se l'inserimento va a buon fine
		carrello.AddArticolo(articoli.get(1)); 
		carrello.removeArticolo(articoli.get(0)); //restituisce true se l'elemento viene rimosso, se ha più quantità le rimuove tutte
		carrello.setQta(articoli.get(1), 5); //restituisce true se riesce ad impostare la quantità<qta> al prodotto <art> 
		qta = carrello.getQta(articoli.get(1));
		System.out.println("quantità articolo = " +qta);
		System.out.println(carrello);
		
		
		
		
	}
	@SuppressWarnings("unused")

	private void testLogin() {
		UtenteDAO uDao = new UtenteDAO();
		System.out.println(uDao.login("mario.rossi@email.com", "admin"));
	}
	
	
	@SuppressWarnings("unused")

	private void testCreazioneOrdine() {
		boolean ris = false;
		ArrayList<ArticoliCarrello> carrello = new ArrayList<ArticoliCarrello>();
		carrello.add(new ArticoliCarrello(ArticoloDAO.getArticoloById("1")));
		BeanUtente user = UtenteDAO.loadUserById(1);
		ris = OrdineDAO.CreateOrder(carrello, user);
		if(ris)
			System.out.println("Creazione a buon fine");
		else
			System.out.println("BOOM");
	}
	
	@SuppressWarnings("unused")
	private void testAggiuntaCarta() {
		boolean ris = false;
		BeanCartaPagamento carta = new BeanCartaPagamento();
		carta.setIdCarta(1001);
        carta.setTitolare("Antonio Rossi");
        carta.setnCarta("1234567812345678");
        carta.setScadenza(LocalDate.of(2026, 5, 20)); 
        carta.setCodiceCVC("123");
		BeanUtente user = UtenteDAO.loadUserById(1);
		ris = CartaPagamentoDAO.AddCartaPagamento(user, carta);
		if(ris)
			System.out.println("Carta creata");
		else
			System.out.println("BOOM");
	}
	
	@SuppressWarnings("unused")
	private void testCarrelloUtente() {

		
		Carrello c = OrdineDAO.LoadCarrelByUser(1);
		if(c.isEmpty())
			System.out.println("Carrello vuoto/BOOOM");
		else if(c == null)
			System.out.println("BOOM");
		else
			System.out.println(c);
	}
	
	@SuppressWarnings("unused")
	private void testErroriGetIdCarrello() {
		int idUtente = 1;
		int idCarrello = OrdineDAO.getIdCarrello(idUtente);
		
		System.out.println(idCarrello);
		Carrello c2 = new Carrello();
		c2.AddArticolo(ArticoloDAO.getArticoloById("1"));
		Connection con = DBConnection.getConnection();
		ComposizioneDAO.AggiungiProdotti(con, c2.getArticoli(), idCarrello,idUtente);
		Carrello c = OrdineDAO.LoadCarrelByUser(1);

		DBConnection.releseConnection(con);
		System.out.println(c);
	
	}
	
	@SuppressWarnings("unused")
	private void testConfermaOrdine() {
		int ris = OrdineDAO.ConfirmOrder(1,1);
		if(ris==0)
			System.out.println("Conferma a buon fine");
		else if(ris>0)
			System.out.println("prodotto con id " +ris + " NON DISPONIBILE IN QUEESTA QUANTITA");
		else
			System.out.println("BOOM");
	}
	@SuppressWarnings("unused")

	private void TestCaricaAcquisti() {
		ArrayList<Acquisto> acquisti = OrdineDAO.loadAllOrdersByIdUtente(1);
		System.out.println(acquisti);
	}
	
	@SuppressWarnings("unused")
	private void TestCaricaChiave() {
		ArrayList<BeanChiave> chiavi = new ArrayList<>();
		chiavi = ChiaveDAO.loadKeysByOrderId(1);
		System.out.println(chiavi);
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TestCaricaChiave();
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}