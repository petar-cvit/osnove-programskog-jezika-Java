<%@page import="java.time.Period"%>
<%@page import="java.time.LocalDateTime"%>
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
<% long miliseconds = System.currentTimeMillis() - ((long) request.getServletContext().getAttribute("date"));

long days = miliseconds / 86400000;
long hours = (miliseconds / 3600000) % 24;
long minutes = (miliseconds / 60000) % 60;
long seconds = (miliseconds / 1000) % 60;
long mili = miliseconds % 1000;

StringBuilder sb = new StringBuilder();
sb.append("System has been active for:\n");
sb.append(days == 0 ? "" : days + " days ");
sb.append(hours == 0 ? "" : hours + " hours ");
sb.append(minutes == 0 ? "" : minutes + " minutes ");
sb.append(seconds == 0 ? "" : seconds + " seconds ");
sb.append(mili == 0 ? "" : mili + " miliseconds.");
%>
<a href="./index.jsp">Home page</a>
<hr>
<h1><%= sb.toString() %></h1>

</body>
</html>