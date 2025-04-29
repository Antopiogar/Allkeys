<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
 	String nome = (String) request.getSession().getAttribute("Nome");
 	if(nome == null){
 		out.print("<meta http-equiv='refresh' content='5;url=../login.jsp' />");
 		request.getSession().setAttribute("redirect", true);
 	}
 	else{
 		request.getSession().setAttribute("redirect", false);

 	}
 	
 %>