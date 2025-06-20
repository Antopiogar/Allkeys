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

@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
			response.sendRedirect("index.jsp");
		} catch (IOException e) {
		}
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BeanUtente user = new BeanUtente();
		String nome,cognome,cf,email,pass,dataHTML;
		String result;
		nome = request.getParameter("nome");
		cognome = request.getParameter("cognome");
		cf = request.getParameter("cf");
		email = request.getParameter("email");
		pass = request.getParameter("password");
		dataHTML = request.getParameter("dataN");
        LocalDate data = LocalDate.parse(dataHTML);
		user.setNome(nome);
		user.setCognome(cognome);
		user.setCf(cf);
		user.setDataNascita(data);
		user.setEmail(email);
		user.setPass(UtenteDAO.toSHA256(pass));
		
		
		UtenteDAO uDao = new UtenteDAO();
		result = uDao.register(user); 
		if(result.equalsIgnoreCase("Utente gia registrato")) {
			request.getSession().setAttribute("Email", "esitente");
			response.sendRedirect("login.jsp");
		}
		else if(result.equalsIgnoreCase("Registrazione eseguita")) {
			request.getSession().setAttribute("Email", "non esistente");
			response.sendRedirect("login.jsp");
		}
		else {
			request.getSession().setAttribute("LoginFallito", true);
			response.sendRedirect("register.jsp");
		}
		
	}

}
