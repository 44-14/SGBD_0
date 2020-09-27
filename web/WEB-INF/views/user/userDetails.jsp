<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 29-08-20
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>




<div class="pageInfo">
     <h2 class="pageInfo"> Détails de l'utilisateur    &nbsp;-&nbsp; <c:out value="${requestScope.myUserRequestKey.label}"/> </h2>
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
<table class="dataList table  table-dark table-hover">  <!-- autre value de attribut class : table-striped -->
    <tbody>
        <tr>
            <td>ID :  </td>
            <td> <c:out value="${requestScope.myRoleRequestKey.id}"/> </td>
        </tr>
        <tr>
            <td>Label :  </td>
            <td><c:out value="${requestScope.myRoleRequestKey.label}"/></td>
        </tr>
        <tr>
            <td>Abbréviation : </td>
            <td> <c:out value="${requestScope.myRoleRequestKey.abbreviation}" /></td>
        </tr>
        <tr>
            <td> Description : </td>
            <td> <c:out value="${requestScope.myRoleRequestKey.description}" /></td>
        </tr>
        <tr>
            <td>Le rôle détient les permissions : </td>
            <td>
            <c:forEach  var="rolePermission"    items="${requestScope.myRoleRequestKey.rolesPermissionsById}" >
                <c:out value="${rolePermission.permissionsByIdPermission.label}"/> <br>
            </c:forEach>
            </td>
        </tr>
    </tbody>
</table>



