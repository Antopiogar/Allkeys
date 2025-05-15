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
<div class="profilo-card">
	<h1>Gestione Admin</h1>

	<div class="admin-form-container">
		<form action="<%= request.getContextPath() %>/GestioneAdminServlet" method="POST">
			<input type="hidden" name="AdminAction" value="addKey">
			<input type="submit" class="profilo-bottone" value="Aggiungi chiave di gioco">
		</form>

		<form action="<%= request.getContextPath() %>/GestioneAdminServlet" method="POST">
			<input type="hidden" name="AdminAction" value="addArticolo">
			<input type="submit" class="profilo-bottone" value="Aggiungi un nuovo articolo">
		</form>
	</div>
</div>

</main>
<%@ include file="../footer.jsp" %>
</body>
</html>