<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogUser"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Blogs by author</title>
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

<% List<BlogEntry> blogEntries = (List<BlogEntry>) request.getAttribute("blogEntries"); %>
<% if(blogEntries == null || blogEntries.isEmpty()) { %>
<h2>No blogs by author ${authorsNick}.</h2>
<% } else { %>
<h2>List of all blogs by author ${authorsNick} :</h2>
<ul>
<c:forEach var="e" items="${blogEntries}">
	<li><a href="/blog/servleti/author/${ authorsNick }/${ e.id }">${ e.title }</a></li>
</c:forEach>
</ul>
<% } %>

<% if(request.getSession().getAttribute("current.user.nick") != null &&
		request.getSession().getAttribute("current.user.nick").equals(request.getAttribute("authorsNick"))) { %>
<a href="/blog/servleti/author/${authorsNick}/new">Write a new blog!</a>
<% } %>
<hr>
<a href="/blog/servleti/main">Back</a>
<hr>
</body>
</html>