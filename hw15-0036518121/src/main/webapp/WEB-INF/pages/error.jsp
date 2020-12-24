<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% if(request.getSession().getAttribute("current.user.id") != null) { %>
	<p><%= request.getSession().getAttribute("current.user.fn") %> 
	<%= request.getSession().getAttribute("current.user.ln") %>
	a.k.a. <%= request.getSession().getAttribute("current.user.nick") %></p>
	<a href="/blog/servleti/logout">Logout</a>
	<hr>
<% } else { %>
	<p>Not logged in</p>
	<hr>
<% } %>
<p style="color: red; font-size: 40">Invalid url!</p>
<hr>
<a href="/blog/servleti/main">Home</a>
</body>
</html>