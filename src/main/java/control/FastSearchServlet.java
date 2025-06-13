package control;

import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.ArticoloDAO;
import model.BeanArticolo;

/**
 * Servlet implementation class FastSearchServlet
 */
@WebServlet("/FastSearch")
public class FastSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	public static String articoliToJson(ArrayList<BeanArticolo> articoli) {
	    JsonObject root = new JsonObject();
	    root.addProperty("result", "successo");
	    
	    // Creazione dell'array per gli articoli
	    JsonArray articoliArray = new JsonArray();
	    
	    for (BeanArticolo articolo : articoli) {
	        JsonObject articoloJson = new JsonObject();
	        articoloJson.addProperty("id", articolo.getIdArticolo());
	        articoloJson.addProperty("nome", articolo.getNome());
	        articoloJson.addProperty("prezzo", articolo.getPrezzo());
	        
	        // Aggiungi l'articolo all'array
	        articoliArray.add(articoloJson);
	    }
	    
	    // Aggiungi l'array all'oggetto principale
	    root.add("articoli", articoliArray);
	    
	    return new Gson().toJson(root);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String value = request.getParameter("fastSearch");
		String jsonResult = """
				{ "result" : "errore"}
				""".trim();
		if(value != null) {
			ArrayList<BeanArticolo> lightList = ArticoloDAO.fastSearch(value);
			if(!lightList.isEmpty()) {
				jsonResult = articoliToJson(lightList);
			}

		}
		response.getWriter().print(jsonResult);
	}
	

	
}
