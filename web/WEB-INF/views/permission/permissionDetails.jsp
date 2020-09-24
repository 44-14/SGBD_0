<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 29-08-20
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>




    <div class="pageInfo">
        <h2 class="pageInfo"> Détails de la permission    &nbsp;-&nbsp; <c:out value="${requestScope.myPermissionRequestKey.label}"/> </h2>
    </div>


    <form  id ="aboveTableForm"  method="post"  action="${pageContext.request.contextPath}/permission" >
        <input type="hidden" id="idPermissionForm" name="idPermissionFromForm"  value="${requestScope.myPermissionRequestKey.id}">
        <!-- Pour chaque button :
       retirer redAlert ou greenAlert pour eviter le clignotement
       et remplacer -danger ou -success par -primary quand tout sera implémenté -->
        <button class="myFormActionButton btn btn-sm btn-outline-danger redAlert"
                    type='submit' name="actionFromForm" value="editOne_getForm" > Editer </button>

        <button class="myFormActionButton btn btn-sm btn-outline-danger redAlert"
                    type='submit' name="actionFromForm" value="deleteOne" > Supprimer </button>
    </form>

    <a id="retourListe" class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/permission"> Retour liste</a>




    <!-- Table contenant les données d'une permission -->
    <table class="permission table  table-dark table-hover">  <!-- autre value de attribut class : table-striped -->

        <tbody>

            <tr>
                <td>ID :  </td>
                <td> <c:out value="${requestScope.myPermissionRequestKey.id}"/> </td>
            </tr>
            <tr>
                <td>Label :  </td>
                <td><c:out value="${requestScope.myPermissionRequestKey.label}"/></td>
            </tr>
            <tr>
                <td>Code : </td>
                <td> <c:out value="${requestScope.myPermissionRequestKey.abbreviation}" /></td>
            </tr>
            <tr>
                <td> Description : </td>
                <td> <c:out value="${requestScope.myPermissionRequestKey.description}" /></td>
            </tr>

            <tr>
                <td>Permission attribuée aux roles : </td>
                <td>
                <c:forEach  var="rolePermission"    items="${requestScope.myPermissionRequestKey.rolesPermissionsById}" >
                    <c:out value="${rolePermission.rolesByIdRole.label}"/> <br>
                </c:forEach>
                </td>
            </tr>

        </tbody>
    </table>



