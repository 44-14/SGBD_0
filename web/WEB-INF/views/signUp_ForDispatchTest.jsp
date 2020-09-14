<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 16-08-20
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!-- Version simplifiée pour test le chemin -->


<!DOCTYPE html>

<html>

<head>
    <meta charset="UTF-8" />
    <title>Création utilisateur</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/public/assets/css/myCss.css' />
</head>

<body>

<!-- les attributs <name> des différentes balises représentent les noms des paramètres de la requête HTTP qui seront récupérables coté serveur -->

    <%--
    <c:out value="${sessionScope.myErrorsSessionKey}"/>
    <c:out value="${sessionScope.myValidAttributesSessionKey}"/>
    --%>



    <div class="maDiv">
        <span class="ask">Veuillez compléter le formulaire d'inscription : </span>

        <!-- Create an user -->
        <form method="post" action="${pageContext.request.contextPath}/signup">

            <!-- First Name -->
            <label for='idFirstNameForm'>Prénom :</label>
            <input id='idFirstNameForm' name='firstNameFromForm' type='text'
                   value='${sessionScope.myValidAttributesSessionKey['firstNameValid']}'  size="30" maxlength="50" required autofocus  />
            <span class="erreur">${sessionScope.myErrorsSessionKey['firstNameError']}</span> <br/>

            <input name='btnCreateUser' type='submit' value="Créer un nouvel utilisateur"/> <br/>
        </form>

    </div>



</body>
</html>

