<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session = "true" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Acquista</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
</head>
<body>
<%@ include file="NavBar.jsp" %>
<main><%if(session.getAttribute("Nome") == null || (int)session.getAttribute("idUser") == -1 || session.getAttribute("idUser") == null)
{
	session.setAttribute("ConfermaOrdineRedirect","true");
	response.sendRedirect(request.getContextPath() + "/login.jsp");
}
%>
	<%!ArrayList<BeanCartaPagamento> carte = null;%>
	<%! Integer id = null;%><%
	Object ido = session.getAttribute("idUser");
	if(ido != null) id = (Integer) ido;
	else id = -1;
	%>
	<% carte = CartaPagamentoDAO.loadCartaPagamentoByIdUtente(id);%>
	<%if(carte.size() == 0){
		%><h1>Nessuna carta memorizzata!</h1>
		  <form action ="<%= request.getContextPath() %>/addNewPaymentMethod.jsp" method ="POST">
		  	<input type="submit" value="Aggiungi nuova carta">
		  </form>
	<%} else{
		
	%>
		<h1>Scegli la carta di pagamento da usare:</h1><br><br>
		<form action="<%= request.getContextPath() %>/orderStatus.jsp" method="POST">
		<%for(BeanCartaPagamento i: carte){ %>
			<div>
				<input type="radio" id="carta_<%=i.getIdCarta()%>" name="idCartaSelezionata" value="<%=i.getIdCarta()%>" required>
				<label for="carta_<%=i.getIdCarta()%>">
					Carta: <%=i.getnCarta()%>
				</label>
			</div><br>
		<%} %>
			<input type="submit" value="Procedi con il pagamento">
		</form>
	<%}%>
</main>
<%@ include file="footer.jsp" %>
</body>
</html>
