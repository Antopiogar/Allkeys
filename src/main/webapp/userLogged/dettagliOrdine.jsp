<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<jsp:include page="verificaLogin.jsp" />
<%
	Acquisto acquisto = null;
    if(request.getAttribute("acquisto") !=null){ acquisto = (Acquisto) request.getAttribute("acquisto");}

    if (acquisto == null) {
        response.sendRedirect("../VisualizzaOrdiniServlet");
        return;
    }

    BeanArticolo art;
    BeanChiave chiave;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dettagli Ordine</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
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
    <h2>Dettagli dell'Ordine #<%= acquisto.getOrdine().getIdOrdine() %></h2>

    <div class="catalogo">
        <% for (int i = 0; i < acquisto.getArticoli().size(); i++) {
            art = acquisto.getArticoli().get(i);
            chiave = acquisto.getChiavi().get(i);
        %>
        <div class="articolo-card">
            <img src="IMG/loghi/<%= art.getLogo() %>" alt="<%= art.getNome() %> Logo" width="100">
            <h3><%= art.getNome() %></h3>
            <br><p><strong>Piattaforma:</strong> <%= art.getPiattaforma() %></p>
            <p><strong>Prezzo:</strong> <%= art.getPrezzo() %> €</p>
            <p><strong>Product Key:</strong><br>
                <code>
                    <%= chiave.getCodice() %>
                </code>
            </p>
        </div>
        <% } %>
    </div>

    <form action="FatturaPDFServlet" method="post" style="margin-top: 30px;">
        <input type="hidden" name="idOrdine" value="<%= acquisto.getOrdine().getIdOrdine() %>">
        <input type="submit" value="Stampa Fattura (PDF)">
    </form>
<% } %>
</main>
<%@ include file="../footer.jsp" %>
</body>
</html>
