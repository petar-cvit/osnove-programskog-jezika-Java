<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home page</title>
</head>
<body style='background-color:<%= request == null || request.getSession().getAttribute("pickedBgCol") == null ?
		"#FFFFFF" : "#" + request.getSession().getAttribute("pickedBgCol") %>;'>
	<a href="./color.jsp">Background color chooser</a>
	<hr>
	<form action="trigonometric" method="GET">
 		Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 		Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 		<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
 	</form>
	<br>
	<a href="./trigonometric?a=0&b=90">Sine and cosine [0, 90]</a>
	<hr>
	<a href="./stories/funny.jsp">Funny joke</a>
	<hr>
	<a href="./report.jsp">Report</a>
	<hr>
	<form action="powers" method="GET">
 		Početna vrijednost:<br><input type="number" name="a" min="-100" max="100" step="1" value="0"><br>
 		Završna vrijednost:<br><input type="number" name="b" min="-100" max="100" step="1" value="0"><br>
 		Potencija:<br><input type="number" name="n" min="0" max="5" step="1" value="0"><br>
 		<input type="submit" value="Potenciraj"><input type="reset" value="Reset">
	</form>
	
	<br>
	<a href="./powers?a=1&b=100&n=3">Skini preddefiniranu tablicu!</a>
	<hr>
	<a href="./appInfo.jsp">Application Info</a>
	<hr>
	<a href="./glasanje">Voting</a>
	
</body>
</html>