<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Marie-Élise
  Date: 28/07/2020
  Time: 18:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vos évenements prévus</title>
</head>
<body>
<table>
    <th></th>
    <tr>
        <c:forEach var="listEvents" items="${listEvent}">
    <tr>
        <td><c:out value="Nom du document: ${listEvents.name}"/></td>
        <td><c:out value="Description du document: ${listEvents.description}"/></td>
        <td><form method="post">
            <input type="hidden" name="idEventUpdate" id="idEventUpdate" value="${listEvents.id}">
            <input type="submit" name="updateEvent" value="updateEvent">
        </form></td>
        <td><form method="post">
            <input type="hidden" name="idEventDelete" id="idEventDelete" value="${listEvents.id}">
            <input type="submit" name="deleteEvent" value="deleteEvent">
        </form></td>
    </tr>
    </c:forEach>
    </tr>
</table>

</body>
</html>
