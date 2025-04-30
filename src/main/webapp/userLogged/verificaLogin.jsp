<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String nome = (String) session.getAttribute("Nome");

    if (nome == null) {
        session.setAttribute("redirect", true);
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return; // Ferma l'esecuzione del resto della pagina
    } else {
        session.setAttribute("redirect", false);
    }
%>