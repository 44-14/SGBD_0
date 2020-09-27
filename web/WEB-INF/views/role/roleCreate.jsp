<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 10-09-20
  Time: 09:32
  To change this template use File | Settings | File Templates.
--%>

    <div class="pageInfo">
        <h2 class="pageInfo"> Formulaire de création - role  </h2>
    </div>



<div class="row">
<a id="retourListe" class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/role"> Retour liste</a>
</div>


<form method="post"  action="${pageContext.request.contextPath}/role" >
    <div class="form-row">
        <div class="col-md-6 mb-3">

            <label for="validationServer01">Label (*)</label>
            <input type="text" class="form-control is-valid" id="validationServer01" name ="labelFromForm" value="" required  minlength="5" maxlength="100">
            <div class="valid-feedback">
                Looks good!
            </div>

        </div>
        <div class="col-md-6 mb-3">

            <label for="validationServer02">Abbreviation (*)</label>
            <input type="text" class="form-control is-valid" id="validationServer02"  name ="abbreviationFromForm" placeholder="U-USER-01" value="" required minlength="6" maxlength="10">
            <div class="valid-feedback">
                Looks good!
            </div>

        </div>
    </div>


    <div class="form-row">
        <div class="col-md-12 mb-6">
            <label for="validationServer03">Description</label>
            <input type="text" class="form-control is-invalid" id="validationServer03" name ="descriptionFromForm" aria-describedby="validationServer03Feedback"  maxlength="2000" >
            <div id="validationServer03Feedback" class="invalid-feedback">
                Please provide a valid city.
            </div>
        </div>
    </div>


    <div class="form-row">
        <div id="roleList" class="col-md-12 mb-6">
            <label for="validationServer04">Permissions attribuées: (maintenir ctrl pour sélection multiple)</label>
            <select class="custom-select is-invalid" multiple id="validationServer04" name ="rolesFromForm" aria-describedby="validationServer04Feedback" required>

                <option selected disabled value="">Attribuez à un ou plusieurs rôles</option>
                <c:forEach  var="permission"    items="${sessionScope.myPermissionListForSelectInputSessionKey}" >
                    <option value="${permission.id}">  <c:out value="${permission.label}"/> </option>
                </c:forEach>
            </select>
            <div id="validationServer04Feedback" class="invalid-feedback">
                Please select a valid state.
            </div>
        </div>
    </div>


    <button class="myFormActionButton btn btn-outline-success " name="actionFromForm" value="createOne" type="submit">Valider</button>
</form>





