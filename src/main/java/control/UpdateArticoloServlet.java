package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/UpdateArticolo")
@MultipartConfig

public class UpdateArticoloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateArticoloServlet() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idArticolo = request.getParameter("articolo");
        HttpSession session = request.getSession();
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if(idArticolo == null || (session != null && (isAdmin == null || isAdmin == false))) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        BeanArticolo articolo = ArticoloDAO.getArticoloById(idArticolo);
    	
    	ArrayList<BeanRecensione> recensioni = RecensioneDAO.getRecensioniByIdArticolo(idArticolo);

        
        request.setAttribute("articoloInfo", articolo);
        request.setAttribute("recensioni", recensioni);

       
        Integer idUser = (Integer) request.getSession().getAttribute("idUser");

        request.setAttribute("idUser", (idUser != null) ? idUser : -1);
        request.setAttribute("isAdmin", (isAdmin != null) ? isAdmin : false);
        
        ArrayList<String> piattaforme = ArticoloDAO.getPiattaforme();
    	request.setAttribute("piattaforme",piattaforme);

        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminLogged/editArticolo.jsp");
        dispatcher.forward(request, response);
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idArticolo = request.getParameter("articolo");
        HttpSession session = request.getSession();
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        boolean result = true;
        BeanArticolo articolo = ArticoloDAO.getArticoloById(idArticolo);
        if (articolo == null || articolo.getNome() == null || articolo.getPiattaforma() == null || 
            articolo.getInfo() == null || articolo.getPrezzo() < 0) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        // Gestione del file con controlli aggiuntivi
        Part filePart = request.getPart("fileInput");
        if(filePart != null) {
            // Controllo se è stato effettivamente selezionato un file
            if(filePart.getSize() > 0 && filePart.getSubmittedFileName() != null && 
               !filePart.getSubmittedFileName().isEmpty()) {
                
                String fileName = articolo.getLogo();
                String uploadPath = getServletContext().getRealPath("") + File.separator + "IMG" + File.separator + "loghi";
                File uploadDir = new File(uploadPath);
                
                // Controllo se la directory esiste, altrimenti la crea
                if (!uploadDir.exists()) {
                    if(!uploadDir.mkdirs()) {
                        System.err.println("Errore nella creazione della directory");
                        result = false;
                    }
                }
                
                // Controllo se il file esiste già e lo elimino se necessario
                File existingFile = new File(uploadPath + File.separator + fileName);
                if(existingFile.exists()) {
                    if(!existingFile.delete()) {
                        System.err.println("Errore nell'eliminazione del file esistente");
                        result = false;
                    }
                }
                
                // Scrivo il nuovo file
                try (InputStream fileContent = filePart.getInputStream();
                    FileOutputStream fos = new FileOutputStream(uploadPath + File.separator + fileName)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileContent.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    System.err.println("Errore nella scrittura del file: " + e.getMessage());
                    result = false;
                }
            } else {
                System.err.println("Nessun file valido selezionato");
            }
        }
        
        
        
        articolo.setDescrizione(request.getParameter("descrizione"));
        articolo.setNome(request.getParameter("nome"));
        String prezzoArticolo = request.getParameter("prezzo");
        articolo.setPrezzo(Float.parseFloat(prezzoArticolo));
        articolo.setPiattaforma(request.getParameter("piattaforma"));
        
        if(result)
        	result = ArticoloDAO.updateArticolo(articolo); 
        Gson g = new Gson();
        JsonObject obj = new JsonObject();
        if(result) {
            obj.addProperty("result", "success");
        } else {
            obj.addProperty("result", "errore");
            obj.addProperty("message", "errore generico");
        }
        response.getWriter().write(g.toJson(obj));
    }
        
    

}
