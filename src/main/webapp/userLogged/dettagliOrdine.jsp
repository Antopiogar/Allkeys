<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%
    Acquisto acquisto = (Acquisto) request.getAttribute("acquisto");

    // Verifica se acquisto è null e redirige l'utente alla pagina degli ordini
    if (acquisto == null) {
        response.sendRedirect("../VisualizzaOrdiniServlet"); // Redirige alla servlet degli ordini
        return; // Evita ulteriori elaborazioni della pagina
    }
%>
<html>
<head>
    <title>Dettagli Ordine</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <jsp:include page="verificaLogin.jsp" />
</head>
<body>
<%@ include file="../NavBar.jsp" %>
<main>
<% 
    boolean redirect = Boolean.TRUE.equals(session.getAttribute("redirect"));
    if (redirect) {
        out.println("Errore, stai per essere reindirizzato al login...");
    } else { 
%>
    <h2>Ordine #<%= acquisto.getOrdine().getIdOrdine() %></h2>
    <ul>
        <% for (BeanArticolo art : acquisto.getArticoli()) { %>
            <li>
                <img src="IMG/loghi/<%= art.getLogo() %>" width="100">
                <strong><%= art.getNome() %></strong> - <%= art.getPrezzo() %> € - <%= art.getPiattaforma() %>
            </li>
        <% } %>
    </ul>
    <form action="FatturaPDFServlet" method="post">
        <input type="hidden" name="idOrdine" value="<%= acquisto.getOrdine().getIdOrdine() %>">
        <input type="submit" value="Stampa Fattura (PDF)">
    </form>
<% } %>
</main>
<%@ include file="../footer.jsp" %>
</body>
</html>
