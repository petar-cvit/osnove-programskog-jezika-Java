<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<% if(request.getAttribute("nickTaken") != null) { %>
	<p style="color:red;">Nickname is already taken!</p>
<% } %>

<% if(request.getAttribute("fillAll") != null) { %>
	<p style="color:red;">All fields are mandatory!</p>
<% } %>

<%
String fn = request.getAttribute("reg.fn") == null ? 
		"" : (String) request.getAttribute("reg.fn"); 
String ln = request.getAttribute("reg.ln") == null ? 
		"" : (String) request.getAttribute("reg.ln"); 
String email = request.getAttribute("reg.email") == null ? 
		"" : (String) request.getAttribute("reg.email"); 
String nick = request.getAttribute("reg.nick") == null ? 
		"" : (String) request.getAttribute("reg.nick"); 					
%>

<form action="" method="POST">
			<label for="firstName">First name:</label>
			<input type="text" id="firstName" name="firstName" value="<%= fn %>"><br>
			
			<label for="lastName">Last name:</label>
			<input type="text" id="lastName" name="lastName" value="<%= ln %>"><br>
			
			<label for="email">Email:</label>
			<input type="text" id="email" name="email" value="<%= email %>"><br>
			
			<label for="nick">Nickname:</label>
			<input type="text" id="nick" name="nick" value="<%= nick %>"><br>
			
			<label for="password">Password:</label>
			<input type="password" id="password" name="password"><br><br>
			
			<input type="submit" value="Submit">
			<input type="reset" value="Reset">
</form>
<hr>
<a href="main">Back</a>
<hr>

</body>
</html>