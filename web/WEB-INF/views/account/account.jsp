<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 20-08-20
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>



<!-- Table contenant les données de l'user connecté -->






<table class="dataList table  table-dark table-hover">



    <tr>
        <td> ID: </td>
        <td> ${sessionScope.signedUser.id} </td>
    </tr>
    <tr> <td> Username: </td>
        <td> ${sessionScope.signedUser.username} </td>
    </tr>


    <tr> <td> Prénom : </td>
        <td> ${sessionScope.signedUser.firstName} </td>
    </tr>

    <tr> <td> Nom : </td>
        <td>${sessionScope.signedUser.lastName}     </td>
    </tr>
    <tr> <td> Rôle </td>
        <td>${sessionScope.signedUser.rolesByIdRole.label} </td>
    </tr>


    <%--
    <td>${requestScope.myPermissionRequestKey.id}</td>
    <td><c:out value="${requestScope.myPermissionRequestKey.abbreviation}" /></td>
    <td><c:out value="${requestScope.myPermissionRequestKey.label}" /></td>
    <td><c:out value="${requestScope.myPermissionRequestKey.description}" /></td>

    <c:forEach items="${requestScope.myPermissionRequestKey.rolesPermissionsById}" var="role">
        <td><c:out value="${role.rolesByIdRole.label}"/> </td>
    </c:forEach>


    </tr>


</table>
































<p class="TODO <your name> PRINT OBJECT PROPERTIES">
    <c:set var="object" value="${sessionScope.signedUser}" />
<h2><b>Object:&nbsp; ${object['class']} </b></h2>
<h3><b>Declared fields</b></h3>
<c:if test="${!empty object['class'].declaredFields}">
    <ul>
        <c:forEach var="attr" items="${object['class'].declaredFields}">
            <c:catch><li><b>${attr.name}</b>:&nbsp; ${object[attr.name]}</li></c:catch>
        </c:forEach>
    </ul>
</c:if>
<c:if test="${empty object['class'].declaredFields}">No declared fields</c:if>
<h3><b>Declared methods</b></h3>
<c:if test="${!empty object['class'].declaredMethods}">
    <ul>
        <c:forEach var="attr" items="${object['class'].declaredMethods}">
            <c:catch><li><b>${attr.name}</b>(...)</li></c:catch>
        </c:forEach>
    </ul>
</c:if>
<c:if test="${empty object['class'].declaredMethods}">No declared methods</c:if>
</p>







<!--Liste tous les champs déclarés et leurs valeurs d'un attribut particulier mis dans un scope -->
<c:set var="object" value="${sessionScope.signedUser}" />
<c:if test="${not empty object['class'].declaredFields}">
    <h2>Champs déclarés de <em>${object.username}</em></h2>
    <ul>
        <c:forEach var="field" items="${object['class'].declaredFields}">
            <c:catch><li><span style="font-weight: bold">
                ${field.name}: </span>${object[field.name]}</li>
            </c:catch>
        </c:forEach>
    </ul>
</c:if>








































<div class="container emp-profile">
    <form method="post">
        <div class="row">
            <div class="col-md-4">
                <div class="profile-img">
                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS52y5aInsxSm31CvHOFHWujqUx_wWTS9iM6s7BAm21oEN_RiGoog" alt=""/>
                    <div class="file btn btn-lg btn-primary">
                        Change Photo
                        <input type="file" name="file"/>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="profile-head">
                    <h5>
                        Kshiti Ghelani
                    </h5>
                    <h6>
                        Web Developer and Designer
                    </h6>
                    <p class="proile-rating">RANKINGS : <span>8/10</span></p>
                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">About</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Timeline</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="col-md-2">
                <input type="submit" class="profile-edit-btn" name="btnAddMore" value="Edit Profile"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="profile-work">
                    <p>WORK LINK</p>
                    <a href="">Website Link</a><br/>
                    <a href="">Bootsnipp Profile</a><br/>
                    <a href="">Bootply Profile</a>
                    <p>SKILLS</p>
                    <a href="">Web Designer</a><br/>
                    <a href="">Web Developer</a><br/>
                    <a href="">WordPress</a><br/>
                    <a href="">WooCommerce</a><br/>
                    <a href="">PHP, .Net</a><br/>
                </div>
            </div>
            <div class="col-md-8">
                <div class="tab-content profile-tab" id="myTabContent">
                    <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                        <div class="row">
                            <div class="col-md-6">
                                <label>User Id</label>
                            </div>
                            <div class="col-md-6">
                                <p>Kshiti123</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Name</label>
                            </div>
                            <div class="col-md-6">
                                <p>Kshiti Ghelani</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Email</label>
                            </div>
                            <div class="col-md-6">
                                <p>kshitighelani@gmail.com</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Phone</label>
                            </div>
                            <div class="col-md-6">
                                <p>123 456 7890</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Profession</label>
                            </div>
                            <div class="col-md-6">
                                <p>Web Developer and Designer</p>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                        <div class="row">
                            <div class="col-md-6">
                                <label>Experience</label>
                            </div>
                            <div class="col-md-6">
                                <p>Expert</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Hourly Rate</label>
                            </div>
                            <div class="col-md-6">
                                <p>10$/hr</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Total Projects</label>
                            </div>
                            <div class="col-md-6">
                                <p>230</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>English Level</label>
                            </div>
                            <div class="col-md-6">
                                <p>Expert</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Availability</label>
                            </div>
                            <div class="col-md-6">
                                <p>6 months</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <label>Your Bio</label><br/>
                                <p>Your detail description</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>




--%>