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
    <h2 class="pageInfo"> Liste des permissions </h2>
    <a id="retourListe" class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/permission/createOne_getForm"> Créer une nouvelle permission</a>
</div>



<c:choose>
    <c:when test="${not empty sessionScope.redirectErrorMessage}">
        <div class="errorMessage">
            <p class="redAlert>"> <c:out value="${sessionScope.redirectErrorMessage}"/> </p>
        </div>
    </c:when>
    <c:when test="${not empty sessionScope.redirectSuccessMessage}">
        <div class="successMessage">
        <p class="greenAlert"> <c:out value="${sessionScope.redirectSuccessMessage}"/> </p>
        </div>
    </c:when>
</c:choose>







        <!-- Table contenant listant les permissions avec infos sommaires -->
        <table class="dataList table  table-dark table-hover">  <!-- autre value de attribut class : table-striped -->

                <thead>
                    <tr>
                        <th> ID </th>
                        <th> Code</th>
                        <th> Label</th>
                        <th> Action</th>
                    </tr>
                </thead>

                <tbody>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <c:forEach items="${requestScope.myPermissionListRequestKey}" var="permission">
                        <tr>
                            <td><c:out value="${permission.id}" /></td>
                            <td><c:out value="${permission.abbreviation}" /></td>
                            <td><c:out value="${permission.label}" /></td>

                            <td>
                                <form method="post"  action="${pageContext.request.contextPath}/permission" >
                                    <input type="hidden" id="idPermissionForm" name="idPermissionFromForm"  value="${permission.id}">
                                    <!-- Pour chaque button :retirer redAlert ou greenAlert pour eviter le clignotement
                                    et remplacer -danger ou -success par -primary quand tout sera implémenté -->
                                    <button class="myFormActionButton btn btn-sm btn-outline-success greenAlert"
                                            type='submit' name="actionFromForm" value="readOne" > Afficher </button>
                                    <button class="myFormActionButton btn btn-sm btn-outline-danger redAlert"
                                            type='submit' name="actionFromForm" value="editOne" > Editer </button>
                                    <button class="myFormActionButton btn btn-sm btn-outline-danger redAlert"
                                            type='submit' name="actionFromForm" value="deleteOne" > Supprimer </button>
                                </form>
                            </td>

                        </tr>
                     </c:forEach>
                </tbody>
        </table>








