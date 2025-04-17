<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
<%@ include file="NavBar.jsp" %>
	<%
	String emailEsistente = (String)session.getAttribute("EmailEsistente");
	boolean loginFallito = Boolean.TRUE.equals(session.getAttribute("RisultatoLogin"));
	
	if(emailEsistente!=null &&  emailEsistente.equalsIgnoreCase("esistente")){
		out.print("Email già registrata<br>");
		session.setAttribute("RisultatoLogin",null);

	}
	else if(emailEsistente!=null && emailEsistente.equalsIgnoreCase("non esistente"))
		out.print("Registrazione riuscita<br>");
		session.setAttribute("RisultatoLogin",null);
	%> 
	<h1>Accedi</h1>
	<form action="loginServlet" method = "POST">
		<label for="email">Email</label>
		<input type="email" name = "email" required id ="email">
		<br><br><label for="password">Password</label>
		<input type="password" name = "password" required id ="password">
		<br><br><input type="submit" value="Accedi">
	</form>
</body>
</html>