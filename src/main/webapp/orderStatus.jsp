<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%@ page import="model.*" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Stato ordine</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>

<%@ include file="NavBar.jsp" %>

<main>
    <%! Integer id = -1; %>
    <%
        Object ido = session.getAttribute("idUser");
        if (ido instanceof Integer) {
            id = (Integer) ido;
        }

        String idCartaStr = request.getParameter("idCartaSelezionata");
        if(idCartaStr == null) response.sendRedirect(request.getContextPath() + "/index.jsp");
        int idCarta = (idCartaStr != null && !idCartaStr.isEmpty()) ? Integer.parseInt(idCartaStr) : -1;
        
        
        int status = OrdineDAO.ConfirmOrder(id, idCarta);
		session.setAttribute("cart", new Carrello());
		
        if (status == 0) {
    %>
        <h1>Ordine effettuato con successo!</h1>
    <%
    
        } else if (status > 0) {
    %>
        <h1>Ordine non effettuato, mancano <%= status %> chiavi!</h1>
    <%
        } else if (status < 0) {
    %>
        <h1>Ordine non effettuato!</h1>
    <%
        }
    %>
</main>

<%@ include file="footer.jsp" %>

</body>
</html>
