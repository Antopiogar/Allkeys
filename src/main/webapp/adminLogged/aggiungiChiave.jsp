<%@page import="model.BeanUtente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ include file="../userLogged/verificaLogin.jsp" %> <%//DA SISTEMARE %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Aggiungi chiave</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>
<jsp:include page="../NavBar.jsp" />
<main>

	<br><h1>Aggiungi una nuova chiave</h1><br>
	
		<form action="<%= request.getContextPath() %>/GestioneAdminServlet" method = "POST">
			<input type="hidden" value="addSettedKey" name="AdminAction">
			<label for="gioco">Gioco</label><br>
			<input type= "text" name = "gioco" id="gioco">
			<br><br><label for="codice">Codice</label><br>
			<input type= "text" name = "codice" id="codice">
			<br><br><input type="submit" value="Aggiungi chiave di gioco">
		</form>
</main>
<%@ include file="../footer.jsp" %>
</body>
</html>