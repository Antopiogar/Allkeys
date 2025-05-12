<%@page import="model.BeanUtente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ include file="../userLogged/verificaLogin.jsp" %> <%//DA SISTEMARE %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Profilo Amministratore</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>
<jsp:include page="../NavBar.jsp" />
<main>

<%

	BeanUtente user = (BeanUtente)session.getAttribute("User");
	if(user == null){
		out.println("Errore, stai per essere reindirizzato al login...");
		out.print("<meta http-equiv='refresh' content='5';url="+request.getContextPath()+"./login.jsp' >");
 		request.getSession().setAttribute("redirect", true);
	}
	
	String risultatoUpdate = (String) session.getAttribute("risultatoModifica");
	java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
	String dataFormattata = user.getDataNascita().format(formatter);
%>

<%
	if(risultatoUpdate != null)
		out.print(risultatoUpdate);
%>
		
		<h1>I tuoi dati</h1>

		<br><form action="<%= request.getContextPath() %>/ModificaUtenteServlet" method = "POST">
			
		<label for="nome">Nome</label>
		<input type="text" name = "nome" required id ="nome" value= "<% out.print(user.getNome());%>">
		<br><br><label for="cognome">Cognome</label>
		<input type="text" name = "cognome" required id ="cognome" value= "<% out.print(user.getCognome());%>">
		<br><br><label for="dataN">Data di nascita</label>
		<input type="date" name = "dataN" required id ="dataN" value="<% out.print(dataFormattata); %>">
		<br><br><label for="cf">Codice Fiscale</label>
		<input type="text" name = "cf" required id ="cf" value="<% out.print(user.getCf()); %>">
		<br><br><label for="email">Email</label>
		<input type="email" name = "email" required id ="email" value="<%=user.getEmail()%>">
		<input type="text" name = "action" value="modifica" hidden="true">
		
		<br><br><input type="submit" value="Modifica informazioni">
	</form><br>
	<br><h1>Gestione Admin</h1><br>
	
		<form action="<%= request.getContextPath() %>/GestioneAdminServlet" method = "POST">
			<input type="hidden" value="addKey" name="AdminAction">
			<input type="submit" value="Aggiungi chiave di gioco">
		</form><br>
		<form action="<%= request.getContextPath() %>/GestioneAdminServlet" method = "POST">
			<input type="hidden" value="addArticolo" name="AdminAction">
			<input type="submit" value="Aggiungi un nuovo articolo">
		</form>
</main>
<%@ include file="../footer.jsp" %>
</body>
</html>