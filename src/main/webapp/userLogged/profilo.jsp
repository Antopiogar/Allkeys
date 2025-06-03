<%@page import="model.BeanUtente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Profilo</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
	<!-- NON ESISTE IL FILE = <link rel="stylesheet" href="<%= request.getContextPath() %>/css/profilo.css"> -->
	<script type="text/javascript" src ="<%= request.getContextPath() %>/js/Profilo.js"></script>
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
	String dataFormattata = null;
	if(user != null) dataFormattata = user.getDataNascita().format(formatter);
	if(user == null) response.sendRedirect(request.getContextPath() + "/login.jsp");
%>

<% if (risultatoUpdate != null) { %>
	<div class="profilo-feedback"><%= risultatoUpdate %></div>
<% } %>

<div class="profilo-card" id="contenitore">
	<h1>Il tuo profilo</h1>

	<div class="profilo-dato">
		<span>Nome:</span> <p id="nome"> <%= user.getNome() %></p>
	</div>
	<div class="profilo-dato">
		<span>Cognome:</span> <p id="cognome"><%= user.getCognome() %></p>
	</div>
	<div class="profilo-dato">
		<span>Data di nascita:</span><p id="dataN"><%= dataFormattata %></p>
	</div>
	<div class="profilo-dato">
		<span>Codice Fiscale:</span> <p id="cf"><%= user.getCf() %></p>
	</div>
	<div class="profilo-dato">
		<span>Email:</span> <p id="email"><%= user.getEmail() %></p></div>
	<div class="profilo-bottone-container">
	<button type="button" class="profilo-bottone" onclick="modificaDati('<%= request.getContextPath() %>')">Modifica</button>
</div>

</div>

</main>
<%@ include file="../footer.jsp" %>
</body>
</html>
