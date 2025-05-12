<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Admin</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>

<%@ include file="NavBar.jsp" %>
<main>
	<h1>Accedi come amministratore</h1><br>
	<form action="LoginAdminServlet" method = "POST">
		<label for="email">Email</label>
		<input type="email" name = "email" required id ="email">
		<br><br><label for="password">Password</label>
		<input type="password" name = "password" required id ="password">
		<br><br><input type="submit" value="Accedi">
	</form>
</main>
	<%@ include file="footer.jsp" %>
</body>
</html>