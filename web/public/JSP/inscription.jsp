<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 16-08-20
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Création utilisateur</title>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/assets/css/signIn.css' />
</head>
<body>

<!-- les attributs <name> des différentes balises représentent les noms des paramètres de la requête HTTP qui seront récupérables coté serveur -->


    <c:out value="${sessionScope.error}"/>
    <c:out value="${sessionScope.ok}"/>





    <div class="maDiv">
        <span class="ask">Veuillez compléter le formulaire : </span>

        <!-- Create an user -->
        <form method="post" action="${pageContext.request.contextPath}/createuser" enctype="multipart/form-data">

            <!-- First Name -->
            <label for='idFirstNameForm'>Prénom :</label>
            <input id='isFirstNameForm' name='firstNameFromForm' type='text' value='${sessionScope.firstNameSessionKey}' required autofocus /> <br/>

            <!-- Last Name -->
            <label for='idLastNameForm'>Nom :</label>
            <input id='idLastNameForm' name='lastNameFromForm' type='text' value='${sessionScope.lastNameSessionKey}' required /> <br/>

            <!-- Username -->
            <label for='idUsernameForm'>Username :</label>
            <input id='idUsernameForm' name='usernameFromForm' type='text' value='${sessionScope.usernameSessionKey}' required /> <br/>

            <!-- Password -->
            <label for='idPasswordForm'>Password :</label>
            <input  id='idPasswordForm' name='passwordFromForm' type='password' value='${sessionScope.passwordSessionKey}' required /> <br/>

            <!-- Phone Number -->
            <label for='idPhoneNumberForm'>Numéro de téléphone : </label>
            <input id='idPhoneNumberForm' name='phoneNumberFromForm' type='text' value='${sessionScope.phoneNumberSessionKey}' /> <br/>
            <p> Null</p>

            <!-- Birthdate -->
            <label for='idBirthdateForm'>Date de naissance :</label>
            <input id='idBirthdateForm' name='birthdateFromForm' type='date' value='${sessionScope.birthdateSessionKey}' required /> <br/>

            <!-- Gender -->
            <label for='idGenderForm'>Genre : </label>
            <select id='idGenderForm' name='genderFromForm' value='${sessionScope.genderSessionKey}' required >
                <option value="MASCULIN">Masculin</option>
                <option value="FEMININ">Féminin</option>
                <option value="NEUTRE">Neutre</option>
                <option value="PERSONNALISE">Personnalisé</option>
            </select>
            <br/>
            <p> à vérifier la manière de proposer les entrées de l'enum</p>


            <!-- Email address -->
            <label for='idEmailAddressForm'>Adresse e-mail :</label>
            <input id='idEmailAddressForm' name='emailAddressFromForm' type='text' value='${sessionScope.addressEmailSessionKey}' required /> <br/>

            <!-- Is active -->
            <!-- useless - niveau code
            <label for='idUsernameForm'>Active ? :</label>
            <input id='idUsernameForm' name='usernameFromForm' type='text' value='${sessionScope.usernameSessionKey}' autofocus /> <br/>
            -->

            <!-- Inscription date -->
            <!-- useless - niveau code
            <label for='idUsernameForm'>Date d'inscription :</label>
            <input id='idUsernameForm' name='usernameFromForm' type='date' value='${sessionScope.usernameSessionKey}' autofocus /> <br/>
            -->

            <!-- Title -->
            <label for='idTitleForm'>Title :</label>
            <input id='idTitleForm' name='titleFromForm' type='text' value='${sessionScope.titleSessionKey}'  /> <br/>
            <p> null </p>

            <!-- Photo -->
            <label for='idPhotoForm'>Photo :</label>
            <input id='idPhotoForm' name='photoFromForm' type='file' value='${sessionScope.photoSessionKey}'  /> <br/>
            <p>null</p>

            <!-- Role -->
            <label for='idRoleForm'>Role :</label>
            <input id='idRoleForm' name='usernameFromForm' type='text' value='${sessionScope.roleSessionKey}'  /> <br/>
            <p>null pour le moment mais need les lister pour en select un</p>

            <!-- Parent -->
            <label for='idParentForm'>Parent :</label>
            <input id='idParentForm' name='parentFromForm' type='text' value='${sessionScope.parentSessionKey}'  /> <br/>
            <p>null</p>


            <br/>
            <input name='btnCreateUser' type='submit' value="Créer un nouvel utilisateur"/> <br/>
        </form>

    </div>



</body>
</html>

