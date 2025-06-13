package control;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.BeanRecensione;
import model.RecensioneDAO;

@WebServlet("/GestioneRecensioniServlet")
public class GestioneRecensioniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	System.out.println("GESTIONE");
    	String stelleP =  req.getParameter("voto");
    	System.out.println(stelleP);
		int nStelle = Integer.parseInt(stelleP);
    	String testo = req.getParameter("testo");
    	int idRecensione = Integer.parseInt( req.getParameter("idRecensione"));
    	BeanRecensione rec = new BeanRecensione();
    	boolean ris = false;
    	rec.setData(LocalDate.now());
    	rec.setTesto(testo);
    	rec.setVoto(nStelle);
    	rec.setIdRecensione(idRecensione);
    	ris = RecensioneDAO.updateRecensione(rec);
    	if(ris) {
    		Gson g = new Gson();
    		JsonObject obj = new JsonObject();
    		obj.addProperty("result", "success");
    		resp.getWriter().write(g.toJson(obj));
    	}
    	else {
    		Gson g = new Gson();
    		JsonObject obj = new JsonObject();
    		obj.addProperty("result", "failure");
    		obj.addProperty("message", "Errore nella modifica della recensione");
    		resp.getWriter().write(g.toJson(obj));
    	}
    	
    }
}
