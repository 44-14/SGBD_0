<%--
  Created by IntelliJ IDEA.
  User: Marie-Élise
  Date: 29/07/2020
  Time: 12:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modifier un évènement</title>
</head>
<body>
    <form method="post">
        <label for="updateNameEvent">Nom de l'évènement</label>
        <input type="text" name="updateNameEvent" id="updateNameEvent">

        <input type="hidder" name="idEventUpdate" id="idEventUpdate" value="${sessionScope.idEventUpdate}">

        <input type="submit" name="Modifier">
    </form>

</body>
</html>
