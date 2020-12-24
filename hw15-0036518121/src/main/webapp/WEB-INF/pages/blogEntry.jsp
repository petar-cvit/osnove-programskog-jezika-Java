<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogComment"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Blog</title>
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

<h2>${ blogTitle }</h2>
<p style="font-size: 8">Posted on: ${ blogCreated }</p>
<hr>
<p>${ blogText }</p>

<hr>

<% List<BlogComment> blogComments = (List<BlogComment>) request.getAttribute("blogComments");
if(blogComments == null || blogComments.isEmpty()) { %>
<h2>Write the first comment.</h2>
<% } else { %>
<h2>Comments:</h2>
<ul>
<c:forEach var="e" items="${blogComments}">
	<li>${ e.message }</li>
</c:forEach>
</ul>
<% } %>

<hr>

<% if(request.getSession().getAttribute("current.user.id") != null) { %>
	<form action="/blog/servleti/comment" method="POST">
			<label for="comment">Comment:</label>
			<input type="text" id="comment" name="comment"><br><input type="submit" value="Submit">
			<input type="reset" value="Reset">
	</form>
	<hr>
<% } %>

<% if(request.getSession().getAttribute("current.user.nick") != null &&
		request.getSession().getAttribute("current.user.nick").equals(request.getSession().getAttribute("authorsNick"))) { %>
<a href="/blog/servleti/author/${authorsNick}/edit">Edit blog</a>
<hr>
<% } %>
<a href=".">Back</a>

</body>
</html>