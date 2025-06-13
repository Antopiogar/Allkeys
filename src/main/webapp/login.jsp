<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
    
<%if(!(session.getAttribute("Nome") != null && !session.getAttribute("Nome").equals(""))){%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
<script type="text/javascript" src ="<%= request.getContextPath() %>/js/login.js" defer></script>

</head>
<body>
<%@ include file="NavBar.jsp" %>
<main>
	
	<h1>Accedi</h1><br>
	<form method = "POST" id="formLogin">
		<label for="email">Email</label>
		<input type="email" name = "email" required id ="email" placeholder = "es. mario.rossi@email.it">
		<br><br><label for="password">Password</label>
		<input type="password" name = "password" required id ="pass" placeholder="es. ProductKey12@">
		<%
			String emailEsistente = (String)session.getAttribute("EmailEsistente");
			boolean loginFallito = Boolean.TRUE.equals(session.getAttribute("LoginFallito"));
			boolean br = true;
			if(emailEsistente!=null &&  emailEsistente.equalsIgnoreCase("esistente")){
				%><br><br><div class = "messaggio-errore" id ="errorInfo">Email già registrata</div><%
				br = false;
				session.setAttribute("RisultatoLogin",null);
			}
			else if(emailEsistente!=null && emailEsistente.equalsIgnoreCase("non esistente")){
				%><br><br><div class = "messaggio-successo" id ="errorInfo">Registrazione riuscita</div><%
				br = false;
				session.setAttribute("RisultatoLogin",null);
			}
			else if(loginFallito){
				br = false;
				%><br><br><div class = "messaggio-errore" id ="errorInfo">Email o password errati</div><%
			}
	
	%>
		
		<%if(br == true){%><br><br><%}%><button type="submit" onclick="check('user')">Accedi</button>
		<span>Non sei registrato? <a href = "register.jsp">Registrati ora</a></span>
		<br><div id="errore"></div>
	</form>
</main>
	<%@ include file="footer.jsp" %>
</body>
</html><%}
else{ response.sendRedirect(request.getContextPath() + "/index.jsp");}%>