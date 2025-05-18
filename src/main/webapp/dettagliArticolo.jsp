<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page session="true" %>

<%int idUser = -1;
if(session.getAttribute("idUser")!=null) idUser = (int) session.getAttribute("idUser");
%>
<%boolean isAdmin = false;
    if(session.getAttribute("isAdmin") != null) isAdmin = (boolean) session.getAttribute("isAdmin");%>
<%
    BeanArticolo articolo = (BeanArticolo) request.getAttribute("articoloInfo");
    if (articolo == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    
    
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= articolo.getNome() %></title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
<%@ include file="NavBar.jsp" %>

<main>
    <h1><%= articolo.getNome() %></h1>

    <div class="dettagli-wrapper">
        <div class="dettagli-img">
            <img src="<%= request.getContextPath() %>/IMG/loghi/<%= articolo.getLogo() %>" alt="Immagine articolo">
        </div>
        <div class="dettagli-info">
            <p><strong>Piattaforma:</strong> <%= articolo.getPiattaforma() %></p>
            <p><strong>Prezzo:</strong> <%= articolo.getPrezzo() %> €</p>
            <form action="CartServlet" method="POST">
                <input type="hidden" name="idArticolo" value="<%= articolo.getIdArticolo() %>">
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Aggiungi al carrello">
            </form>
        </div>
    </div>

    <div class="descrizione">
        <h2>Descrizione</h2>
        <p><%= articolo.getDescrizione() %></p>
    </div>

    <div class="recensioni">
        <h2>Recensioni</h2>
        <%
            ArrayList<BeanRecensione> recensioni = RecensioneDAO.getRecensioniByIdArticolo(String.valueOf(articolo.getIdArticolo()));
            if (recensioni != null && !recensioni.isEmpty()) {
                for (BeanRecensione rec : recensioni) {
        %>
            <div class="recensione-card">
                <div class="recensione-header">
                    <span class="recensione-user"><i class="fa-solid fa-user"></i> <strong><%= rec.getUtenteRecensione().getNome() %></strong></span>
                    <span class="recensione-data"><i class="fa-regular fa-calendar-days"></i> <%= rec.getData() %></span>
                </div>
                <div class="recensione-stelle">
                    <%-- Mostra stelle --%>
                    <%
                        int voto = rec.getVoto();
                        for (int i = 1; i <= 5; i++) {
                            if (i <= voto) {
                    %><i class="fa-solid fa-star"></i><%
                            } else {
                    %><i class="fa-regular fa-star"></i><%
                            }
                        }
                    %>
                </div>
                <div class="recensione-testo"><%= rec.getTesto() %></div>
                <%if(rec.getUtenteRecensione().getIdUtente() == idUser && idUser!=-1) {%>
                <br><button onclick="da_inserire()">✏️</button>
                <%} %>
                <%if(isAdmin == true || (rec.getUtenteRecensione().getIdUtente() == idUser && idUser!=-1)) {%>
                <br><button onclick="da_inserire()">🗑️</button>
                <%} %>
            </div><br>
            <form action="AddRecensioneServlet" method="POST">
                <textarea rows="4" cols="50" placeholder="Scrivi qui..."></textarea>
                <br><input type="submit" value="Aggiungi la recensione">
            </form>
        <%
                }
            } else {
        %>
            <p>Nessuna recensione disponibile per questo articolo.</p>
        <%
            }
        %>
    </div>
</main>

<%@ include file="footer.jsp" %>
</body>
</html>
