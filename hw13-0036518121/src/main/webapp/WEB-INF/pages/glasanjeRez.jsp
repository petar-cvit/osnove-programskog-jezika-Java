<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.p11.servleti.Quadraple"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	table.rez td {text-align: center;}
</style>
</head>
<body style='background-color:<%= request == null || request.getSession().getAttribute("pickedBgCol") == null ?
		"#FFFFFF" : "#" + request.getSession().getAttribute("pickedBgCol") %>;'>
<% 	List<Quadraple> bandsResults = (List<Quadraple>) request.getSession().getAttribute("bandsResults"); 
	List<Quadraple> topBands = (List<Quadraple>) request.getSession().getAttribute("topBands");%>
<h1>Rezultati glasanja</h1>
 <p>Ovo su rezultati glasanja.</p>
 <table border="1" cellspacing="0" class="rez">
	 <thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
	 <tbody>
	 	<%for(Quadraple entry : bandsResults) { %>
	 		<tr><td><%= entry.getName() %></td><td><%= entry.getVotes() %></td></tr>
		 <% } %>
	 </tbody>
 </table>
 
 <h2>Grafički prikaz rezultata</h2>
 <img alt="Pie-chart" src="./glasanje-grafika" width="400" height="400" />
 
 <h2>Rezultati u XLS formatu</h2>
 <p>Rezultati u XLS formatu dostupni su <a href="./glasanje-xls">ovdje</a></p>

 <h2>Razno</h2>
 <p>Primjeri pjesama pobjedničkih bendova:</p>
 
 <ul>
 	<%for(Quadraple entry : topBands) { %>
		 	<li><a href="<%= entry.getSong() %>" target="_blank"><%= entry.getName() %></a></li>
	<% } %>
 </ul>
</body>
</html>