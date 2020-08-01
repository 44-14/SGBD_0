<%--
  Created by IntelliJ IDEA.
  User: Marie-Élise
  Date: 29/07/2020
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Suppression</title>
</head>
<body>
<p>Voulez vous réelement supprimer ce document? <!-- ici: nom + type de doc --></p>
<form method="post">
    <label for="yes">Oui : </label>
    <input type="radio" name="choiceDelete" value="yes" id="yes" />

    <label for="no">Non : </label>
    <input type="radio" name="choiceDelete" value="no" id="no" />

    <input type="hidden" name="idEventDelete" id = "idEventDelete" value="${sessionScope.idEventDelete}">
    <input type="submit" name = "Valider">
</form>

</body>
</html>
