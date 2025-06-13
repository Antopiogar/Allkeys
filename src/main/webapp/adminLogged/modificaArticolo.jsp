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
<title>Modifica articolo - <%= articolo.getNome() %></title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<script type="text/javascript" src ="<%= request.getContextPath() %>/js/Search2.js" defer></script>
<script type="text/javascript" src ="<%= request.getContextPath() %>/js/dettagliArticolo.js" defer></script>
</head>
<body>
<%@ include file="../NavBar.jsp" %>

<main>
    <h1>Modifica articolo</h1>

    <% if (request.getAttribute("result") != null) { %>
        <% if (result) { %>
            <p class="success-message">Articolo modificato correttamente!</p>
        <% } else { %>
            <p class="error-message">Errore nella modifica dell'articolo.</p>
        <% } %>
    <% } %>

    <form action="ModificaArticoloServlet" method="POST" enctype="multipart/form-data" class="modifica-articolo-form">
        <!-- id nascosto, serve per aggiornare l’articolo esistente -->
        <input type="hidden" name="idArticolo" value="<%= articolo.getIdArticolo() %>">

        <label for="nome">Nome:</label><br>
        <input type="text" id="nome" name="nome" value="<%= articolo.getNome() %>" required><br><br>

        <label for="piattaforma">Piattaforma:</label><br>
        <input type="text" id="piattaforma" name="piattaforma" value="<%= articolo.getPiattaforma() %>" required><br><br>

        <label for="prezzo">Prezzo (€):</label><br>
        <input type="number" id="prezzo" name="prezzo" value="<%= articolo.getPrezzo() %>" step="0.01" min="0" required><br><br>

        <label for="descrizione">Descrizione:</label><br>
        <textarea id="descrizione" name="descrizione" rows="5" cols="50" required><%= articolo.getDescrizione() %></textarea><br><br>

        <label for="logo">Logo (se vuoi cambiarlo):</label><br>
        <input type="file" id="logo" name="logo" accept="image/*"><br><br>

        <input type="submit" value="Salva modifiche" class="center-submit-button">
    </form>
</main>

<%@ include file="../footer.jsp" %>
</body>
</html>
