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


    HashMap contenant les erreurs : <c:out value="${sessionScope.myErrorsSessionKey}"/> <br/>
    HashMap contenant les attributs valides : <c:out value="${sessionScope.myValidAttributesSessionKey}"/>



    <div class="maDiv">
        <span class="ask">Veuillez compléter le formulaire d'inscription : </span>

        <!-- Create an user -->
        <form method="post" action="${pageContext.request.contextPath}/signup" >


            <!-- First Name -->
            <label for='idFirstNameForm'>Prénom :</label>
            <input id='idFirstNameForm' name='firstNameFromForm' type='text'
                   value='${sessionScope.myValidAttributesSessionKey['firstNameValid']}'  size="30" maxlength="50" required autofocus  />
            <span class="error">${sessionScope.myErrorsSessionKey['firstNameError']}</span> <br/>


            <!-- Last Name -->
            <label for='idLastNameForm'>Nom :</label>
            <input id='idLastNameForm' name='lastNameFromForm' type='text'
                   value='${sessionScope.myValidAttributesSessionKey['lastNameValid']}'  size="30" maxlength="50" required />
            <span class="error">${sessionScope.myErrorsSessionKey['lastNameError']}</span> <br/>


            <!-- Username -->
            <label for='idUsernameForm'>Username :</label>
            <input id='idUsernameForm' name='usernameFromForm' type='text'
                   value='${sessionScope.myValidAttributesSessionKey['usernameValid']}' size="30" maxlength="50" required />
            <span class="error">${sessionScope.myErrorsSessionKey['usernameError']}</span> <br/>


            <!-- Password -->
            <label for='idPasswordForm'>Password :</label>
            <input  id='idPasswordForm' name='passwordFromForm' type='password'
                    value='${sessionScope.myValidAttributesSessionKey['passwordValid']}' size="30" maxlength="50" required />
            <span class="error">${sessionScope.myErrorsSessionKey['passwordError']}</span> <br/>


            <!-- Confirmation Password -->
            <label for='idConfirmationPasswordForm'>Confirmez le Password :</label>
            <input  id='idConfirmationPasswordForm' name='confirmationPasswordFromForm' type='password'
                    value='${sessionScope.myValidAttributesSessionKey['confirmationPasswordValid']}' size="30" maxlength="50" required />
            <span class="error">${sessionScope.myErrorsSessionKey['confirmationPasswordError']}</span> <br/>


            <!-- Phone Number -->
            <label for='idPhoneNumberForm'>Numéro de téléphone : </label>
            <input id='idPhoneNumberForm' name='phoneNumberFromForm' type='text'
                   value='${sessionScope.myValidAttributesSessionKey['phoneNumberValid']}' size="30" maxlength="25" />
            <span class="error">${sessionScope.myErrorsSessionKey['phoneNumberError']}</span>
            <aqa> null</aqa>
            <br/>

            <!-- Birthdate -->
            <label for='idBirthdateForm'>Date de naissance :</label>
            <input id='idBirthdateForm' name='birthdateFromForm' type='date'
                   value='${sessionScope.myValidAttributesSessionKey['birthdateValid']}' required />
            <span class="error">${sessionScope.myErrorsSessionKey['birthdateError']}</span> <br/>


            <!-- Gender -->
            <label for='idGenderForm'>Genre : </label>
            <select id='idGenderForm' name='genderFromForm' required >
                <option value="MASCULIN">Masculin</option>
                <option value="FEMININ">Féminin</option>
                <option selected value="NEUTRE">Neutre</option>
                <option value="PERSONNALISE">Personnalisé</option>
            </select>
            <span class="error">${sessionScope.myErrorsSessionKey['genderError']}</span>
            <aqa> à vérifier la manière de proposer les entrées de l'enum</aqa>
            <br/>

            <!-- Email address -->
            <label for='idEmailAddressForm'>Adresse e-mail :</label>
            <input id='idEmailAddressForm' name='emailAddressFromForm' type='text'
                   value='${sessionScope.myValidAttributesSessionKey['emailAddressValid']}' size="30" maxlength="100" required />
            <span class="error">${sessionScope.myErrorsSessionKey['emailAddressError']}</span> <br/>


            <!-- Is active -->
            <!-- useless - niveau code
            <label for='idUsernameForm'>Active ? :</label>
            <input id='idUsernameForm' name='usernameFromForm' type='text' value=''  /> <br/>
            -->


            <!-- Inscription date -->
            <!-- useless - niveau code
            <label for='idUsernameForm'>Date d'inscription :</label>
            <input id='idUsernameForm' name='usernameFromForm' type='date' value=''  /> <br/>
            -->


            <!-- Title -->
            <label for='idTitleForm'>Title :</label>
            <input id='idTitleForm' name='titleFromForm' type='text'
                   value='${sessionScope.myValidAttributesSessionKey['titleValid']}' size="30" maxlength="50" />
            <span class="error">${sessionScope.myErrorsSessionKey['titleError']}</span>
            <aqa> null </aqa>
            <br/>

            <!-- Photo -->
            <label for='idPhotoForm'>Photo :</label>
            <input id='idPhotoForm' name='photoFromForm' type='file'
                   value='${sessionScope.myValidAttributesSessionKey['photoValid']}'  />
            <span class="error">${sessionScope.myErrorsSessionKey['photoError']}</span>
            <aqa>null</aqa>
            <br/>

            <!-- Role -->
            <label for='idRoleForm'>Role :</label>
            <input id='idRoleForm' name='roleFromForm' type='text'
                   value='${sessionScope.myValidAttributesSessionKey['roleValid']}'  />
            <span class="error">${sessionScope.myErrorsSessionKey['roleError']}</span>
            <aqa>null pour le moment mais need les lister pour en select un </aqa>
            <br/>


            <!-- Parent -->
            <label for='idParentForm'>Parent :</label>
            <input id='idParentForm' name='parentFromForm' type='text'
                   value='${sessionScope.myValidAttributesSessionKey['parentValid']}'  />
            <span class="error">${sessionScope.myErrorsSessionKey['parentError']}</span>
            <aqa>null</aqa>
            <br/>

            <br/>
            <input name='btnCreateUser' type='submit' value="Créer un nouvel utilisateur"/> <br/>
        </form>

    </div>



</body>
</html>

