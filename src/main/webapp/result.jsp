<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "model.*" %>
<% 
	ArrayList<BeanArticolo> articoli = (ArrayList<BeanArticolo>) request.getAttribute("result");
	if(articoli == null){
		response.sendRedirect("ViewCatalog");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Catalogo articoli</title>
</head>
<body>
<%@ include file="NavBar.jsp" %>
    <h1>Catalogo articoli</h1>
    <% 	if (articoli != null && !articoli.isEmpty()) { 
        	for (BeanArticolo articolo : articoli) { %>

            <p><%= articolo.toString() %>
            <form action="CartServlet" method="POST">
            	<input type="text" value = "<%= articolo.getIdArticolo() %>" hidden = "true" name = idArticolo>
            	<input type = "text" value = "add" hidden = "true" name = "action">
            	<input type="submit" value="Aggiungi al carrello">
            </form>
            </p>
        <% } 
   		}else { %>
      	  <p>Nessun articolo disponibile.</p>
    <% } %>
</body>
</html>
