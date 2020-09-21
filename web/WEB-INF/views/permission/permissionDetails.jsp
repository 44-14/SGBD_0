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
    <title>Détails de la permission ${requestScope.myPermissionRequestKey.abbreviation}</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/public/assets/css/customLandingCSS.css' />

</head>
<body>

    <!-- include de la barre de navigation -->
    <%--<jsp:include page="/WEB-INF/content/navbar.jsp"></jsp:include>--%>


    <!-- Table contenant les données de la permissions -->
    <table>
            <tr>
                <td>${requestScope.myPermissionRequestKey.id}</td>
                <td><c:out value="${requestScope.myPermissionRequestKey.abbreviation}" /></td>
                <td><c:out value="${requestScope.myPermissionRequestKey.label}" /></td>
                <td><c:out value="${requestScope.myPermissionRequestKey.description}" /></td>

                    <c:forEach items="${requestScope.myPermissionRequestKey.rolesPermissionsById}" var="role">
                        <td><c:out value="${role.rolesByIdRole.label}"/> </td>
                    </c:forEach>


            </tr>


    </table>

</body>
</html>





