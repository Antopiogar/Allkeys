<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.*" %>


<%if(request.getAttribute("redirected") == null || (Boolean) request.getAttribute("redirected") == false) response.sendRedirect(request.getContextPath() + "/index.jsp");

ArrayList<BeanUtente> users = null;
if(request.getAttribute("users")!=null) users = (ArrayList<BeanUtente>)request.getAttribute("users");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista Utenti</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>
<%@ include file="../NavBar.jsp" %>
<main>
<h1>Lista Utenti</h1>
<% if (users == null || users.isEmpty()) { %>
        <p>Nessun utente trovato.</p>
    <% } else { %>
        <table border="1">
            <tr>
                <th class="center-text">ID</th>
                <th class="center-text">Codice Fiscale</th>
                <th class="center-text">Nome</th>
                <th class="center-text">Cognome</th>
                <th class="center-text">Data di nascita</th>
                <th class="center-text">Email</th>
                <th class="center-text">Password</th>
                <th class="center-text">Ruolo</th>
                <th class="center-text">Ordini</th>
            </tr>
            <% for (BeanUtente user : users) {
            %>
                <tr>
                    <td class="center-text"><%= user.getIdUtente()%></td>
                    <td class="center-text"><%= user.getCf()%></td>
                    <td class="center-text"><%= user.getNome()%></td>
                    <td class="center-text"><%= user.getCognome()%></td>
                    <td class="center-text"><%= user.getDataNascita()%></td>
                    <td class="center-text"><%= user.getEmail()%></td>
                    <td class="center-text"><%= user.getPass()%></td>
                    <td class="center-text"><%if(user.getIsAdmin()==true){ %>Amministratore<% }else{%>Utente registrato<% }%></td>
                    <td class="center-text">
                        <form action="<%= request.getContextPath() %>/VisualizzaOrdiniServlet" method="POST">
                            <input type="hidden" name="idUser" value="<%=user.getIdUtente()%>">
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