<%@page import="model.BeanUtente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ include file="verificaLogin.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Profilo</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/profilo.css">
</head>
<body>
<jsp:include page="../NavBar.jsp" />
<main>

<%
	BeanUtente user = (BeanUtente) session.getAttribute("User");
	if (user == null) {
		out.println("Errore, stai per essere reindirizzato al login...");
		out.print("<meta http-equiv='refresh' content='5';url=" + request.getContextPath() + "/login.jsp' >");
		session.setAttribute("redirect", true);
		return;
	}

	String risultatoUpdate = (String) session.getAttribute("risultatoModifica");
	java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
	String dataFormattata = user.getDataNascita().format(formatter);
%>

<% if (risultatoUpdate != null) { %>
	<div class="profilo-feedback"><%= risultatoUpdate %></div>
<% } %>

<div class="profilo-card">
	<h1>Il tuo profilo</h1>

	<p class="profilo-dato"><span>Nome:</span> <%= user.getNome() %></p>
	<p class="profilo-dato"><span>Cognome:</span> <%= user.getCognome() %></p>
	<p class="profilo-dato"><span>Data di nascita:</span> <%= dataFormattata %></p>
	<p class="profilo-dato"><span>Codice Fiscale:</span> <%= user.getCf() %></p>
	<p class="profilo-dato"><span>Email:</span> <%= user.getEmail() %></p>
	<div class="profilo-bottone-container">
	<a href="modificaProfilo.jsp" class="profilo-bottone">Modifica</a><%//DA CAMBIARE QUI.%>
</div>

</div>

</main>
<%@ include file="../footer.jsp" %>
</body>
</html>
