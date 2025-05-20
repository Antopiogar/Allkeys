package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Carrello;
import model.ComposizioneDAO;
import model.OrdineDAO;
import model.UtenteDAO;

@WebServlet("/LoginAdminServlet")
public class LoginAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginAdminServlet() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email,pass;
		int idUser =-1;
		String nomeUser;
		UtenteDAO uDao = new UtenteDAO();
		email = request.getParameter("email");
		pass = UtenteDAO.toSHA256(request.getParameter("password"));
		idUser = uDao.loginAdmin(email, pass);
		if(idUser == -1) {
			System.out.println("idUser = "+ idUser);

			request.getSession().setAttribute("LoginFallito", true);
			System.out.println("password errata admin");
			response.sendRedirect(request.getContextPath() + "/loginAdmin.jsp");
		}
		else {
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
