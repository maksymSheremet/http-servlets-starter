<%@ page import="my.code.practice_one.service.TicketService" %>
<%@ page import="my.code.practice_one.dto.TicketDto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Maksym
  Date: 09.08.2025
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Purchased tickets:</h1>
    <ul>
    <%
        Long flightId = Long.valueOf(request.getParameter("flightId"));
        List<TicketDto> tickets = TicketService.getInstance().findAllByFlightId(flightId);
        for (TicketDto ticket : tickets) {
            out.write(String.format("<li>%s</li>", ticket.getSeatNo()));
        }
    %>
    </ul>
</body>
</html>
<%!
    public void jspInit() {
        System.out.println("Hello World!");
    }
%>
