<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <title>Errore 500 - Errore interno del server</title>
</head>
<body>
	<%@ include file="../NavBar.jsp" %>
	<main>
    <h1>Errore 500</h1>
    <p>Si è verificato un errore interno del server. Riprova più tardi.</p>
    <br><a href="<%= request.getContextPath() %>/index.jsp">Torna alla Home</a>
    </main>
    <%@ include file="../footer.jsp" %>
</body>
</html>
