<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main</title>
</head>
<body>

<% if(request.getSession().getAttribute("current.user.id") != null) { %>
	<p><%= request.getSession().getAttribute("current.user.fn") %> 
	<%= request.getSession().getAttribute("current.user.ln") %>
	a.k.a. <%= request.getSession().getAttribute("current.user.nick") %></p>
	<a href="/blog/servleti/logout">Logout</a>
<% } else { %>

<p>Not logged in</p>
<hr>

<% if(request.getAttribute("notFound") != null) { %>
	<p style="color:red;">User not found! (Check username)</p>
<% } %>
<% if(request.getAttribute("invalidPassword") != null) { %>
	<p style="color:red;">Invalid password! (Check username and password)</p>
<% } %>

<% String nick = request.getAttribute("nick") == null ? 
								"" : (String) request.getAttribute("nick"); %>
<form action="" method="POST">
			<label for="nick">Nickname:</label>
			<input type="text" id="nick" name="nick" value="<%= nick  %>"><br>
			<label for="password">Password:</label>
			<input type="text" id="password" name="password"><br><br>
			<input type="submit" value="Submit">
			<input type="reset" value="Reset">
</form>
<hr>
<a href="registration">Register</a>
<% } %>
<hr>
<% List<BlogUser> blogUsers = (List<BlogUser>) request.getSession().getAttribute("blogUsers");
if(blogUsers == null || blogUsers.isEmpty()) { %>
<h2>No authors are registered.</h2>
<% } else { %>
<h2>List of all registered authors:</h2>
<ul>
<c:forEach var="e" items="${blogUsers}">
	<li><a href="author/${ e.nick }">${ e.nick }</a></li>
</c:forEach>
</ul>
<% } %>
</body>
</html>