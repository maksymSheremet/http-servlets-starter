<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Включена JSP</title>
</head>
<body>
<h3>Привіт з включеної JSP-сторінки!</h3>
<p>Я можу отримати атрибути з запиту:</p>
<ul>
    <li>Повідомлення запиту: <%= request.getAttribute("requestMessage") %>
    </li>
    <li>Ім'я користувача: <%= request.getAttribute("userName") %>
    </li>
</ul>
<p>І навіть з сесії (якщо вона активна):</p>
<ul>
    <li>Кількість візитів у сесії: <%= session.getAttribute("sessionVisitCount") %>
    </li>
</ul>
<p>І з контексту додатка:</p>
<ul>
    <li>Глобальна кількість запитів: <%= application.getAttribute("globalRequestCount") %>
    </li>
</ul>
</body>
</html>