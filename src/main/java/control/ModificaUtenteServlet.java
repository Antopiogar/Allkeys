package control;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.BeanUtente;
import model.UtenteDAO;

@WebServlet("/ModificaUtenteServlet")
public class ModificaUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
			response.sendRedirect("index.jsp");
		} catch (IOException e) {
		}
    }

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
