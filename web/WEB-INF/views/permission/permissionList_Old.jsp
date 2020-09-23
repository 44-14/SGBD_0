<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 20-08-20
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>



    <!-- Table contenant listant les permissions avec infos sommaires -->
    <table class="permission table  table-dark table-hover">  <!-- autre value de attribut class : table-striped -->

            <thead>
                <tr>
                    <th> ID </th>
                    <th> Label</th>
                    <th> Abbreviation</th>
                    <th>  </th>
                    <th> Action</th>
                    <th> </th>
                </tr>
            </thead>

            <tbody>
                <c:forEach items="${requestScope.myPermissionsListRequestKey}" var="permission">
                    <tr>
                        <td>${permission.id}</td>
                        <td><c:out value="${permission.abbreviation}" /></td>
                        <td><c:out value="${permission.label}" /></td>
                        <td>
                            <form  method="post"  action="${pageContext.request.contextPath}/permission" >
                                <input type="hidden" id="idPermissionFormShow" name="idPermissionFromForm"  value="${permission.id}">
                                <button type='submit' name="actionFromForm" value="readOne" > Afficher </button>
                            </form>
                        </td>
                        <td>
                            <form  method="post"  action="${pageContext.request.contextPath}/permission" >
                                <input type="hidden" id="idPermissionFormEdit" name="idPermissionFromForm"  value="${permission.id}">
                                <button type='submit' name="actionFromForm" value="editOne" > Editer </button>
                            </form>
                        </td>
                        <td>
                            <form method="post">
                                <input type="hidden" id="idPermissionFormDelete" name="idPermissionFromForm"  value="${permission.id}">
                                <button type='submit' name="actionFromForm" value="deleteOne" > Supprimer </button>
                            </form>
                        </td>
                    </tr>
                 </c:forEach>
            </tbody>
    </table>







