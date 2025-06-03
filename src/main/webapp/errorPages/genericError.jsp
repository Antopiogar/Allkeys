<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <title>Errore imprevisto</title>
</head>
<body>
	<%@ include file="../NavBar.jsp" %>
	<main>
    <h1>Errore imprevisto</h1>
    <p>Si è verificato un errore non previsto.</p><br>
    <p><strong>Dettagli:</strong></p>
    <pre><%= exception != null ? exception.getMessage() : "Errore sconosciuto." %></pre>
    <br><a href="<%= request.getContextPath() %>/index.jsp">Torna alla Home</a>
    </main>
    <%@ include file="../footer.jsp" %>
</body>
</html>
