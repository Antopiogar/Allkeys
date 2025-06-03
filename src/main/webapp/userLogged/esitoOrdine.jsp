<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Esito Ordine</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>

<%@ include file="../NavBar.jsp" %>

<main>
        <%Integer status = (Integer) request.getAttribute("status");

        if (status == null) {
    %>
        <h1>❌ Errore interno: stato ordine non disponibile.</h1>
    <%
        } else if (status == 0) {
    %>
        <h1>✅ Ordine effettuato con successo!</h1>
        <p>Grazie per il tuo acquisto.</p>
    <%
        } else if (status > 0) {
    %>
        <h1>⚠️ Ordine non effettuato</h1>
        <p>Mancano <strong><%= status %></strong> articoli nel magazzino.</p>
    <%
        } else if (status == -2) {
    %>
        <h1>⚠️ Errore nel formato dell'ID della carta.</h1>
    <%
        } else if (status == -3) {
    %>
        <h1>⚠️ Nessuna carta selezionata.</h1>
    <%
        } else {
    %>
        <h1>❌ Errore durante l'elaborazione dell'ordine.</h1>
        <p>Riprova più tardi o contatta l'assistenza.</p>
    <br><a href="<%= request.getContextPath() %>/ViewCatalog">Torna al catalogo</a><%} %>
</main>

<%@ include file="../footer.jsp" %>

</body>
</html>
