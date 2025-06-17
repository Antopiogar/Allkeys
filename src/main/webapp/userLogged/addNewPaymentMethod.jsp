<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Aggiungi metodo di pagamento</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
	<script type="text/javascript" src ="<%= request.getContextPath() %>/js/Carta.js"></script>

</head>
<body>
<%@ include file="../NavBar.jsp" %>
<main>

		<h1>Inserisci i dati della carta di credito:</h1>
		<form>
			<br>Titolare*<br>
			<input type="text" name ="titolare" required id="titolare" placeholder="es. Mario Rossi"><br><br>
			<br>Numero Carta*<br>
			<input type="text" name="numeroCarta" required id="numeroCarta" placeholder="es.4532 1234 5678 9012 ">
			<br>Data di scadenza*<br>
			<input type="text" name="scadenza" required id="scadenza" placeholder="01/2025">
			<br>CVC*<br>
			<input type="text" name="cvc" required id="cvc" placeholder="es.453 "><br><br>
			<button type="button"  class="center-submit-button" onclick="checkForm()"> Aggiungi</button>
			<div id="errore" hidden="true"></div>
			<br><br><p>* campo obbligatorio</p>
		</form>
</main>
	<%@ include file="../footer.jsp" %>
</body>
</html>