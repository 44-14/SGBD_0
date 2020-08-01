<%--
  Created by IntelliJ IDEA.
  User: Marie-Élise
  Date: 25/07/2020
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Supprimer un document</title>
</head>
<body>

<!-- recuperate the id of document choose in showDocuments for the update -->
<c:out value="TEST TEST ${sessionScope.idDocDelete}"/>

<p>Voulez vous réelement supprimer ce document? <!-- ici: nom + type de doc --></p>
<form method="post">
    <label for="yes">Oui : </label>
    <input type="radio" name="choiceDelete" value="yes" id="yes" />

    <label for="no">Non : </label>
    <input type="radio" name="choiceDelete" value="no" id="no" />

    <input type="hidden" name="idDocDelete" id = "idDocDelete" value="${sessionScope.idDocDelete}">
    <input type="submit" name = "Valider">
</form>

</body>
</html>
