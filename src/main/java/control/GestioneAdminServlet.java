package control;

import java.io.IOException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import model.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet("/GestioneAdminServlet")
public class GestioneAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GestioneAdminServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int aggiunta = -1;
        String AdminAction = request.getParameter("AdminAction");
        if (AdminAction == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            BeanChiave chiave = new BeanChiave();
            BeanArticolo articolo = new BeanArticolo();

            if (AdminAction.equals("addArticolo")) {
            	ArrayList<String> piattaforme = ArticoloDAO.getPiattaforme();
            	request.setAttribute("piattaforme",piattaforme);
            	request.getRequestDispatcher("/adminLogged/aggiungiArticolo.jsp").forward(request, response);
            }
            else if (AdminAction.equals("addKey")) {
            	ArrayList<BeanArticolo> articoli = ArticoloDAO.loadAllAvailableArticles();
            	request.setAttribute("articoli",articoli);
            	request.getRequestDispatcher("/adminLogged/aggiungiChiave.jsp").forward(request, response);
            }
            else if (AdminAction.equals("addSettedKey")) {
                chiave.setCodice(request.getParameter("codice"));
                String idArticoloStr = request.getParameter("idArticolo");
                articolo = ArticoloDAO.getArticoloById(idArticoloStr);
                aggiunta = ChiaveDAO.saveKey(articolo,chiave);
                
                if (aggiunta ==1) {
                    response.sendRedirect(request.getContextPath() + "/adminLogged/aggiungiChiave.jsp?success=true");
                } else if(aggiunta ==-2) {
                    response.sendRedirect(request.getContextPath() + "/adminLogged/aggiungiChiave.jsp?error=true&duplicate=true");
                }
                else {
                    response.sendRedirect(request.getContextPath() + "/adminLogged/aggiungiChiave.jsp?error=true");
                }
            }
            else if (AdminAction.equals("addSettedArticolo")) {
                //prende i dati del form
                articolo.setNome(request.getParameter("nome"));
                articolo.setPrezzo(Float.parseFloat(request.getParameter("prezzo")));
                articolo.setDescrizione(request.getParameter("descrizione"));
                articolo.setPiattaforma(request.getParameter("piattaforma"));

                //prende la parte del file
                Part filePart = request.getPart("immagine");
                String fileName = ArticoloDAO.getNextLogo();  //Es: logo_005.png
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

                // Salva anche la chiave
                chiave.setCodice(request.getParameter("codice"));
                aggiunta = ChiaveDAO.saveKey(articolo, chiave);

                // Redirect finale
                if (aggiunta == 1) {
                    response.sendRedirect(request.getContextPath() + "/adminLogged/aggiungiChiave.jsp?success=true");
                } else if(aggiunta == -2) {
                    response.sendRedirect(request.getContextPath() + "/adminLogged/aggiungiChiave.jsp?error=true&duplicate=true");
                } else {
                    response.sendRedirect(request.getContextPath() + "/adminLogged/aggiungiChiave.jsp?error=true");
                }
            }
        }
    }
}
