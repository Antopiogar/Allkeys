<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "model.*" %>
<% 
	ArrayList<BeanArticolo> articoli = (ArrayList<BeanArticolo>) request.getAttribute("result");
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Catalogo articoli</title>
</head>
<body>
    <h1>Catalogo articoli</h1>
    <% 	if (articoli != null && !articoli.isEmpty()) { 
        	for (BeanArticolo articolo : articoli) { %>
            <p><%= articolo.toString() %></p>
        <% } 
   		}else { %>
      	  <p>Nessun articolo disponibile.</p>
    <% } %>
</body>
</html>
