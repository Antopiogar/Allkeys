package control;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.UtenteDAO;

@WebServlet("/CheckEmailServlet")
public class CheckEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		JsonObject root = new JsonObject();
		System.out.println(email);
		boolean isOnDb = UtenteDAO.checkEmailOnDB(email);
		System.out.println(isOnDb);

		if(!isOnDb) {
		    root.addProperty("result", "successo");
		    root.addProperty("exists", false);
		}
		else {
			root.addProperty("result", "successo");
		    root.addProperty("exists", true);
		}
		Gson g = new Gson();
	    response.getWriter().print(g.toJson(root).toString());
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
			response.sendRedirect("index.jsp");
		} catch (IOException e) {
		}
    }


}
