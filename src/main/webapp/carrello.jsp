<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session = "true" %>
<%@ page import = "model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Carrello</title>
</head>
<body>
	<h1>Carrello</h1>
	<% Carrello cart = (Carrello) request.getSession().getAttribute("cart");%>
	<%!BeanArticolo articolo = null; %>
	<%if(cart == null || cart.isEmpty()){
		%>
		<br>Nessun articolo nel carrello.
	<% }else{
	%>
	<%for(ArticoliCarrello i : cart.getArticoli()){
		articolo = i.getArticolo();
		out.println(articolo.toString());
		%>
	
	<form action="CartServlet" method = "POST">
		<input type="text" value = "<%= articolo.getIdArticolo() %>" hidden = "true" name = idArticolo>
		<input type="number" value = "<%= i.getQta() %>" min="0" max="99"  name = "quantita">

        <input type = "text" value = "changeQta" hidden = "true" name = "action">
        <input type="submit" value="Modifica">
	</form>
	<form action="CartServlet" method = "POST">
        <input type = "text" value = "delete" hidden = "true" name = "action">
        <input type="text" value = "<%= articolo.getIdArticolo() %>" hidden = "true" name = idArticolo>
        <input type="submit" value="Elimina">
	</form>
	<% }%>
	<%} %>
	
	<h2> totale = <%= cart.prezzoTotale() %> €</h2>
	<br><a href="ViewCatalog">Torna al catalogo</a>
</body>
</html>