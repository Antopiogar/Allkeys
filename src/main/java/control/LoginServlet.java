package control;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Carrello;
import model.ComposizioneDAO;
import model.OrdineDAO;
import model.UtenteDAO;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
			response.sendRedirect("index.jsp");
		} catch (IOException e) {
		}
    }


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email,pass;
		int idUser =-1;
		String nomeUser;
		UtenteDAO uDao = new UtenteDAO();
		email = request.getParameter("email");
		pass = UtenteDAO.toSHA256(request.getParameter("password"));
		idUser = uDao.login(email, pass);
		
		
		//Login user
		if(idUser == 1) {
			
			Carrello c= (Carrello) request.getSession().getAttribute("cart");
			Carrello DB = OrdineDAO.LoadCarrelByUser(idUser);
			
			System.out.println("DB"+DB);
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
			request.getSession().setAttribute("User", UtenteDAO.loadUserById(idUser));
			request.getSession().setAttribute("isAdmin", false);
			response.sendRedirect("userLogged/profilo.jsp");

		}
		//login admin
		else {
			
			idUser = uDao.loginAdmin(email, pass);
			
			if(idUser == -1) {
				//login errato
				System.out.println("idUser = "+ idUser);
	
				request.getSession().setAttribute("LoginFallito", true);
				System.out.println("password errata admin");
				response.sendRedirect(request.getContextPath() + "/login.jsp");
			}
			else {
				//login Admin
				Carrello c= (Carrello) request.getSession().getAttribute("cart");
				Carrello DB = OrdineDAO.LoadCarrelByUser(idUser);
				
				System.out.println("DB"+DB);
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
				request.getSession().setAttribute("User", UtenteDAO.loadUserById(idUser));
				request.getSession().setAttribute("isAdmin", true);
				response.sendRedirect("adminLogged/profiloAdmin.jsp");
	
			    
				
			}
		}
	}

}
