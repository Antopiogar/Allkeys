<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%@ page session="true" %>
<%@ page import="java.util.ArrayList" %>

<%
    BeanArticolo articolo = (BeanArticolo) request.getAttribute("articoloInfo");
    if (articolo == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    ArrayList<BeanRecensione> recensioni = (ArrayList<BeanRecensione>) request.getAttribute("recensioni");

    int idUser = (int) request.getAttribute("idUser");
    boolean isAdmin = (boolean) request.getAttribute("isAdmin");

    boolean result = false;
    if(request.getAttribute("result") != null){
        result = (boolean) request.getAttribute("result");
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= articolo.getNome() %></title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<script type="text/javascript" src ="<%= request.getContextPath() %>/js/Search2.js" defer></script>
<script type="text/javascript" src ="<%= request.getContextPath() %>/js/dettagliArticolo.js" defer></script>


</head>
<body>
<%@ include file="NavBar.jsp" %>
<main>
    <h1><%= articolo.getNome() %></h1>

    <% if (request.getAttribute("result") != null) { %>
        <% if (result) { %>
            <p>Recensione creata correttamente!</p>
        <% } else { %>
            <p>Recensione non creata!</p>
        <% } %>
    <% } %>

    <div class="dettagli-wrapper">
        <div class="dettagli-img">
            <img src="<%= request.getContextPath() %>/IMG/loghi/<%= articolo.getLogo() %>" alt="Immagine articolo">
        </div>
        <div class="dettagli-info">
            <p class="center-text"><strong>Piattaforma:</strong> <%= articolo.getPiattaforma() %></p>
            <p class="center-text"><strong>Prezzo:</strong> <%= articolo.getPrezzo() %> €</p>
            <form action="CartServlet" method="POST">
                <input type="hidden" name="idArticolo" value="<%= articolo.getIdArticolo() %>">
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Aggiungi al carrello" class="center-submit-button">
            </form>
        </div>
    </div>

    <div class="descrizione">
        <h2>Descrizione</h2>
        <p><%= articolo.getDescrizione() %></p>
    </div>

    <div class="recensioni">
        <h2>Recensioni</h2>
        
        <% if (request.getAttribute("deleteMessage") != null) { %>
    <br><div class="messaggio-info">
        <%= request.getAttribute("deleteMessage") %>
    </div>
<% } %>
        <%
        boolean existsRecensione = false;
            if (recensioni != null && !recensioni.isEmpty()) {
            	
                for (BeanRecensione rec : recensioni) {
                	if(rec.getUtenteRecensione().getIdUtente() == idUser)
                		existsRecensione = true;
        %>
            <div class="recensione-card" id="recensione<%=rec.getIdRecensione() %>">
                <div class="recensione-header">
                    <span class="recensione-user"><i class="fa-solid fa-user"></i> <strong><%= rec.getUtenteRecensione().getNome()%></strong></span>
                    <span class="recensione-data"><i class="fa-regular fa-calendar-days"></i><%= rec.getData() %></span>
                </div>
                <div class="recensione-stelle" id="stelle<%=rec.getIdRecensione() %>">
                    <% 
                        int voto = rec.getVoto();
                        for (int i = 1; i <= 5; i++) {
                            if (i <= voto) { %>
                                <i class="fa-solid fa-star"></i>
                            <% } else { %>
                                <i class="fa-regular fa-star"></i>
                            <% }
                        }
                    %>
                </div>
                <div class="recensione-testo" id="testo<%=rec.getIdRecensione() %>"><%= rec.getTesto() %></div>
                <% if(rec.getUtenteRecensione().getIdUtente() == idUser && idUser != -1) { %>
                    <button onclick="modificaRecensione(<%=rec.getIdRecensione() %>)">✏️</button>
                <% } %>
                <% if(isAdmin || (rec.getUtenteRecensione().getIdUtente() == idUser && idUser != -1)) { %>
                    <form action="<%= request.getContextPath() %>/DeleteRecensioneServlet" method="post" style="display:inline;">
    					<input type="hidden" name="idRec" value="<%= rec.getIdRecensione() %>">
    					<input type="hidden" name="idArticolo" value="<%= articolo.getIdArticolo() %>">
    					<input type="submit" value="🗑️" title="Elimina recensione">
					</form>

                <% } %>
            </div><br>
        <%      }
            } else {
        %>
            <p>Nessuna recensione disponibile per questo articolo.</p>
        <% } %>
		<% if(!existsRecensione) {%>
	        <% if(idUser != -1) { %>
	            <form action="AddRecensioneServlet" method="POST">
	                <select name="voto" id="voto" required>
	                    <option value="" disabled selected>Seleziona un voto</option>
	                    <option value="1">1 - ⭐</option>
	                    <option value="2">2 - ⭐⭐</option>
	                    <option value="3">3 - ⭐⭐⭐</option>
	                    <option value="4">4 - ⭐⭐⭐⭐</option>
	                    <option value="5">5 - ⭐⭐⭐⭐⭐</option>
	                </select><br>
	                <textarea name="recensione" rows="4" cols="50" placeholder="Scrivi qui..."></textarea>
	                <input type="hidden" name="idArticolo" value="<%= articolo.getIdArticolo() %>"><br>
	                <input type="submit" value="Aggiungi la recensione">
	            </form>
	        <% } else { %>
	            <p>Per scrivere una recensione esegui il <a href="<%= request.getContextPath() %>/login.jsp">login</a></p>
	        <% } 
        }%>
    </div>
</main>

<%@ include file="footer.jsp" %>
</body>
</html>
