package control;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

@WebServlet("/AddRecensioneServlet")
public class AddRecensioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddRecensioneServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int idUser = -1;
		int voto = -1;
		int idArticolo = -1;
		boolean result = false;
		if(session.getAttribute("idUser")!=null) idUser = (int) session.getAttribute("idUser");
		String recensione = null;
		if(request.getParameter("recensione")!= null) recensione = request.getParameter("recensione");
		if(request.getParameter("voto")!= null) voto = Integer.parseInt(request.getParameter("voto"));
		if(request.getParameter("idArticolo")!= null) idArticolo = Integer.parseInt(request.getParameter("idArticolo"));
		if(idUser == -1 || recensione == null || voto == -1 || idArticolo == -1) {
			response.sendRedirect(request.getContextPath() + "/dettagliArticolo.jsp");
		}else {
			BeanRecensione rec = new BeanRecensione();
			BeanUtente utente = UtenteDAO.loadUserById(idUser);
			rec.setData(LocalDate.now());
			rec.setTesto(recensione);
			rec.setUtenteRecensione(utente);
			rec.setVoto(voto);
			result = RecensioneDAO.saveRecensione(rec, idArticolo);
			request.setAttribute("result", result);
			request.setAttribute("articoloInfo",ArticoloDAO.getArticoloById(request.getParameter("idArticolo")));
			RequestDispatcher rd = request.getRequestDispatcher("dettagliArticolo.jsp");
			rd.forward(request, response);
		}
	}

}
