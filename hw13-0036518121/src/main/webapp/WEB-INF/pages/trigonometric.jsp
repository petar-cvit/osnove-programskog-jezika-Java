<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<style>
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}
th, td {
  padding: 5px;
  text-align: left;
}
</style>
<meta charset="ISO-8859-1">
<title>Trigonometric</title>
</head>
<body style='background-color:<%= request == null || request.getSession().getAttribute("pickedBgCol") == null ?
		"#FFFFFF" : "#" + request.getSession().getAttribute("pickedBgCol") %>;'>
	<a href="./index.jsp">Home page</a>
	
	<table style="width:100%">
		<tr>
			<th>Degree</th>
			<th>Sine</th>
			<th>Cosine</th>
		</tr>
		<%
		List<Double> sin = (List<Double>) request.getSession().getAttribute("sin");
		List<Double> cos = (List<Double>) request.getSession().getAttribute("cos");
		Integer a = (Integer) request.getSession().getAttribute("a");
		for (int i = 0; i < sin.size(); i++) {
			%>
			<tr>
				<td><%= a + i %></td>
			   	<td><%= sin.get(i) %></td>
			   	<td><%= cos.get(i) %></td>
			</tr>
		<% } %>
	</table>
</body>
</html>