<%@page import="java.awt.Color"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ide dobar vic</title>
</head>
<body style='background-color:<%= request == null || request.getSession().getAttribute("pickedBgCol") == null ?
		"#FFFFFF" : "#" + request.getSession().getAttribute("pickedBgCol") %>;'>
<% Random random = new Random();
	Color color = new Color(random.nextFloat(),
								random.nextFloat(),
								random.nextFloat());
	String colorString = String.format("#%02X%02X%02X",
										color.getRed(),
										color.getGreen(),
										color.getBlue());
%>
<p style="color:<%= colorString %>">Idu dvije čačkalice ulicom i vide ježa. I kaže jedna drugoj: Pa mogli smo busom.</p>
</body>
</html>