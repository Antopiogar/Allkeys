<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="model.*" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Acquista</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <style>
        .submit-container {
            text-align: center;
            margin-top: 20px;
        }
    </style>
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
else{
    ArrayList<BeanCartaPagamento> carte = (ArrayList<BeanCartaPagamento>) request.getAttribute("carte");
%>

<% if (carte == null || carte.isEmpty()) { %>
    <h1>Nessuna carta memorizzata!</h1>
    <form action="<%= request.getContextPath() %>/userLogged/addNewPaymentMethod.jsp" method="POST">
        <input type="submit" value="Aggiungi nuova carta">
    </form>
<% } else { %>
    <h1>Scegli la carta di pagamento da usare:</h1><br><br>
    <form action="<%= request.getContextPath() %>/ConfermaOrdineServlet" method="POST" class="payment-form">
        <div class="payment-card-container">
        <% for (BeanCartaPagamento i : carte) { 
               String numeroCarta = i.getnCarta();
               String finale = numeroCarta.length() > 4 ? numeroCarta.substring(numeroCarta.length() - 4) : numeroCarta;
        %>
            <label class="payment-card">
                <input type="radio" name="idCartaSelezionata" value="<%= i.getIdCarta() %>" required>
                <div class="card-content">
                    💳 Carta: **** **** **** <%= finale %>
                </div>
            </label>
        <% } %>
        </div>
        <div class="submit-container">
            <input type="submit" value="Procedi con il pagamento">
        </div>
    </form>
<% }} %>
</main>

<%@ include file="../footer.jsp" %>
</body>
</html>
