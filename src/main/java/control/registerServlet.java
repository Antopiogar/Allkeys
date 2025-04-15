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

@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public registerServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BeanUtente user = new BeanUtente();
		String nome,cognome,cf,email,pass,dataHTML;
		boolean result;
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
		user.setPass(pass);
		UtenteDAO uDao = new UtenteDAO();
		result = uDao.register(user); 
		if(result) {
			response.sendRedirect("login.jsp");
		}
		else {
			response.sendRedirect("regiser.jsp");
		}
	}

}
