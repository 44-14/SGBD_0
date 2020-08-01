<%--
  Created by IntelliJ IDEA.
  User: Marie-Élise
  Date: 26/07/2020
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Créer vos évenements</title>
</head>
<body>

    <form method="post">
        <label for="name">*Nom de l'évènement: </label>
        <input type="text" name="name" id="name" required>

        <label for="beginDate">*Date de début:</label>
        <input type="date" name="beginDate" id="beginDate" required>
        <!-- min = "{sessionScope.dayDate}"-->

        <label for="endDate">*Date de fin: </label>
        <input type="date" name="endDate" id="endDate" required>

        <label for="beginHour">*Heure de début: </label>
        <input type="time" name="beginHour" id="beginHour" required>

        <label for="endHour">*Heure de fin: </label>
        <input type="time" name="endHour" id="endHour" required>

        <label for="description">*Description: </label>
        <input type="text" name="description" id="description" required>

        <label for="urlAttachment">Piéce jointe: </label>
        <input type="url" name="urlAttachment" id="urlAttachment">

        <input type="submit" name="Valider">
    </form>

</body>
</html>
