<!-- XL

<nav class="navbar navbar-expand-lg navbar-light bg-light">
<a class="navbar-brand" href="${pageContext.request.contextPath}/users">Horaires</a>
<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
<span class="navbar-toggler-icon"></span>
</button>

<div class="collapse navbar-collapse" id="navbarSupportedContent">
<ul class="navbar-nav mr-auto">

<li class="nav-item dropdown">
<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownWorkers" role="button"
data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
Workers
</a>
<div class="dropdown-menu" aria-labelledby="navbarDropdown">
<a class="dropdown-item" href="${pageContext.request.contextPath}/workers/list">list</a>
<a class="dropdown-item" href="${pageContext.request.contextPath}/workers/create">create</a>
</div>
</li>
<li class="nav-item dropdown">
<a class="nav-link dropdown-toggle" href="${pageContext.request.contextPath}/roles/list"
id="navbarDropdownRoles" role="button" data-toggle="dropdown" aria-haspopup="true"
aria-expanded="false">
Roles
</a>
<div class="dropdown-menu" aria-labelledby="navbarDropdown">
<a class="dropdown-item" href="${pageContext.request.contextPath}/roles/list">list</a>
</div>
</li>
<li class="nav-item">
<a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
</li>
<li class="nav-item">
<a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
</li>
</ul>

</div>
</nav>


-->

<!-- <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top"> -->
<nav class="navbar navbar-expand-lg navbar-dark  fixed-top">
    <a id="myNavbar-brand" class="navbar-brand" href="${pageContext.request.contextPath}/home">School UX</a>
    <button class="navbar-toggler " type="button" data-toggle="collapse" data-target="#navbarNavDropdown"
            aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <div>
            <ul class="navbar-nav">

                <li class="nav-item active">
                    <a class="nav-link btn-outline-dark" href="${pageContext.request.contextPath}/home">Accueil <span class="sr-only">(current)</span></a>
                </li>

                <li class="nav-item">
                    <a  class="nav-link btn-outline-dark" href="#">Lorem</a>
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle btn-outline-dark" href="#" id="navbarDropdownMenuLink" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Drop
                    </a>

                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="#">Action</a>
                        <a class="dropdown-item" href="#">Another action</a>
                        <a class="dropdown-item" href="#">Something else here</a>
                    </div>
                </li>
            </ul>
        </div>



        <div class="ml-auto"> <!-- ml-auto permet d'envoyer le contenu tout à gauche de la navbar jsppq -->
            <ul class="navbar-nav">


                <li class=" nav-item dropdown ">
                    <a  class="nav-link dropdown-toggle btn-outline-dark" href="#" id="navbarDropdownMenu_Gestion" role="button"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Gestion
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenu_Gestion">
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/user">Utilisateurs</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/role">Roles</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/permission">Permissions</a>
                    </div>
                </li>


                <li class="nav-item ">
                    <a class="nav-link btn-outline-dark" href="${pageContext.request.contextPath}/account">
                        Mon compte
                    </a>
                </li>

                <li class="nav-item">
                    <a  class="nav-link btn-outline-dark" href="${pageContext.request.contextPath}/signout">
                        Sign out
                    </a>
                </li>

            </ul>
        </div>
    </div>
</nav>




<!--
<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle btn-outline-dark" href="#" id="navbarDropdownMenuLink" role="button"
       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        Drop
    </a>

    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
        <a class="dropdown-item" href="#">Action</a>
        <a class="dropdown-item" href="#">Another action</a>
        <a class="dropdown-item" href="#">Something else here</a>
    </div>
</li>
-->