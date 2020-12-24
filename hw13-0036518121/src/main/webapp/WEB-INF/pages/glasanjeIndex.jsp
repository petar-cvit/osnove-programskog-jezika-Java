<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body style='background-color:<%= request == null || request.getSession().getAttribute("pickedBgCol") == null ?
		"#FFFFFF" : "#" + request.getSession().getAttribute("pickedBgCol") %>;'>
	<h1>Glasanje za omiljeni bend:</h1>
 <p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
<% Map<String, String> bands = (Map<String, String>) request.getSession().getAttribute("bands"); %>
 	<ol>
 		<% for(Entry<String, String> e : bands.entrySet()) {%>
		 <li><a href=<%= "glasanje-glasaj?id=" + e.getKey() %>><%= e.getValue() %></a></li>
		<% } %>
	</ol>
</body>
</html>