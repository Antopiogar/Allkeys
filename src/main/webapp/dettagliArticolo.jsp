<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%
    BeanArticolo articolo = (BeanArticolo) request.getAttribute("articoloInfo");
    if (articolo == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= articolo.getNome() %></title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>
<%@ include file="NavBar.jsp" %>

<main>
    <h1><%= articolo.getNome() %></h1>

    <div class="dettagli-wrapper">
        <div class="dettagli-img">
            <img src="<%= request.getContextPath() %>/IMG/loghi/<%= articolo.getLogo() %>" alt="Immagine articolo">
        </div>
        <div class="dettagli-info">
            <p><strong>Piattaforma:</strong> <%= articolo.getPiattaforma() %></p>
            <p><strong>Prezzo:</strong> <%= articolo.getPrezzo() %> €</p>
            <form action="CartServlet" method="POST">
                <input type="hidden" name="idArticolo" value="<%= articolo.getIdArticolo() %>">
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Aggiungi al carrello">
            </form>
        </div>
    </div>

    <div class="descrizione">
        <h2>Descrizione</h2>
        <p>Non sono ancora presenti le descrizioni per gli articoli, riprovare più tardi!</p>
    </div>

    <div class="recensioni">
        <h2>Recensioni</h2>//TO DO
        <p>Nessuna recensione disponibile al momento.</p>
    </div>
</main>

<%@ include file="footer.jsp" %>
</body>
</html>
