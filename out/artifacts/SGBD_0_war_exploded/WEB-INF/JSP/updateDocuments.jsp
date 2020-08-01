<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Marie-Élise
  Date: 19/07/2020
  Time: 09:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modifier votre document</title>
</head>
<body>
<!-- Ici, faire une separation selon le choix de modifier un document normalement (titre etc) ou de le delete (selon les droits toujours) -->

<c:out value=" haha ${sessionScope['monDocument'].label} id: ${sessionScope['monDocument'].id} " />
<c:out value="doc find one ${sessionScope['docFindOne'].label}"/>

<!-- recuperate the id of document choose in showDocuments for the update -->
<c:out value="TEST TEST ${sessionScope.idDocUpdate}"/>
RESTE A RECUPERER COMME POUR L IDLE LABEL ET LURL POUR LE PLACEHOLDER CORRECT
<p>Modifier votre document</p>

<form method="post">
    <label for="updateLabelDoc">Modifier votre titre:</label>
    <input type="text" name="updateLabelDoc" id="updateLabelDoc" placeholder="${sessionScope.labelDocUpdate}">

    <label for="updateUrlDoc">Modifier votre URL: </label>
    <input type="text" name="updateUrlDoc" id="updateUrlDoc" placeholder="${sessionScope}">

    <label for="updateTypeDoc">Modifier le type du document</label>
    <select id="updateTypeDoc" name="updateTypeDoc">
        <option value="bulletin">Bulletin</option>
        <option value="homework">Devoirs</option>
        <option value="inscription">Formulaire d'inscription</option>
        <option value="medical">Certificat médical</option>
        <option value="outCertificat">Bon de sorties</option>
        <option value="other">Autre</option>
    </select>
    <input type="text" name="other" id="other">

    <input type="hidden" name="idDocUpdate" id="idDocUpdate" value="${sessionScope.idDocUpdate}">


    <input type="submit" name="Confirmer">
</form>

<a href="showDocuments.jsp">Retour à l'affichage des documents</a>

</body>
</html>
