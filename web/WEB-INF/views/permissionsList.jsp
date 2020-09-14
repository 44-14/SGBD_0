<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 20-08-20
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
    <meta charset="UTF-8" />
    <title>Liste des permissions</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/public/assets/css/myCss.css' />

</head>
<body>

    <!-- include de la barre de navigation -->
    <jsp:include page="/WEB-INF/content/navBar.jsp"></jsp:include>


    <!-- Table contenant les données des permissions -->
    <table>
        <c:forEach items="${requestScope.myPermissionsListRequestKey}" var="permission">
            <tr>
                <td>${permission.id}</td>
                <td><c:out value="${permission.abbreviation}" /></td>-->
                <td><c:out value="${permission.label}" /></td>
                <td>${permission.label}</td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>





