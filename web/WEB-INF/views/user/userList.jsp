<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 20-08-20
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<!-- Source : https://stackoverflow.com/questions/5967564/form-inside-a-table

-->

<div class="pageInfo">
    <h2 class="pageInfo"> Liste des utlisateurs </h2>
    <%-- Pas eu le temps de faire createUser via userManagerServlet
    <div>
    <a id="retourListe" class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/user/createOne_getForm"> Créer un nouvel utilisateur </a>
    </div>
    --%>
</div>






<!-- Table listant les roles avec infos sommaires -->
<table class="dataList table  table-dark table-hover">  <!-- autre value de attribut class : table-striped -->

    <thead>
    <tr>
        <th> ID </th>
        <th> Username</th>
        <th> Prénom</th>
        <th> Nom</th>
        <th> Rôle</th>
        <th> Action</th>
    </tr>
    </thead>

    <tbody>
    <tr>
        <td colspan="6"> </td>

    </tr>
    <c:forEach  var="user"  items="${requestScope.myUserListRequestKey}">
        <tr>
            <td><c:out value="${user.id}" /></td>
            <td><c:out value="${user.username}" /></td>
            <td><c:out value="${user.firstName}" /></td>
            <td><c:out value="${user.lastName}" /></td>
            <td><c:out value="${user.rolesByIdRole.label}" /></td>

            <td>
                <form method="post"  action="${pageContext.request.contextPath}/user" >
                    <input type="hidden" id="idUserForm" name="idUserFromForm"  value="${user.id}">
                    <!-- Pour chaque button :retirer redAlert ou greenAlert pour eviter le clignotement
                    et remplacer -danger ou -success par -primary quand tout sera implémenté -->
                    <button class="myFormActionButton btn btn-sm btn-outline-success greenAlert"
                            type='submit' name="actionFromForm" value="readOne" > Afficher </button>
                    <button class="myFormActionButton btn btn-sm btn-outline-danger redAlert"
                            type='submit' name="actionFromForm" value="editOne_getForm" > Editer </button>

                    <!-- Pour retirer la possibilité de suppression de l utilisateur correspondant à  l'utilisateur actuellement connecté -->
                    <c:if test="${sessionScope.signedUser.id != user.id}">
                    <button class="myFormActionButton btn btn-sm btn-outline-danger redAlert"
                            type='submit' name="actionFromForm" value="deleteOne" > Supprimer </button>
                    </c:if>

                </form>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>



