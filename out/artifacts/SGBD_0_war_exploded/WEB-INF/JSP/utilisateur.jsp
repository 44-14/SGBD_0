<%@ page import="be.src.persistence.model.DocumentsEntity" %>

<%@ page import="be.src.servlet.ReadUser" %><%--
  Created by IntelliJ IDEA.
  User: Marie-Élise
  Date: 27/05/2020
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Jsuis un gars ou une fille</title>
</head>
<body>

Mon utilisateur n° :
<c:out value="jaime bien quand ca marche!" />
<c:out value=" haha ${sessionScope['monUtilisateur'].getNom() }" />

<form>
    <input type="text" name="nom" id="nom"/>
    <input type="text" name="prenom" id="prenom">
    <input type="submit" name="velider">
</form>


</body>
</html>
