<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
boolean loginFallito = Boolean.TRUE.equals(session.getAttribute("LoginFallito"));

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Admin</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
        <script type="text/javascript" src ="<%= request.getContextPath() %>/js/admin/login.js" defer></script>

</head>
<body>

<%@ include file="NavBar.jsp" %>
<main>
	<h1>Accedi come amministratore</h1><br>
	<%
	if(loginFallito){
		out.print("Email o password errati <br>");

	}
	 %>
	<form  method = "POST" id="formLogin">
	<div id="errore"></div>
		<label for="email">Email</label>
		<input type="email" name = "email" required id ="email">
		<br><br><label for="password">Password</label>
		<input type="password" name = "password" required id ="pass">
		<br><br><button type="submit" class="center-submit-button"  onclick="check()">Accedi</button> 
	</form>
</main>
	<%@ include file="footer.jsp" %>
</body>
</html>