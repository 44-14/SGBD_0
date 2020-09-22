<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 10-08-20
  Time: 06:50
  To change this template use File | Settings | File Templates.
--%>

<div class="messageInfo">
    <h2> ♦ ♦ Vous avez été redirigé ici car l'URL ne matchait aucun URL-pattern traité ♦ ♦ </h2>
    <p> ${sessionScope.signedUser.firstName} ${sessionScope.signedUser.lastName} connecté en tant que ${sessionScope.signedUser.username}
        <br/> Role : ${sessionScope.signedUser.rolesByIdRole.label}
    </p>
</div>



