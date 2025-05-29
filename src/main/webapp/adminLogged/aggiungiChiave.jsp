<%@page import="java.util.ArrayList"%>
<%@page import="model.BeanArticolo"%>
<%@page import="model.ArticoloDAO"%>

<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ include file="../userLogged/verificaLogin.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Aggiungi chiave</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>
<jsp:include page="../NavBar.jsp" />
<main>
	<%
		ArrayList<BeanArticolo> articoli = (ArrayList<BeanArticolo>) request.getAttribute("articoli");
		if (articoli == null) {
			// Se non ci sono articoli, fai il redirect a una pagina di errore o di login
			response.sendRedirect(request.getContextPath() + "/adminLogged/profiloAdmin.jsp");
			return;
		}
	%>
	<br><h1>Aggiungi una nuova chiave</h1><br>

	<form action="<%= request.getContextPath() %>/GestioneAdminServlet" method="POST">
		<input type="hidden" value="addSettedKey" name="AdminAction">
		
		<label for="articolo">Gioco</label><br>
		<select name="idArticolo" id="articolo">
			<%-- Carica gli articoli nel menu a tendina --%>
			<% for (BeanArticolo articolo : articoli) { %>
				<option value="<%= articolo.getIdArticolo() %>"><%= articolo.getNome() %></option>
			<% } %>
		</select>
		<br><br>
		<label for="codice">Codice</label><br>
		<input type="text" name="codice" id="codice">
		<br><br>

		<input type="submit" value="Aggiungi chiave di gioco" class="center-submit-button">
	</form>

</main>
<%@ include file="../footer.jsp" %>
</body>
</html>
