<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 1-09-20
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>



<div class="pageInfo">
    <h2 class="pageInfo"> Edition de l'utilisateur    &nbsp;-&nbsp; <c:out value="${requestScope.myUserVMRequestKey.username}"/> </h2>
</div>




    <div class="row">
        <a id="retourListe" class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/user"> Retour liste</a>
    </div>


    <form method="post"  action="${pageContext.request.contextPath}/user" >
        <input type="hidden" id="idRoleForm" name="idUserFromForm"  value="${requestScope.myUserVMRequestKey.id}">

        <div class="form-row">
            <div class="col-md-6 mb-3">

                <label for="validationServer01">Username (*)</label>
                <input type="text" class="form-control" id="validationServer01" name ="usernameFromForm" value="${requestScope.myUserVMRequestKey.username}" required  minlength="5" maxlength="50">
                <div class="valid-feedback">

                </div>

            </div>
            <div class="col-md-6 mb-3">

                <label for="validationServer02">Password (*)</label>
                <input type="text" class="form-control" id="validationServer02"  name ="passwordFromForm" value="${requestScope.myUserVMRequestKey.password}"  required minlength="3" maxlength="50">
                <div class="valid-feedback">

                </div>

            </div>
        </div>


        <div class="form-row">
            <div class="col-md-12 mb-6">
                <label for="validationServer03">Role actuel </label>
                <input disabled type="text" class="form-control " id="validationServer03"  value="${requestScope.myUserVMRequestKey.rolesByIdRole.label}" aria-describedby="validationServer03Feedback"  maxlength="2000" >
                <div id="validationServer03Feedback" class="invalid-feedback">

                </div>
            </div>
        </div>

        <%-- on affiche la sélection de permissions que si on en a récupérées dans le contexte lors du myPermissionService.selectAll dans la roleServlet --%>
        <c:if test="${requestScope.myRoleListForSelectInputRequestKey.size() > 0}">
        <div class="form-row">
            <div id="roleList" class="col-md-12 mb-6">
                <label for="validationServer04">Attribuez un rôle : (Resélectionnez l'ancien si il ne change pas) </label>

                <select class="custom-select is-invalid" multiple id="validationServer04" name ="roleFromForm" aria-describedby="validationServer04Feedback" required>

                    <option selected disabled value="">Attribuez un rôle </option>

                         <c:forEach  var="role"    items="${requestScope.myRoleListForSelectInputRequestKey}">
                             <option
                                     <c:if test="${requestScope.myUserVMRequestKey.rolesByIdRole.id == role.id}">
                                         aria-checked="true"
                                    </c:if>
                              value="${role.id}"> <c:out value="${role.label}"/>
                             </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        </c:if>


        <button class="myFormActionButton btn btn-outline-success " name="actionFromForm" value="editOne" type="submit">Valider</button>
    </form>











