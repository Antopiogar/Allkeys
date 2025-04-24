<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String nome = (String) request.getSession().getAttribute("Nome");
%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/NavBar.css">
<div class="navbar">
    <div class="logo">
    	<a href="index.jsp">Allkeys</a>
    </div>
    <% if(nome != null){%>
	    <div class="dropdown"> 
	      <button class="dropbtn"><%= nome %></button>
	      <div class="dropdown-content">
	        <a href="userLogged/profilo.jsp">Profilo</a>
	        <a href="<%= request.getContextPath() %>/logout">Logout</a>
	      </div>
	    </div>
	<%}else { %>
		<a href="register.jsp">Registati</a>
		<a href="login.jsp">Login</a>
		
	<%} %>
</div>