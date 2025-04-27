<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<% 
    ArrayList<BeanArticolo> articoli = (ArrayList<BeanArticolo>) request.getAttribute("result");
    if (articoli == null) {
        response.sendRedirect("ViewCatalog");
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
<title>Catalogo articoli</title>
</head>
<body>
<%@ include file="NavBar.jsp" %>

<main>
<h1>Catalogo articoli</h1>
<% if (articoli != null && !articoli.isEmpty()) { %>
<div class="catalogo">
    <% for (BeanArticolo articolo : articoli) { %>
    <div class="articolo-card">
    <img src="<%= request.getContextPath() %>/IMG/loghi/<%= articolo.getLogo() %>" alt="Immagine articolo" style="max-width: 100%; height: auto;">
    <p><%= articolo.toString() %></p>
    <form action="CartServlet" method="POST">
        <input type="hidden" value="<%= articolo.getIdArticolo() %>" name="idArticolo">
        <input type="hidden" value="add" name="action">
        <input type="submit" value="Aggiungi al carrello">
    </form>
</div>
    <% } %>
</div>
<% } else { %>
    <p>Nessun articolo disponibile.</p>
<% } %>
</main>

<div class="footer">
    &copy; 2025 - Allkeys - Tutti i diritti riservati
</div>

</body>
</html>
