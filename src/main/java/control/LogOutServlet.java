package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogOutServlet
 */
@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public LogOutServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		String id = (String) request.getSession().getAttribute("Nome");

		if(id != null) {
			request.getSession().invalidate();
			response.sendRedirect("login.jsp");
		}
	}

}
