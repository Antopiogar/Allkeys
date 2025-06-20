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
    ArrayList<String> piattaforme = null;
    if(request.getAttribute("piattaforme") != null){ piattaforme = (ArrayList<String>) request.getAttribute("piattaforme");}
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifica articolo: <%= articolo.getNome() %></title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <script type="text/javascript" src ="<%= request.getContextPath() %>/js/admin/ModificaArticolo.js" defer></script>
	<script>
		var IdArt = '<%=articolo.getIdArticolo() %>';
		document.getElementById("fileInput").addEventListener("change", function(event) {
		    const file = event.target.files[0];
		    const preview = document.getElementById("previewImage");
		    const originalSrc = "<%= request.getContextPath() %>/IMG/loghi/<%= articolo.getLogo() %>";
		
		    if (file) {
		        const reader = new FileReader();
		        reader.onload = function(e) {
		            preview.src = e.target.result;
		        }
		        reader.readAsDataURL(file);
		    } else {
		        preview.src = originalSrc;
		    }
		});
	</script>

</head>
<body>
<%@ include file="../NavBar.jsp" %>

<main>
    <h1>Modifica articolo: <%=articolo.getNome() %></h1>

    <div class="dettagli-wrapper">
        <div class="dettagli-img">
            <img id="previewImage" src="<%= request.getContextPath() %>/IMG/loghi/<%= articolo.getLogo() %>" alt="Immagine articolo" style="max-width: 200px; display: block; margin: 0 auto;">
        </div>
        <form class="dettagli-info">
            <p class="center-text"><strong>Nome:</strong><br>
                <textarea id="nome" rows="1" cols="25"><%=articolo.getNome()%></textarea>
            </p>
            <p class="center-text"><strong>Piattaforma:</strong><br>
                <select id="piattaforma">
                </select>
            </p>
            <div id="nuovaPiattaformaContainer" class="center-text" hidden="true">
		    	<br><br>
		    	<label for="nuovaPiattaforma">Nuova Piattaforma</label><br>
				<input type="text" name="nuovaPiattaforma" id="nuovaPiattaforma" placeholder ="es. PS6">
			</div>
            <p class="center-text"><strong>Prezzo:</strong><br>
                <textarea id="prezzo" rows="1" cols="25"><%=articolo.getPrezzo()%></textarea>
            </p>
            <p class="center-text"><strong>Scegli un'immagine per cambiare quella presente:</strong><br>
                <input type="file" id="fileInput" name="immagine" accept="image/*">
            </p>
        </form>
    </div>

    <div class="descrizione">
        <h2>Descrizione</h2>
        <p class="center-text"><br> 
            <textarea id="descrizione" rows="15" cols="50"><%= articolo.getDescrizione() %></textarea>
        </p>
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
        <% }
        } else { %>
            <p>Nessuna recensione disponibile per questo articolo.</p>
        <% } %>
    </div>

    <br><br><button onclick="checkForm()">Modifica</button><br>
    <div id="errore">
    </div>
</main>

<%@ include file="../footer.jsp" %>

</body>
</html>
