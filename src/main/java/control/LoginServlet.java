package control;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Carrello;
import model.ComposizioneDAO;
import model.OrdineDAO;
import model.UtenteDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	
	
    public LoginServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email,pass;
		int idUser =-1;
		String nomeUser;
		UtenteDAO uDao = new UtenteDAO();
		email = request.getParameter("email");
		pass = UtenteDAO.toSHA256(request.getParameter("password"));
		idUser = uDao.login(email, pass);
		if(idUser == -1) {
			System.out.println("idUser = "+ idUser);

			request.getSession().setAttribute("LoginFallito", true);
			response.sendRedirect("login.jsp");
		}
		else {
			Carrello c= (Carrello) request.getSession().getAttribute("cart");
			Carrello DB = OrdineDAO.LoadCarrelByUser(idUser);
			System.out.println(DB);
			if(c == null) {
				c = DB;
			}
			else {
				c= Carrello.MergeCarrelli(DB,c);
				ComposizioneDAO.SincronizzaCarrelli(idUser, c);
			}
			request.getSession().setAttribute("cart", c);
			
			nomeUser = uDao.loadNameById(idUser);
			request.getSession().setAttribute("idUser", idUser);
			request.getSession().setAttribute("Nome", nomeUser);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("userLogged/profilo.jsp");
		    dispatcher.forward(request, response);
		}
	
		
		
		doGet(request, response);
	}

}
