<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 20-08-20
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

    <!-- Table contenant les données des permissions -->
    <table>
        <c:forEach items="${requestScope.myPermissionsListRequestKey}" var="permission">


            <tr>
                <td>${permission.id}</td>
                <td><c:out value="${permission.abbreviation}" /></td>
                <td><c:out value="${permission.label}" /></td>
                <td>${permission.label}</td>
                <td>

                    <form method="post">
                        <input type="hidden" id="idPermissionFormShow" name="idPermissionFromForm"  value="${permission.id}">
                        <button type='submit' name="actionFromForm" value="readOne" > Afficher </button>
                    </form>
                    <form method="post">
                        <input type="hidden" id="idPermissionFormEdit" name="idPermissionFromForm"  value="${permission.id}">
                        <button type='submit' name="actionFromForm" value="editOne" > Editer </button>
                    </form>
                    <form method="post">
                        <input type="hidden" id="idPermissionFormDelete" name="idPermissionFromForm"  value="${permission.id}">
                        <button type='submit' name="actionFromForm" value="deleteOne" > Supprimer </button>
                    </form>

                </td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>





