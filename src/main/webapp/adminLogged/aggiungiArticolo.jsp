<%@page import="model.*"%>
<%@page import ="java.util.ArrayList" %>
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
	<script type="text/javascript" src ="<%= request.getContextPath() %>/js/admin/AggiungiArticolo2.js" defer></script>
	
</head>
<body>
<jsp:include page="../NavBar.jsp" />
<main>
	<%
		ArrayList<String> piattaforme = null;
		if(request.getAttribute("piattaforme") != null) piattaforme = (ArrayList<String>) request.getAttribute("piattaforme");
		if (piattaforme == null) {
			// Se non ci sono articoli, fai il redirect a una pagina di errore o di login
			response.sendRedirect(request.getContextPath() + "/adminLogged/profiloAdmin.jsp");
			return;
		}
	%>
	<br><h1>Aggiungi un nuovo articolo</h1><br>
		<div id="errore" style="color: red; margin-bottom: 10px;"></div>
		<form action="<%= request.getContextPath() %>/AggiungiArticolo" method = "POST" enctype="multipart/form-data">
			<input type="hidden" value="addSettedArticolo" name="AdminAction">
			<label for="nome">Nome</label><br>
			<input type= "text" name = "nome" id="nome">
			<br><br><label for="prezzo">Prezzo</label><br>
			<input type= "text" name = "prezzo" id="prezzo" placeholder="Prezzo > 0">
			<br><br><label for="descrizione">Descrizione</label><br>
			<input type= "text" name = "descrizione" id="descrizione">
			<br><br><label for="codice">Codice</label><br>
			<input type="text" name="codice" id="codice">
			<br><br><label for="piattaforma">Piattaforma</label><br>
			<select name="piattaforma" id="piattaforma" onchange="switchPiattaforma()"></select>
			<div id="nuovaPiattaformaContainer" hidden="true">
		    	<br><br>
		    	<label for="nuovaPiattaforma">Nuova Piattaforma</label><br>
				<input type="text" name="nuovaPiattaforma" id="nuovaPiattaforma" >
			</div>
			<br><br><label for="immagine">Logo (.png)</label><br>
    		<input type="file" name="immagine" id="immagine" accept=".png,image/png" >
			<br><br><button type="button" class="center-submit-button" onclick="checkForm()">Aggiungi articolo</button> 
		</form>
</main>
<%@ include file="../footer.jsp" %>
</body>
</html>