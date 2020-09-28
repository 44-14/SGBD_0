<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 29-08-20
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>




<div class="pageInfo">
     <h2 class="pageInfo"> Détails de l'utilisateur    &nbsp;-&nbsp; <c:out value="${requestScope.myUserRequestKey.username}"/> </h2>
</div>





<form  id ="aboveTableForm"  method="post"  action="${pageContext.request.contextPath}/user" >
    <input type="hidden" id="idUserForm" name="idUserFromForm"  value="${requestScope.myUserRequestKey.id}">
    <!-- Pour chaque button :
   retirer redAlert ou greenAlert pour eviter le clignotement
   et remplacer -danger ou -success par -primary quand tout sera implémenté -->
    <button class="myFormActionButton btn btn-sm btn-outline-danger redAlert"
                type='submit' name="actionFromForm" value="editOne_getForm" > Editer </button>
<c:if test="${sessionScope.signedUser.id != requestScope.myUserRequestKey.id}">
    <button class="myFormActionButton btn btn-sm btn-outline-danger redAlert"
                type='submit' name="actionFromForm" value="deleteOne" > Supprimer </button>
</c:if>
</form>
<a id="retourListe" class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/user"> Retour liste</a>





<!-- Table contenant les données d'une permission -->
<table class="dataList table  table-dark table-hover">  <!-- autre value de attribut class : table-striped -->
    <tbody>
        <tr>
            <td>ID :  </td>
            <td> <c:out value="${requestScope.myUserRequestKey.id}"/> </td>
        </tr>
        <tr>
            <td>Username :  </td>
            <td><c:out value="${requestScope.myUserRequestKey.username}"/></td>
        </tr>
        <tr>
            <td>Prénom : </td>
            <td> <c:out value="${requestScope.myUserRequestKey.firstName}" /></td>
        </tr>
        <tr>
            <td> Nom : </td>
            <td> <c:out value="${requestScope.myUserRequestKey.lastName}" /></td>
        </tr>
        <tr>
            <td> Password : </td>
            <td> <c:out value="${requestScope.myUserRequestKey.password}" /></td>
        </tr>

        <tr>
            <td> Date de naissance : AAAA-MM-JJ </td>
            <td> <c:out value="${requestScope.myUserRequestKey.birthdate}" /></td>
        </tr>

        <tr>
            <td> Genre : </td>
            <td> <c:out value="${requestScope.myUserRequestKey.gender}" /></td>
        </tr>

        <tr>
            <td> Adresse email : </td>
            <td> <c:out value="${requestScope.myUserRequestKey.emailAddress}" /></td>
        </tr>

        <tr>
            <td> Rôle : </td>
            <td> <c:out value="${requestScope.myUserRequestKey.rolesByIdRole.label}" /></td>
        </tr>


    </tbody>
</table>



