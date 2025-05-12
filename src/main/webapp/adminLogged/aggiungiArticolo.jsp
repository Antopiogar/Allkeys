<%@page import="model.BeanUtente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ include file="../userLogged/verificaLogin.jsp" %> <%//DA SISTEMARE %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Aggiungi articolo</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>
<jsp:include page="../NavBar.jsp" />
<main>

	<br><h1>Aggiungi un nuovo articolo</h1><br>
	
		<form action="<%= request.getContextPath() %>/GestioneAdminServlet" method = "POST">
			<input type="hidden" value="addSettedArticolo" name="AdminAction">
			<label for="nome">Nome</label><br>
			<input type= "text" name = "nome" id="nome">
			<br><br><label for="prezzo">Prezzo</label><br>
			<input type= "text" name = "prezzo" id="prezzo">
			<br><br><label for="descrizione">Descrizione</label><br>
			<input type= "text" name = "descrizione" id="descrizione">
			<br><br><input type="submit" value="Aggiungi articolo">
		</form>
</main>
<%@ include file="../footer.jsp" %>
</body>
</html>