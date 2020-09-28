<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 1-09-20
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>



<div class="pageInfo">
    <h2 class="pageInfo"> Edition de la permission    &nbsp;-&nbsp; <c:out value="${requestScope.myPermissionVMRequestKey.label}"/> </h2>
</div>




    <div class="row">
        <a id="retourListe" class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/permission"> Retour liste</a>
    </div>


    <form method="post"  action="${pageContext.request.contextPath}/role" >
        <input type="hidden" id="idPermissionForm" name="idPermissionFromForm"  value="${requestScope.myPermissionVMRequestKey.id}">

        <div class="form-row">
            <div class="col-md-6 mb-3">

                <label for="validationServer01">Label (*)</label>
                <input type="text" class="form-control is-valid" id="validationServer01" name ="labelFromForm" value="${requestScope.myPermissionVMRequestKey.label}" required  minlength="5" maxlength="100">
                <div class="valid-feedback">
                    Looks good!
                </div>

            </div>
            <div class="col-md-6 mb-3">

                <label for="validationServer02">Abbreviation (*)</label>
                <input type="text" class="form-control is-valid" id="validationServer02"  name ="abbreviationFromForm" value="${requestScope.myPermissionVMRequestKey.abbreviation}"  required minlength="6" maxlength="10">
                <div class="valid-feedback">
                    Looks good!
                </div>

            </div>
        </div>


        <div class="form-row">
            <div class="col-md-12 mb-6">
                <label for="validationServer03">Description</label>
                <input type="text" class="form-control is-invalid" id="validationServer03" name ="descriptionFromForm" value="${requestScope.myPermissionVMRequestKey.description}" aria-describedby="validationServer03Feedback"  maxlength="2000" >
                <div id="validationServer03Feedback" class="invalid-feedback">
                    Please provide a valid city.
                </div>
            </div>
        </div>

        <%-- on affiche la sélection de rôles que si on en a récupérées dans le contexte lors du myRoleService.selectAll dans la permissionServlet --%>
        <c:if test="${sessionScope.myRoleListForSelectInputSessionKey.size() > 0}">
        <div class="form-row">
            <div id="roleList" class="col-md-12 mb-6">
                <label for="validationServer04">Rôles attribuées: (maintenir ctrl pour sélection multiple)</label>

                <select class="custom-select is-invalid" multiple id="validationServer04" name ="rolesFromForm" aria-describedby="validationServer04Feedback" required>

                    <option selected disabled value="">Attribuez à un ou plusieurs rôles</option>

                    <c:forEach  var="role"    items="${sessionScope.myRoleListForSelectInputSessionKey}" >
                        <option

                         <c:forEach  var="rolePermission"    items="${requestScope.myPermissionVMRequestKey.rolesPermissionsById}">
                             <c:if test="${role.id == rolePermission.rolesByIdRole.id}">
                                aria-checked="true"
                             </c:if>
                         </c:forEach>

                                value="${role.id}"> <c:out value="${role.label}"/> </option>
                    </c:forEach>
                </select>
                <div id="validationServer04Feedback" class="invalid-feedback">
                    Please select a valid state.
                </div>
            </div>
        </div>
        </c:if>


        <button class="myFormActionButton btn btn-outline-success " name="actionFromForm" value="editOne" type="submit">Valider</button>
    </form>











