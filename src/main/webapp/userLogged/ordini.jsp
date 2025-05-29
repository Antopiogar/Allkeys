<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>

<%

    ArrayList<Acquisto> ordini = (ArrayList<Acquisto>) session.getAttribute("ordini");


    String dataInizioStr = (String) request.getAttribute("dataInizio");
    String dataFineStr = (String) request.getAttribute("dataFine");

    if (dataInizioStr == null || dataInizioStr.isEmpty()) {
        dataInizioStr = "2000-01-01";
    }
    if (dataFineStr == null || dataFineStr.isEmpty()) {
        dataFineStr = java.time.LocalDate.now().toString();
    }


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html>
<head>
    <title>I miei ordini</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <jsp:include page="verificaLogin.jsp" />
</head>

<body>
<%@ include file="../NavBar.jsp" %>

<main>
    <h2>I tuoi ordini</h2>

    <form action="<%= request.getContextPath() %>/VisualizzaOrdiniServlet" method="get">
        <label>Data Inizio: 
            <input type="date" name="dataInizio" value="<%= dataInizioStr %>">
        </label>
        <label>Data Fine: 
            <input type="date" name="dataFine" value="<%= dataFineStr %>">
        </label><br>
        <br><input type="submit" value="Filtra" class="center-submit-button">
    </form>

    <br>

    <% if (ordini == null || ordini.isEmpty()) { %>
        <p>Non hai ancora effettuato ordini nel periodo selezionato.</p>
    <% } else { %>
        <table border="1">
            <tr>
                <th class="center-text">Data Ordine</th>
                <th class="center-text">Totale</th>
                <th class="center-text">Azioni</th>
            </tr>
            <% for (Acquisto acquisto : ordini) {
                float totale = 0;
                if (acquisto.getArticoli() != null) {
                    for (BeanArticolo art : acquisto.getArticoli()) {
                        totale += art.getPrezzo();
                    }
                }
            %>
                <tr>
                    <td class="center-text"><%= acquisto.getOrdine().getDataAcquisto().format(formatter) %></td>
                    <td class="center-text"><%= String.format("%.2f €", totale) %></td>
                    <td class="center-text">
                        <form action="<%= request.getContextPath() %>/DettagliOrdineServlet" method="POST">
                            <input type="hidden" name="idOrdine" value="<%= acquisto.getOrdine().getIdOrdine() %>">
                            <input type="submit" value="Dettagli" class="center-submit-button">
                        </form>
                    </td>
                </tr>
            <% } %>
        </table>
    <% } %>
</main>

<%@ include file="../footer.jsp" %>
</body>
</html>
