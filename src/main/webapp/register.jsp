<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrazione</title>
</head>
<body>
<%@ include file="NavBar.jsp" %>
	<h1>Registrati</h1>
	<%
	boolean registrazioneFallita = Boolean.TRUE.equals(session.getAttribute("LoginFallito"));
	if(registrazioneFallita)
		out.print("Registrazione Fallita, riprova");
	%>
	<form action="registerServlet" method = "POST">
		<label for="nome">Nome</label>
		<input type="text" name = "nome" required id ="nome">
		<br><br><label for="cognome">Cognome</label>
		<input type="text" name = "cognome" required id ="cognome">
		<br><br><label for="dataN">Data di nascita</label>
		<input type="date" name = "dataN" required id ="dataN">
		<br><br><label for="cf">Codice Fiscale</label>
		<input type="text" name = "cf" required id ="cf">
		<br><br><label for="email">Email</label>
		<input type="email" name = "email" required id ="email">
		<br><br><label for="password">Password</label>
		<input type="password" name = "password" required id ="password">
		<br><br><input type="submit" value="Registrati ora">
	</form>
	<%@ include file="footer.jsp" %>
</body>
</html>