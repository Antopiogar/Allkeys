<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Profilo</title>
	<jsp:include page="verificaLogin.jsp" />
</head>
<body>
<%@ include file="../NavBar.jsp" %>

<%
	boolean redirect = Boolean.TRUE.equals(session.getAttribute("redirect"));
	if(redirect){
		out.println("Errore, stai per essere reindirizzato al login");
	}
	else{
		out.println("Benvenuto/a " + session.getAttribute("Nome"));
	}
%>
</body>
</html>