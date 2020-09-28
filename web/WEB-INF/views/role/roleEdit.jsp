<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 1-09-20
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>



<div class="pageInfo">
    <h2 class="pageInfo"> Edition du rôle    &nbsp;-&nbsp; <c:out value="${requestScope.myRoleVMRequestKey.label}"/> </h2>
</div>




    <div class="row">
        <a id="retourListe" class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/role"> Retour liste</a>
    </div>


    <form method="post"  action="${pageContext.request.contextPath}/role" >
        <input type="hidden" id="idRoleForm" name="idRoleFromForm"  value="${requestScope.myRoleVMRequestKey.id}">

        <div class="form-row">
            <div class="col-md-6 mb-3">

                <label for="validationServer01">Label (*)</label>
                <input type="text" class="form-control " id="validationServer01" name ="labelFromForm" value="${requestScope.myRoleVMRequestKey.label}" required  minlength="5" maxlength="100">
                <div class="valid-feedback">

                </div>

            </div>
            <div class="col-md-6 mb-3">

                <label for="validationServer02">Abbreviation (*)</label>
                <input type="text" class="form-control " id="validationServer02"  name ="abbreviationFromForm" value="${requestScope.myRoleVMRequestKey.abbreviation}"  required minlength="6" maxlength="10">
                <div class="valid-feedback">

                </div>

            </div>
        </div>


        <div class="form-row">
            <div class="col-md-12 mb-6">
                <label for="validationServer03">Description</label>
                <input type="text" class="form-control " id="validationServer03" name ="descriptionFromForm" value="${requestScope.myRoleVMRequestKey.description}" aria-describedby="validationServer03Feedback"  maxlength="2000" >
                <div id="validationServer03Feedback" class="invalid-feedback">

                </div>
            </div>
        </div>

        <%-- on affiche la sélection de permissions que si on en a récupérées dans le contexte lors du myPermissionService.selectAll dans la roleServlet --%>
        <c:if test="${sessionScope.myPermissionListForSelectInputSessionKey.size() > 0}">
        <div class="form-row">
            <div id="roleList" class="col-md-12 mb-6">
                <label for="validationServer04">Permissions attribuées: (maintenir ctrl pour sélection multiple)</label>

                <select class="custom-select is-invalid" multiple id="validationServer04" name ="permissionsFromForm" aria-describedby="validationServer04Feedback" required>

                    <option selected disabled value="">Attribuez à un ou plusieurs rôles</option>

                    <c:forEach  var="permission"    items="${sessionScope.myPermissionListForSelectInputSessionKey}" >
                        <option

                         <c:forEach  var="rolePermission"    items="${requestScope.myRoleVMRequestKey.rolesPermissionsById}">
                             <c:if test="${permission.id == rolePermission.permissionsByIdPermission.id}">
                                 aria-checked="true"
                             </c:if>
                         </c:forEach>

                                value="${permission.id}"> <c:out value="${permission.label}"/> </option>
                    </c:forEach>
                </select>
                <div id="validationServer04Feedback" class="invalid-feedback">

                </div>
            </div>
        </div>
        </c:if>


        <button class="myFormActionButton btn btn-outline-success " name="actionFromForm" value="editOne" type="submit">Valider</button>
    </form>











