<%@page import="hr.fer.zemris.java.p12.model.Option"%>
<%@page import="java.util.List"%>
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
<body>
<% 	List<Option> options = (List<Option>) request.getSession().getAttribute("options");
List<Option> winners = (List<Option>) request.getSession().getAttribute("winners");%>
<h1>Rezultati glasanja</h1>
 <p>Ovo su rezultati glasanja.</p>
 <table border="1" cellspacing="0" class="rez">
	 <thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
	 <tbody>
	 	<%for(Option o : options) { %>
	 		<tr><td><%= o.getName() %></td><td><%= o.getVotes() %></td></tr>
		 <% } %>
	 </tbody>
 </table>
 
 <h2>Grafički prikaz rezultata</h2>
 <img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
 
 <h2>Rezultati u XLS formatu</h2>
 <p>Rezultati u XLS formatu dostupni su <a href="./glasanje-xls">ovdje</a></p>

 <h2>Razno</h2>
 <p>Opcije koje su imale najviše glasova na anketi:</p>
 
 <ul>
 	<%for(Option o : winners) { %>
		 	<li><a href="<%= o.getLink() %>" target="_blank"><%= o.getName() %></a></li>
	<% } %>
 </ul>
</body>
</html>