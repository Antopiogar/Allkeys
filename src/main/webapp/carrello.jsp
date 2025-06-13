<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="model.*" %>
<%@ page session = "true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
<title>Carrello</title>
</head>
<body>
<%@ include file="NavBar.jsp" %>

<main>
<h1>Carrello</h1>
<% 
    Carrello cart = (Carrello) request.getSession().getAttribute("cart");
    BeanArticolo articolo = null;
    if (cart == null || cart.isEmpty()) {
%>
    <p>Nessun articolo nel carrello.</p>
<% 
    } else {
        for (ArticoliCarrello i : cart.getArticoli()) {
            articolo = i.getArticolo();
%>
    <div class="articolo-card">
    	<img src="<%= request.getContextPath() %>/IMG/loghi/<%= articolo.getLogo() %>" alt="Immagine articolo" style="max-width: 100%; height: auto;">
        <p><%= articolo.getInfo() %></p>
        <form action="CartServlet" method="POST">
            <input type="hidden" value="<%= articolo.getIdArticolo() %>" name="idArticolo">
            <input type="number" value="<%= i.getQta() %>" min="0" max="99" name="quantita">
            <br><input type="hidden" value="changeQta" name="action">
            <br><input type="submit" value="Modifica Quantità">
        </form><br>
        <form action="CartServlet" method="POST">
            <input type="hidden" value="delete" name="action">
            <input type="hidden" value="<%= articolo.getIdArticolo() %>" name="idArticolo">
            <input type="submit" value="Elimina">
        </form>
    </div>
    <br>
<% 
        }
    }
    if (cart != null) { 
%>
    <h2>Totale = <%= cart.prezzoTotale() %> €</h2>
    <br><%if(cart.prezzoTotale()>0){
    	%><form action="AcquistaServlet" method = "POST">
    		<input type = "submit" value ="Acquista">
   		  </form>
   <% }
 }%>
<br><a href="ViewCatalog">Torna al catalogo</a>
</main>

<%@ include file="footer.jsp" %>

</body>
</html>
