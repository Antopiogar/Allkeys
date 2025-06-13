package control;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.ArticoloDAO;
import model.BeanArticolo;

@WebServlet("/GestioneArticoliServlet")
@MultipartConfig
public class GestioneArticoliServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        JsonObject obj = new JsonObject();
        
        
        
        String action = request.getParameter("AdminAction");
        
        
        System.out.println("Action finale: " + action);
        
        if (action == null) {
            obj.addProperty("result", "failure");
            obj.addProperty("message", "Azione non specificata");
            out.write(gson.toJson(obj));
            out.close();
            return;
        }
        
        boolean success = false;
        
        if (action.equalsIgnoreCase("elimina")) {
            int idArticolo = Integer.parseInt(request.getParameter("idArticolo"));
            System.out.println("Elimino articolo ID: " + idArticolo);
            success = ArticoloDAO.deleteArticolo(idArticolo);
            
        } else if (action.equalsIgnoreCase("modifica")) {
            BeanArticolo articolo = new BeanArticolo();
            articolo.setIdArticolo(Integer.parseInt(request.getParameter("idArticolo")));
            articolo.setNome(request.getParameter("nome"));
            articolo.setDescrizione(request.getParameter("descrizione"));
            articolo.setPiattaforma(request.getParameter("piattaforma"));
            articolo.setPrezzo(Float.parseFloat(request.getParameter("prezzo")));
            
            System.out.println("Modifico articolo ID: " + articolo.getIdArticolo());
            success = ArticoloDAO.updateArticolo(articolo);
        }
        
        if (success) {
            obj.addProperty("result", "success");
        } else {
            obj.addProperty("result", "failure");
            obj.addProperty("message", "Errore nell'operazione");
        }
        
        out.write(gson.toJson(obj));
        out.close();
    }
}