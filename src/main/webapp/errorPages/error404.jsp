<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <title>Errore 404 - Pagina non trovata</title>
</head>
<body>
	<%@ include file="../NavBar.jsp" %>
	<main>
    <h1>Errore 404</h1>
    <p>La pagina richiesta non esiste o è stata rimossa.</p>
    <br><a href="<%= request.getContextPath() %>/index.jsp">Torna alla Home</a>
    </main>
    <%@ include file="../footer.jsp" %>
</body>
</html>
