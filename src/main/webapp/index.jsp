<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Allkeys</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>
<%@ include file="NavBar.jsp" %>
<main>
<%response.sendRedirect("result.jsp");%>
</main>
<%@ include file="footer.jsp" %>
</body>
</html>