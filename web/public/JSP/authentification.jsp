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
    <title>Authentification</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/assets/css/authentification.css' />
</head>

<body>

        <span id="mainTitle"> School UX </span>
        <div id="maDiv">
            <span id="ask">Veuillez vous authentifier : </span>

            <form method="post" action="auth">
                <label for='idUsernameForm'>Username :</label>
                <input id='idUsernameForm' name='usernameFromForm' type='text' value='${usernameSessionKey}' autofocus /> <br/>
                <label for='idPwdForm'>Password :</label>
                <input  id='idPwdForm' name='passwordFromForm' type='password' value='${passwordSessionKey}' /> <br/>
                <br/>
                <input name='btnConnect' type='submit' /> <br/>
            </form>
        </div>

</body>
</html>





<%--<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%> --%>
