<%--
  Created by IntelliJ IDEA.
  User: Marie-Élise
  Date: 01/08/2020
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajoutez vos médias</title>
</head>
<body>
    <form method="post">
        <label for="nameMedia">*Nom de votre média:</label>
        <input type="text" name="nameMedia" id="nameMedia" required>

        <label for="typeMedia">*Type de média:</label>
        <select id="typeMedia" name="typeMedia">
            <option value="photo">Photo</option>
            <option value="video">Vidéo</option>
            <option value="audio">Audio</option>
            <option value="other">Autre</option>
        </select>

        <label for="formatMedia">*Format du média:</label>
        <input type="text" name="formatMedia" id="formatMedia">

        <label for="urlMedia">Lien de votre média:</label>
        <input type="url" name="urlMedia" id="urlMedia">

        <input type="submit" name="Valider">
    </form>

</body>
</html>
