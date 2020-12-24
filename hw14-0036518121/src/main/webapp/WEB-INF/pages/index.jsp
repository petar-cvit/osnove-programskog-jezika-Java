<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="hr.fer.zemris.java.p12.model.Poll"%>
<%@page import="java.util.List"%>
<%
  List<Poll> polls = (List<Poll>)request.getSession().getAttribute("polls");
%>
<html>
  <body>

  <b>Pronađeni su sljedeći unosi:</b><br>

  <% if(polls.isEmpty()) { %>
    No entries.
  <% } else { %>
    <ul>
    <% for(Poll poll : polls) { %>
    <li><a href="./glasanje?pollID=<%= poll.getId() %>"><%= poll.getTitle() %></a></li>
    <% } %>  
    </ul>
  <% } %>

  </body>
</html>