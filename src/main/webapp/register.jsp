<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrazione</title>
<script type="text/javascript" src ="<%= request.getContextPath() %>/js/Register.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>
<%@ include file="NavBar.jsp" %>
<main>
	<h1>Registrati</h1>
	<%
	boolean registrazioneFallita = Boolean.TRUE.equals(session.getAttribute("LoginFallito"));
	if(registrazioneFallita)
		out.print("Registrazione Fallita, riprova");
	%>
	<form id="form" action="registerServlet" method = "POST">
		<div id="error" hidden="true">
		</div>
		<label for="nome">Nome</label>
		<input type="text" name = "nome" required id ="nome" placeholder="solo lettere">
		<br><br><label for="cognome">Cognome</label>
		<input type="text" name = "cognome" required id ="cognome" placeholder="solo lettere">
		<br><br><label for="dataN">Data di nascita</label>
		<input type="date" name = "dataN" required id ="dataN">
		<br><br><label for="cf">Codice Fiscale</label>
		<input type="text" name = "cf" required id ="cf" >
		<br><br><label for="email">Email</label>
		<input type="email" name = "email" required id ="email">
		<br><br><label for="password">Password</label>
		<input type="password" name = "password" required id ="password" placeholder="6 caratteri almeno 1 lettera e 1 numero">
		<br><br><button type="button" onclick="register()">Registrati ora</button>
	</form>
</main>
	<%@ include file="footer.jsp" %>
</body>
</html>