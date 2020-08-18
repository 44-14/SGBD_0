<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 10-08-20
  Time: 06:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>

<html>

<head>
    <meta charset="UTF-8" />
    <title>Bienvenue</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/assets/css/signIn.css' />
</head>

<body>
    <div id="maDiv">
        <div id="block">
        <span id="ask">Bienvenue Mister ${usernameSessionKey} </span>
        </div>

        <a href="${pageContext.request.contextPath}/public/home.jsp"><button class="btn"> Appuyez ici pour continuer. </button></a>

    </div>
</body>
</html>





<%--<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%> --%>
