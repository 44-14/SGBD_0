<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 16-08-20
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Confirmation temporaire</title>
	<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/assets/css/signIn.css' />
</head>
<body>
	<p>Ceci est une confirmation d'inscription</p>
	<p>Votre nom est ${sessionScope.myValidAttributesSessionKey['usernameValid']}</p>
	<p>Votre mdp est ${sessionScope.myValidAttributesSessionKey['passwordValid']}</p>
	<p>Votre rôle est ${sessionScope.myValidAttributesSessionKey['passwordValid']}</p>

	<a href="${pageContext.request.contextPath}/signin"><button class="btn"> Appuyez ici pour continuer. </button></a>
</body>
</html>