package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import model.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/UpdateArticolo")
public class UpdateArticoloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateArticoloServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idArticolo = request.getParameter("articolo");
        HttpSession session = request.getSession();
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (idArticolo == null || (session != null && (isAdmin == null || isAdmin == false))) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        BeanArticolo articolo = ArticoloDAO.getArticoloById(idArticolo);
        if (articolo == null || articolo.getNome() == null || articolo.getPiattaforma() == null || articolo.getInfo() == null || articolo.getPrezzo() < 0) {
            
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        String edit = request.getParameter("edit");
        if(edit == null) edit = "view";
        if(edit.equals("view")) {
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
        else if(edit.equals("update")) {
        	
        	articolo.setDescrizione(request.getParameter("descrizioneArticolo"));
        	articolo.setNome(request.getParameter("nomeArticolo"));
        	String prezzoArticolo = request.getParameter("prezzoArticolo");
        	articolo.setPrezzo(Float.parseFloat(prezzoArticolo));
        	articolo.setPiattaforma(request.getParameter("piattaformaArticolo"));
        	Boolean result = ArticoloDAO.updateArticolo(articolo); 
        	
        	
        	
        	//prende la parte del file
            Part filePart = request.getPart("fileInput");
            String fileName = articolo.getLogo();
            articolo.setLogo(fileName);

            //ottiene il path assoluto della cartella IMG/loghi in webapp
            String uploadPath = getServletContext().getRealPath("") + File.separator + "IMG" + File.separator + "loghi";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();  // Crea la cartella se non esiste

            //scrive il file
            try (InputStream fileContent = filePart.getInputStream();
                 FileOutputStream fos = new FileOutputStream(uploadPath + File.separator + fileName)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileContent.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }
            
            
        	
        	request.setAttribute("result", result);
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/adminLogged/editArticolo.jsp");
            dispatcher.forward(request, response);
        }
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
