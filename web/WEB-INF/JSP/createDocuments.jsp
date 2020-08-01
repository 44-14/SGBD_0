<%--
  Created by IntelliJ IDEA.
  User: Marie-Élise
  Date: 14/07/2020
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Créer ou importer votre document</title>
</head>
<body>


<!-- PREREQUIS : Possiblité d'acceder à cette page ou de créer le document seulement si l'user est connécté  -->
<!-- show errors or confirmation -->

<c:out value="${sessionScope.error}"/>

<!-- Create a document -->

<form method="post" >
    <!--  Name of the document -->
    <label for="labelDoc">Nom du document: *</label>
    <input type="text" id="labelDoc" name="labelDoc" required>

    <!--  Type of the document -->
    <label for="typeDoc">Type de document: *</label>
    <select id="typeDoc" name="typeDoc">
        <option value="bulletin">Bulletin</option>
        <option value="homework">Devoirs</option>
        <option value="inscription">Formulaire d'inscription</option>
        <option value="medical">Certificat médical</option>
        <option value="outCertificat">Bon de sorties</option>
        <option value="other">Autre</option>
    </select>

    <!-- <label for="fileDoc">Fichier: </label>
    <input type="file" name="fileDoc" id="fileDoc">
    -->
    <!--  Format of the document -->
    <label for="formatDoc">Format du document: </label>
    <select id="formatDoc" name="formatDoc" >
        <option value="txt">.txt</option>
        <option value="doc">.doc</option>
        <option value="other">Autre</option>
    </select>
    <label for="otherFormatDoc">Autre format: </label>
    <input type="text" name="otherFormatDoc" id="otherFormatDoc">

    <!--  URL of the document. Not required -->
    <label for="urlDoc">Lien: *</label>
    <input type="url" id="urlDoc" name="urlDoc" required>

    <!--  Send the form -->
    <input type="submit" name="valider">
</form>

<!-- Import a document -->
<p>salut creation doc</p>
</body>
</html>
