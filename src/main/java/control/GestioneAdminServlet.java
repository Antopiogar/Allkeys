package control;

import java.io.IOException;
import model.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
                articolo.setLogo(ArticoloDAO.getNextLogo());
                articolo.setNome(request.getParameter("nome"));
                articolo.setPrezzo(Float.parseFloat(request.getParameter("prezzo")));
                articolo.setDescrizione(request.getParameter("descrizione"));
                articolo.setPiattaforma(request.getParameter("piattaforma"));
                //aggiungere salvataggio logo in tomcat.
                
                chiave.setCodice(request.getParameter("codice"));
                aggiunta = ChiaveDAO.saveKey(articolo, chiave);
                
                if (aggiunta == 1) {
                    response.sendRedirect(request.getContextPath() + "/adminLogged/aggiungiChiave.jsp?success=true");
                } else if(aggiunta ==-2) {
                    response.sendRedirect(request.getContextPath() + "/adminLogged/aggiungiChiave.jsp?error=true&duplicate=true");
                }
                else {
                    response.sendRedirect(request.getContextPath() + "/adminLogged/aggiungiChiave.jsp?error=true");
                }
            }
        }
    }
}
