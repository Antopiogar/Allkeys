
<%@page import="java.util.ArrayList"%>
<%@page import="model.BeanArticolo"%>
<%@page import="model.ArticoloDAO"%>

<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Aggiungi chiave</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
	<script type="text/javascript" src ="<%= request.getContextPath() %>/js/admin/aggiungiKey2.js" defer></script>
	
</head>
<body>
<jsp:include page="../NavBar.jsp" />
<main>
	<%
		ArrayList<BeanArticolo> articoli = (ArrayList<BeanArticolo>) request.getAttribute("articoli");
		if (articoli == null) {
			// Se non ci sono articoli, fai il redirect a una pagina di errore o di login
			response.sendRedirect(request.getContextPath() + "/adminLogged/profiloAdmin.jsp");
			return;
		}
	%>
	<br><h1>Aggiungi una nuova chiave</h1><br>
	<div id="errore" style="color: red; margin-bottom: 10px;"></div>

	<form action="<%= request.getContextPath() %>/GestioneAdmin" method="POST">
		<input type="hidden" value="addSettedKey" name="AdminAction">
		
		<label for="articolo">Gioco</label><br>
		<select name="idArticolo" id="articolo">
			<%-- Carica gli articoli nel menu a tendina --%>
			<% for (BeanArticolo articolo : articoli) { %>
				<option value="<%= articolo.getIdArticolo() %>"><%= articolo.getNome() %></option>
			<% } %>
		</select>
		<br><br>
		<label for="codice">Codice</label><br>
		<input type="text" name="codice" id="codice">
		<br><br>

		<button type="button"  class="center-submit-button" onclick="checkForm()">Aggiungi chiave di gioco</button> 
	</form>

</main>
<%@ include file="../footer.jsp" %>
</body>
</html>
