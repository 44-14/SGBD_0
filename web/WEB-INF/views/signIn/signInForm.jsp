<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 10-08-20
  Time: 06:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>SignIn</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/public/assets/css/myCss.css' />
</head>
<body>

<!-- les attributs <name> des différentes balises représentent les noms des paramètres de la requête HTTP qui seront récupérables coté serveur -->


    HashMap contenant les erreurs : <c:out value="${requestScope.myErrorsRequestKey}"/> <br/>
    HashMap contenant les attributs valides : <c:out value="${requestScope.myValidAttributesRequestKey}"/>


    <div class="maDiv">
            <span id="mainTitle"> School UX </span>
    </div>

    <div class="maDiv">
            <span class="ask">Veuillez vous authentifier : </span>

            <form method="post" action="${pageContext.request.contextPath}/signin">
                <!--action correspond l'URL vers laquelle on envoie le contenu du formulaire -->
                <!-- contextePath est le point d'entrée de notre application -->

                <label for='idUsernameForm'>Username :</label>
                <input id='idUsernameForm' name='usernameFromForm' type='text' value='${requestScope.myValidAttributesRequestKey['usernameFromFormValid']}' size="30" maxlength="50" required autofocus /> <br/>
                <span class="error">${requestScope.myErrorsRequestKey['usernameFromFormError']}</span> <br/>

                <label for='idPwdForm'>Password :</label>
                <input  id='idPwdForm' name='passwordFromForm' type='password' value='${requestScope.myValidAttributesRequestKey['passwordFromFormValid']}' size="30" maxlength="50" required /> <br/>
                <span class="error">${requestScope.myErrorsRequestKey['passwordFromFormError']}</span> <br/>

                <input name='btnSignIn' type='submit' value="Se connecter"/> <br/>
            </form>

    </div>



    <div class="maDiv">

            <span class="ask">Pas encore inscrit ? </span>

                <a id="monAncre" href="${pageContext.request.contextPath}/signup">
                    <button>S'inscrire</button>
                </a>

    </div>
</body>
</html>





<%--<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%> --%>
