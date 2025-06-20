<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <title>Errore 405 - Metodo non consentito</title>
</head>
<body>
	<%@ include file="../NavBar.jsp" %>
	<main>
    <h1>Errore 405</h1>
    <p>Metodo non consentito.</p>
    <br><a href="<%= request.getContextPath() %>/index.jsp">Torna alla Home</a>
    </main>
    <%@ include file="../footer.jsp" %>
</body>
</html>
