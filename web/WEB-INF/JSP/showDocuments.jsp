<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Marie-Élise
  Date: 15/07/2020
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vos documents</title>
</head>
<body>
<p>cool</p>
<table>
    <th></th>
    <tr>
        <c:forEach var="listDocuments" items="${listDoc}">
            <tr>
                <td><c:out value="Nom du document: ${listDocuments.label}"/></td>
                <td><c:out value="Type du document: ${listDocuments.type}"/></td>
                <td><form method="post">
                    <input type="hidden" name="idDocUpdate" id="idDocUpdate" value="${listDocuments.id}">
                    <input type="hidden" name="labelDocUpdate"  id="labelDocUpdate" value="${listDocuments.label}">
                    <input type="hidden" name="typeDocUpdate" value="${listDocuments.type}">
                    <input type="submit" name="updateDoc" value="updateDoc">
                </form></td>
                 <td><form method="post">
                    <input type="hidden" name="idDocDelete" id="idDocDelete" value="${listDocuments.id}">
                    <input type="submit" name="deleteDoc" value="deleteDoc">
                 </form></td>
            </tr>
        </c:forEach>
    </tr>
</table>

<c:out value="hey hey"/>

</body>
</html>
