<%--
  Created by IntelliJ IDEA.
  User: Maksym
  Date: 18.08.2025
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%-- Директива page: встановлює мову, кодування та імпортує клас Date --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.Date" %>
<%-- Директива taglib: для використання JSTL Core Library --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Привіт з JSP!</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        h1 { color: #333; }
        p { color: #555; }
        ul { list-style-type: disc; margin-left: 20px; }
        li { margin-bottom: 5px; }
        .info { background-color: #e9ecef; padding: 10px; border-left: 5px solid #007bff; margin-top: 15px; }
    </style>
</head>
<body>
<div class="container">
    <h1>Ласкаво просимо на нашу JSP-сторінку!</h1>

    <%-- Вираз JSP: виводить поточну дату --%>
    <p>Поточна дата на сервері: <b><%= new Date() %></b></p>

    <%-- Скриптлет (не рекомендовано для складних логік, але для прикладу): --%>
    <%
        String greetingMessage = "Це повідомлення з Java-коду всередині JSP.";
        // Можна отримати атрибути з request, session, application
        String userNameFromRequest = (String) request.getAttribute("userName");
        Integer visitCountFromSession = (Integer) session.getAttribute("sessionVisitCount");
        Date appStartTimeFromContext = (Date) application.getAttribute("appStartupTime");
    %>

    <p><%= greetingMessage %></p>

    <%-- Використання Expression Language (EL) для доступу до атрибутів --%>
    <div class="info">
        <h2>Інформація, передана з сервлету (через атрибути):</h2>
        <ul>
            <li>Ім'я користувача (з Request Scope): <b>${requestScope.userName}</b></li>
            <li>Повідомлення запиту (з Request Scope): <b>${requestScope.requestMessage}</b></li>
            <li>Кількість візитів (з Session Scope): <b>${sessionScope.sessionVisitCount}</b></li>
            <li>Час останнього доступу до сесії (з Session Scope): <b>${sessionScope.lastSessionAccess}</b></li>
            <li>Час запуску додатку (з Application Scope): <b>${applicationScope.appStartupTime}</b></li>
            <li>Глобальна кількість запитів (з Application Scope): <b>${applicationScope.globalRequestCount}</b></li>
        </ul>
    </div>

    <%-- Приклад використання JSTL (якщо JSTL JAR додано до проекту) --%>
    <%-- Перевіряємо, чи існує атрибут userName --%>
    <c:if test="${not empty requestScope.userName}">
        <p>Привіт, <c:out value="${requestScope.userName}" />! Ми раді вас бачити.</p>
    </c:if>
    <c:if test="${empty requestScope.userName}">
        <p>Привіт, незнайомець! Будь ласка, увійдіть.</p>
    </c:if>

    <%-- Коментар JSP: не буде видно у вихідному HTML --%>
    <%-- Ця частина коду є лише для демонстрації JSP-коментарів --%>

    <!-- Це HTML коментар: буде видно у вихідному HTML -->
</div>
</body>
</html>

