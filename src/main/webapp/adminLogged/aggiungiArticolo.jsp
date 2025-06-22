<%@page import="model.*"%>
<%@page import ="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Aggiungi articolo</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
	<script type="text/javascript" src ="<%= request.getContextPath() %>/js/admin/aggiungiArticolo3.js" defer></script>
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
		<div id="errore" style="margin-bottom: 10px;"></div>
		<form action="<%= request.getContextPath() %>/AggiungiArticolo" method = "POST" enctype="multipart/form-data">
			<input type="hidden" value="addSettedArticolo" name="AdminAction">
			<label for="nome">Nome</label><br>
			<input type= "text" name = "nome" id="nome" placeholder= "es. Halo Infinite">
			<br><br><label for="prezzo">Prezzo</label><br>
			<input type= "text" name = "prezzo" id="prezzo" placeholder="es. 29.99€">
			<br><br><label for="descrizione">Descrizione</label><br>
			<input type= "text" name = "descrizione" id="descrizione" placeholder="es. Gioco avventuroso con storia epica!">
			<br><br><label for="codice">Codice</label><br>
			<input type="text" name="codice" id="codice" placeholder = "es. KEY-GAME-XBOX-001">
			<br><br><label for="piattaforma">Piattaforma</label><br>
			<select name="piattaforma" id="piattaforma" onchange="switchPiattaforma()"></select>
			<div id="nuovaPiattaformaContainer" hidden="true">
		    	<br>
		    	<label for="nuovaPiattaforma">Nuova Piattaforma</label><br>
				<input type="text" name="nuovaPiattaforma" id="nuovaPiattaforma" placeholder ="es. PS6">
			</div>
			<br><br><label for="immagine">Logo</label><br>
    		<input type="file" name="immagine" id="immagine" accept=".png,image/png" >
			<br><br><button type="button" class="center-button" onclick="checkForm()">Aggiungi articolo</button> 
		</form>
</main>
<%@ include file="../footer.jsp" %>
</body>
</html>