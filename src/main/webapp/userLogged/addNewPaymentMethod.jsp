<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Aggiungi metodo di pagamento</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
<jsp:include page="verificaLogin.jsp" />
</head>
<body>
<%@ include file="../NavBar.jsp" %>
<main>
<%
	boolean redirect = Boolean.TRUE.equals(session.getAttribute("redirect"));
	if(redirect){
		out.println("Errore, stai per essere reindirizzato al login...");
	}
	else{%>
		<h1>Inserisci i dati della carta di credito:</h1>
		<form action="../AddPaymentMethodServlet" method="POST">
			<br>Titolare<br>
			<input type="text" name ="titolare" required id="titolare"><br><br>
			Numero Carta<br>
			<input type="numeric" name="numeroCarta" required id="numeroCarta">
			<br>Data di scadenza<br>
			<input type="date" name="scadenza" required id="scandeza">
			<br>CVC<br>
			<input type="numeric" name="cvc" required id="cvc"><br><br>
			<input type="submit" value="Aggiungi" class="center-submit-button">

		</form>
	<%}
%>
</main>
	<%@ include file="../footer.jsp" %>
</body>
</html>