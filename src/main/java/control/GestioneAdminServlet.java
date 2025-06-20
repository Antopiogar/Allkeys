package control;

import java.io.IOException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileOutputStream;
import model.*;

import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet("/GestioneAdmin")
public class GestioneAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
			response.sendRedirect("index.jsp");
		} catch (IOException e) {
		}
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
                
              //feedback
                Gson g = new Gson();
                JsonObject obj = new JsonObject();
                
                if (aggiunta ==1) { //va a buon fine
                	obj.addProperty("result", "success");
                } else if(aggiunta ==-2) { //chiave duplicata
                	obj.addProperty("result", "failure");
                	obj.addProperty("message", "Chiave duplicata");
                	
                }
                else { //altro
                	obj.addProperty("result", "failure");
                	obj.addProperty("message", "Errore generico");
                }
                response.getWriter().write(g.toJson(obj));
                
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

                //feedback
                Gson g = new Gson();
                JsonObject obj = new JsonObject();
                
                if (aggiunta ==1) { //va a buon fine
                	obj.addProperty("result", "success");
                } else if(aggiunta ==-2) { //chiave duplicata
                	obj.addProperty("result", "failure");
                	obj.addProperty("message", "Chiave duplicata");
                	
                }
                else { //altro
                	obj.addProperty("result", "failure");
                	obj.addProperty("message", "Errore generico");
                }
                response.getWriter().write(g.toJson(obj));
                
            }
            else if(AdminAction.equals("viewAllUsers")) {
            	ArrayList<BeanUtente> users = UtenteDAO.getUsers();
            	request.setAttribute("users", users);
            	request.setAttribute("redirected",true);
            	RequestDispatcher rd = request.getRequestDispatcher("/adminLogged/viewAllUsers.jsp");
            	rd.forward(request, response);
            }
            
            else if(AdminAction.equals("viewAllUsersOrders")) {
            	String dataInizioStr = request.getParameter("dataInizio");
                String dataFineStr = request.getParameter("dataFine");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


                LocalDate dataInizio = (dataInizioStr != null && !dataInizioStr.isEmpty())
                        ? LocalDate.parse(dataInizioStr, formatter)
                        : LocalDate.of(2000, 1, 1);

                LocalDate dataFine = (dataFineStr != null && !dataFineStr.isEmpty())
                        ? LocalDate.parse(dataFineStr, formatter)
                        : LocalDate.now();

                LocalDateTime t1 = dataInizio.atStartOfDay(); // 00:00:00
                LocalDateTime t2 = dataFine.atTime(23, 59, 59); // 23:59:59


                ArrayList<Acquisto> acquisti = OrdineDAO.loadOrdersByTime(t1, t2);
                System.out.println(acquisti);

                request.getSession().setAttribute("ordini", acquisti);
                request.setAttribute("dataInizio", dataInizio.toString());
                request.setAttribute("dataFine", dataFine.toString());	
            	request.setAttribute("redirected",true);
            	RequestDispatcher rd = request.getRequestDispatcher("/adminLogged/allOrders.jsp");
            	rd.forward(request, response);
            }
        }
    }
}
