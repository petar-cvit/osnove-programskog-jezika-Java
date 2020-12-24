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

<h1>Create a new blog!</h1>
<hr>
<form action="/blog/servleti/author/newBlog/<%= request.getSession().getAttribute("current.user.nick") %>" method="POST">
			<label for="blogTitle">Blog title:</label>
			<input type="text" id="blogTitle" name="blogTitle"><br>
			<label for="blogText">Text:</label>
			<textarea type="text" id="blogText" name="blogText"></textarea><br><br>
			<input type="submit" value="Submit">
			<input type="reset" value="Reset">
</form>
<hr>
<a href=".">Back</a>
</body>
</html>