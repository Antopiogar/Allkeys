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
<%if(request.getAttribute("redirected") == null || (Boolean) request.getAttribute("redirected") == false) response.sendRedirect(request.getContextPath() + "/index.jsp");

%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista Ordini</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>
<%@ include file="../NavBar.jsp" %>
<main>
<h1>Lista Ordini</h1>
<form action="<%= request.getContextPath() %>/GestioneAdmin" method="get">
		<input type="hidden" name="AdminAction" value="viewAllUsersOrders">
        <label>Data Inizio: 
            <input type="date" name="dataInizio" value="<%= dataInizioStr %>">
        </label>
        <label>Data Fine: 
            <input type="date" name="dataFine" value="<%= dataFineStr %>">
        </label><br>
        <br><input type="submit" value="Filtra" class="center-submit-button">
    </form>
<% if (ordini == null || ordini.isEmpty()) { %>
        <br><p>Nessun ordine trovato.</p>
    <% } else { %>
        <table border="1">
            <tr>
                <th class="center-text">ID Ordine</th>
                <th class="center-text">ID utente</th>
                <th class="center-text">Data Acquisto</th>
                <th class="center-text">Nome</th>
                <th class="center-text">Cognome</th>
                <th class="center-text">Email</th>
                <th class="center-text">Carta</th>
                <th class="center-text">Prezzo</th>
                <th class="center-text">Azioni</th>
            </tr>
            <% for (Acquisto a : ordini) {
            	float totale = 0;
                if (a.getArticoli() != null) {
                    for (BeanArticolo art : a.getArticoli()) {
                        totale += art.getPrezzo();
                    }
                }
            %>
                <tr>
                    <td class="center-text"><%= a.getIdOrdine()%></td>
                    <td class="center-text"><%= a.getUtente().getIdUtente()%></td>
                    <td class="center-text"><%= a.getOrdine().getDataAcquisto().format(formatter)%></td>
                    <td class="center-text"><%= a.getUtente().getNome()%></td>
                    <td class="center-text"><%= a.getUtente().getCognome()%></td>
                    <td class="center-text"><%= a.getUtente().getEmail()%></td>
                    <td class="center-text"><%= a.getCarta().getnCarta()%></td>
                    <td class="center-text"><%= String.format("%.2f €", totale)%></td>
                    <td class="center-text">
                        <form action="<%= request.getContextPath() %>/DettagliOrdineServlet" method="POST">
                            <input type="hidden" name="idOrdine" value="<%= a.getIdOrdine()%>">
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