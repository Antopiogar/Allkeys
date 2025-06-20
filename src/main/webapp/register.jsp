<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session = "true" %>

<%if(!(session.getAttribute("Nome") != null && !session.getAttribute("Nome").equals(""))){%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrazione</title>
<script type="text/javascript" src ="<%= request.getContextPath() %>/js/Register.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>
<%@ include file="NavBar.jsp" %>
<main>
	<h1>Registrati</h1>
	<form id="form" action="registerServlet" method = "POST">
		
		<label for="nome">Nome*</label>
		<input type="text" name = "nome" required id ="nome" placeholder="es. Mario">
		<br><br><label for="cognome">Cognome*</label>
		<input type="text" name = "cognome" required id ="cognome" placeholder="es. Rossi">
		<br><br><label for="dataN">Data di nascita*</label>
		<input type="date" name = "dataN" required id ="dataN">
		<br><br><label for="cf">Codice Fiscale*</label>
		<input type="text" name = "cf" required id ="cf" placeholder = "es. ROSMRI87A04H501K">
		<br><br><label for="email">Email*</label>
		<input type="email" name = "email" required id ="email" placeholder = "es. mario.rossi@email.it">
		<br><br><label for="password">Password*</label>
		<input type="password" name = "password" required id ="password" placeholder="es. ProductKey12@">
		<br><br><p>* campo obbligatorio</p>
		<div id="error2" hidden="true">
			<br>
			<div id="error" hidden="true">
			
			</div>
		</div>
		
		<br><button type="button" onclick="register()">Registrati</button>
		<span>Sei già registrato? <a href = "login.jsp">Accedi</a></span>
		
	</form>
	
</main>
	<%@ include file="footer.jsp" %>
</body>
</html>
<%}
else{ response.sendRedirect(request.getContextPath() + "/index.jsp");}%>