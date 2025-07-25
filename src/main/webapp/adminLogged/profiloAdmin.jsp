<%@page import="model.BeanUtente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Profilo Amministratore</title>
		<script type="text/javascript" src ="<%= request.getContextPath() %>/js/admin/ProfiloAdmin2.js"></script>
	
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>
<jsp:include page="../NavBar.jsp" />
<main>

<%

	BeanUtente user = (BeanUtente)session.getAttribute("User");
	if(user == null){
		%><div class= "messaggio-info">
		Errore, stai per essere reindirizzato al login...</div><%
		out.print("<meta http-equiv='refresh' content='5';url="+request.getContextPath()+"./loginAdmin.jsp' >");
 		request.getSession().setAttribute("redirect", true);
	}
	
	String risultatoUpdate = (String) session.getAttribute("risultatoModifica");
	java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
	String dataFormattata = null;
	if(user != null) dataFormattata = user.getDataNascita().format(formatter);
	if(user == null) response.sendRedirect(request.getContextPath() + "/login.jsp");
%>



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
	
	<% if (risultatoUpdate != null) { %>
	<br><br><div class="profilo-feedback messaggio-info"><%= risultatoUpdate %></div>
<% } %>
</div>


</div>
<div class="profilo-card">
	<h1>Gestione Admin</h1>

	<div class="admin-form-container">
	
		<form action="<%= request.getContextPath() %>/GestioneAdmin" method="POST">
			<input type="hidden" name="AdminAction" value="viewAllUsers">
			<input type="submit" class="profilo-bottone" value="Lista utenti registrati">
		</form>
		
		<form action="<%= request.getContextPath() %>/GestioneAdmin" method="POST">
			<input type="hidden" name="AdminAction" value="viewAllUsersOrders">
			<input type="submit" class="profilo-bottone" value="Visualizza tutti gli ordini">
		</form>
		
		<form action="<%= request.getContextPath() %>/GestioneAdmin" method="POST">
			<input type="hidden" name="AdminAction" value="addKey">
			<input type="submit" class="profilo-bottone" value="Aggiungi chiave di gioco">
		</form>

		<form action="<%= request.getContextPath() %>/GestioneAdmin" method="POST">
			<input type="hidden" name="AdminAction" value="addArticolo">
			<input type="submit" class="profilo-bottone" value="Aggiungi un nuovo articolo">
		</form>
		
	</div>
</div>

</main>
<%@ include file="../footer.jsp" %>
</body>
</html>