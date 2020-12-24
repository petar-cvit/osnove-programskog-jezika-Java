<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.p12.model.Option"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1><%= request.getSession().getAttribute("title") %></h1>
 <p><%= request.getSession().getAttribute("message") %></p>
<% List<Option> options = (List<Option>) request.getSession().getAttribute("options"); %>
 	<ol>
 		<% for(Option o : options) {%>
		 <li><a href=<%= "glasanje-glasaj?id=" + o.getId() %>><%= o.getName() %></a></li>
		<% } %>
	</ol>
</body>
</html>