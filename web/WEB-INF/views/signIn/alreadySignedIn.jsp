<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 10-08-20
  Time: 06:50
  To change this template use File | Settings | File Templates.
--%>

<div class="messageInfo">
    <h2> ♦ ♦ Vous êtes déjà connecté ♦ ♦ </h2>
    <p> Username : ${sessionScope.signedUser.username}
        <br/> Role : ${sessionScope.signedUser.rolesByIdRole.label}
    </p>

    <div class ="margin-div">
        <a href="${pageContext.request.contextPath}/home" class="btn btn-sm btn-outline-success alreadySignedIn" name="" >
                Retour vers l'accueil
        </a>

        <a href="${pageContext.request.contextPath}/signout" class="btn btn-sm btn-outline-danger alreadySignedIn" name="" >
                Se déconnecter
        </a>
    </div>

    <!--
    <div class="mySmallContainer">
        <a class=" btn btn-sm btn-outline-primary"  href="${pageContext.request.contextPath}/home">
            Accéder sans plus attendre à cette magnifique application
        </a>
    </div>
    -->
</div>


