<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Background color chooser</title>
</head>
<body style='background-color:<%= request == null || request.getSession().getAttribute("pickedBgCol") == null ?
		"#FFFFFF" : "#" + request.getSession().getAttribute("pickedBgCol") %>;'>
	<a href="./index.jsp">Home page</a>
	<hr>
	<a href="./setcolor?pickedBgCol=FFFFFF">WHITE</a><br>
	<a href="./setcolor?pickedBgCol=FF0000">RED</a><br>
	<a href="./setcolor?pickedBgCol=00FF00">GREEN</a><br>
	<a href="./setcolor?pickedBgCol=0000FF">CYAN</a><br>
</body>
</html>