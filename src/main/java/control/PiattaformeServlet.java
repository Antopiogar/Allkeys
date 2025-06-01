package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.ArticoloDAO;

/**
 * Servlet implementation class PiattaformeServlet
 */
@WebServlet("/PiattaformeServlet")
public class PiattaformeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		ArrayList<String> piattaforme = ArticoloDAO.getPiattaforme(); 
		String jsonResult = """
				{ "result" : "errore"}
				""".trim();

		if(!piattaforme.isEmpty()) {
			jsonResult = PiattaformeToJson(piattaforme);
		}

	
		response.getWriter().print(jsonResult);
		
	}

	private String PiattaformeToJson(ArrayList<String> piattaforme) {
		JsonObject root = new JsonObject();
	    root.addProperty("result", "successo");
	    
	    // Creazione dell'array per gli articoli
	    JsonArray piattaformeArray = new JsonArray();
	    
	    for (String piattaforma : piattaforme) {
	        piattaformeArray.add(piattaforma);
	    }
	    
	    // Aggiungi l'array all'oggetto principale
	    root.add("piattaforme", piattaformeArray);
	    
	    return new Gson().toJson(root);
	}


}
