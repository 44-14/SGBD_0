<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 20-08-20
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>

    <!-- Table contenant les données des roles -->
    <table>
        <c:forEach  var="role"   items="${requestScope.myRoleListRequestKey}">

            <tr>
                <td>${role.id}</td>
                <td>${role.label}</td>
                <td><c:out value="${role.label}" /></td>
                <td><c:out value="${role.abbreviation}" /></td>
                <td><c:out value="${role.description}" /></td>
                <td><c:out value="${role.rolesPermissionsById}" /></td>
                <td><c:out value="${role.usersById}" /></td>
                <td>

                    <form method="post">
                        <input type="hidden" id="idRoleFormShow" name="idRoleFromForm"  value="${role.id}">
                        <button type='submit' name="actionFromForm" value="readOne" > Afficher </button>
                    </form>
                    <form method="post">
                        <input type="hidden" id="idRoleFormEdit" name="idRoleFromForm"  value="${role.id}">
                        <button type='submit' name="actionFromForm" value="editOne" > Editer </button>
                    </form>
                    <form method="post">
                        <input type="hidden" id="idRoleFormDelete" name="idRoleFromForm"  value="${role.id}">
                        <button type='submit' name="actionFromForm" value="deleteOne" > Supprimer </button>
                    </form>

                </td>
            </tr>
        </c:forEach>
    </table>







