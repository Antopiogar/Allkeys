<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<% 
	String filtro = (String) request.getAttribute("Filtro");
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
<script type="text/javascript" src ="<%= request.getContextPath() %>/js/Catalogo2.js" defer></script>


<title>Catalogo articoli</title>
</head>
<body>
<%@ include file="NavBar.jsp" %> <%//includendo NavBar.jsp si ottiene anche lo scope di isAdmin visibile in questa jsp. %>

<main>

<h1>
Catalogo articoli <%= filtro != null ? filtro : "" %>
</h1>
<br>
<div id="BottoniFiltro">
	</div>

<% if (articoli != null && !articoli.isEmpty()) { %>

<div class="catalogo">
	
    <% for (BeanArticolo articolo : articoli) { %>
    <div class="articolo-card">
    <a href="DettagliArticoloServlet?articolo=<%= articolo.getIdArticolo() %>" class="articolo-link">
    <img src="<%= request.getContextPath() %>/IMG/loghi/<%= articolo.getLogo() %>" alt="Immagine articolo" style="max-width: 100%; height: auto;">
    <p><%= articolo.getInfo() %></p>
    <form action="CartServlet" method="POST">
        <input type="hidden" value="<%= articolo.getIdArticolo() %>" name="idArticolo">
        <input type="hidden" value="add" name="action">
        <input type="submit" value="Aggiungi al carrello">
    </form>
    </a><br>
    <%
    if(session.getAttribute("isAdmin") != null) isAdmin = (boolean) session.getAttribute("isAdmin");
    if(isAdmin==true){ %><button onclick="da_inserire()">✏️</button>
    <button onclick="da_inserire()">🗑️</button>
    <%} %>
</div>
    <% } %>
</div>
<% } else { %>
    <p>Nessun articolo disponibile.</p>
<% } %>
</main>

<%@ include file="footer.jsp" %>

</body>
</html>
