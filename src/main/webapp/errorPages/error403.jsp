<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <title>Errore 403 - Accesso Negato</title>
</head>
<body>
	<%@ include file="../NavBar.jsp" %>
	<main>
    <h1>Errore 403</h1>
    <p>Non hai i permessi per accedere a questa risorsa.</p>
    <br><a href="<%= request.getContextPath() %>/index.jsp">Torna alla Home</a>
    </main>
    <%@ include file="../footer.jsp" %>
</body>
</html>
