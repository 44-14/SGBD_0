<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 10-08-20
  Time: 06:50
  To change this template use File | Settings | File Templates.
--%>

<!-- les attributs <name> des différentes inputs représentent les keys des paramètres de la requête HTTP qui seront récupérables coté serveur via request.getParameter["key"] -->

<!-- Attention : les erreurs de validation checkent juste si ce qui a été soumis par les inputs est susceptible d'exister en DB donc null empty les contraintes de db etc
mais un attribut validé n'est pas forcément existant en db, donc on peut retourner cela sans soucis vers les formulaires
Par contre, si les erreurs proviennent des verifications dans la db, il ne faut pas retourner les choses qui y étaient valides car cela donnerait des indications à de potentiels hackers
En effet, si je rentre Tototo en username, c est bien au dessus de 5 caractères et la chaine n est ni vide, ni null donc attribut valide
Si je cherche en db et que Tototo existe bien mais que le password est incorrect, je ne retourne pas que Tototo etait valide mais juste qu il y a eu une erreur
Car un hacker saurait que Tototo est un utilisateur existant et forcebrute pour le mdp etc
-->

<%--
<h1>HashMap contenant les erreurs de validation : <c:out value="${requestScope.myErrorsRequestKey}"/> </h1> <br/>
<h1>HashMap contenant les attributs valides : <c:out value="${requestScope.myValidAttributesRequestKey}"/> </h1>
--%>



<c:if test="${not empty requestScope.ServletErrorMessagesRequestKey && requestScope.ServletErrorMessagesRequestKey.size() > 0}">
    <div id="" class="alert alert-danger" role="alert">
        <ul>test
            <c:forEach items="${requestScope.ServletErrorMessagesRequestKey}" var="item">
                <li>${item}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>


<div class="mySmallContainer">
    <div class="mySmallContainer">
            <div class="title1"> School UX </div>
    </div>



    <div class="mySmallContainer">
            <div class="title2">Veuillez vous authentifier : </div>

            <form method="post" action="${pageContext.request.contextPath}/signin">
                <!--action correspond l'URI vers laquelle on envoie le contenu du formulaire -->
                <!-- contextePath est le point d'entrée de notre application -->

                <label for='idUsernameForm'>Username :</label>
                <input id='idUsernameForm' name='usernameFromForm' type='text' value='${requestScope.myValidAttributesRequestKey['usernameFromFormValid']}' size="30" minlength="5" maxlength="50" required autofocus />
                <span class="error">${requestScope.myErrorsRequestKey['usernameFromFormError']}</span>
                <span class="error">${requestScope.signInError}</span> <br/>

                <label for='idPwdForm'>Password :</label>
                <input  id='idPwdForm' name='passwordFromForm' type='password' value='${requestScope.myValidAttributesRequestKey['passwordFromFormValid']}' size="30" minlength="3" maxlength="50" required />
                <span class="error">${requestScope.myErrorsRequestKey['passwordFromFormError']}</span>
                <span class="error">${requestScope.signInError}</span> <br/>

                <button id="signInButton" class="btn btn-sm btn-outline-warning" name="" type="submit">Se connecter</button>

                <!--  <input name='btnSignIn' type='submit' value="Se connecter"/> <br/>  -->
            </form>
    </div>
</div>



    <div class="mySmallContainer">

        <a href="${pageContext.request.contextPath}/signup" id="signUpButton" class="btn btn-sm btn-outline-primary" >
                S'inscrire
        </a>
    </div>


    <!--
        Utiliser les button que pour submit de formulaire
         Pour un bouton qui pointe juste vers une url, il ne faut pas utiliser la balise button
         juste faire un <a href="maRef" class=" celles d'un des bouton ci-dessous
    -->
<%--
    <div class="mySmallContainer">
        <a href="${pageContext.request.contextPath}/signup">
            <button class="btn btn-sm btn-outline-primary" type="button">S'inscrire</button> <!-- blue -->
            <button class="btn btn-sm btn-outline-secondary" type="button">S'inscrire</button> <!-- dark gray -->
            <button class="btn btn-sm btn-outline-info" type="button">S'inscrire</button> <!-- turquoise -->
            <button class="btn btn-sm btn-outline-warning" type="button">S'inscrire</button> <!-- yellow -->
            <button class="btn btn-sm btn-outline-danger" type="button">S'inscrire</button> <!-- red -->
            <button class="btn btn-sm btn-outline-success" type="button">S'inscrire</button> <!-- green -->
            <button class="btn btn-sm btn-outline-light" type="button">S'inscrire</button> <!-- white -->


        </a>
    </div>
--%>



<%--<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%> --%>
