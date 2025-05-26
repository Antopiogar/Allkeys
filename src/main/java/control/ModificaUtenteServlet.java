package control;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BeanUtente;
import model.UtenteDAO;

/**
 * Servlet implementation class ModificaUtenteServlet
 */
@WebServlet("/ModificaUtenteServlet")
public class ModificaUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaUtenteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Object idUtente = request.getSession().getAttribute("idUser");
		Object isAdmin = request.getSession().getAttribute("isAdmin");
		boolean adminB = false;
		if(idUtente == null) {
			response.sendRedirect("../login.jsp");
		}
		if(isAdmin == null) {
			adminB = false;
		}
		else {
			adminB = (boolean)isAdmin;
		}
		BeanUtente user = new BeanUtente();
		String nome,cognome,cf,email,dataHTML;
		String result;
		nome = request.getParameter("nome");
		cognome = request.getParameter("cognome");
		cf = request.getParameter("cf");
		email = request.getParameter("email");
		dataHTML = request.getParameter("dataN");
		LocalDate data = LocalDate.parse(dataHTML);
		user.setIdUtente((int) idUtente);
		user.setNome(nome);
		user.setCognome(cognome);
		user.setCf(cf);
		user.setDataNascita(data);
		user.setEmail(email);
		
		result = UtenteDAO.modificaUtenteById(user);
		request.getSession().setAttribute("risultatoModifica",result);
		request.getSession().setAttribute("User",user);
		request.getSession().setAttribute("Nome",user.getNome());
		if(!adminB) { //se è un utente normale
		response.sendRedirect("userLogged/profilo.jsp");
		}
		else {
			response.sendRedirect("adminLogged/profiloAdmin.jsp");
		}
	}

}
