



<!-- les attributs <name> des différentes balises représentent les noms des paramètres de la requête HTTP qui seront récupérables coté serveur -->


    <c:if test="${not empty requestScope.myErrorsRequestKey}">

        <span> HashMap contenant les erreurs :           <c:out value="${requestScope.myErrorsRequestKey}"/> </span>
        <br/>
        <span> HashMap contenant les attributs valides : <c:out value="${requestScope.myValidAttributesRequestKey}"/>

    </c:if>

<aqa></aqa>

    <div class="mySmallContainer">
        <div class="mySmallContainer">
            <span class="title1"> School UX </span>
        </div>




        <div class="mySmallContainer">
            <div class="title2"> Formulaire d'inscription </div>

            <!-- Create an user -->
            <form method="post" action="${pageContext.request.contextPath}/signup"
                  oninput='confirmationPasswordFromForm.setCustomValidity(confirmationPasswordFromForm.value != passwordFromForm.value ? "Les mots de passe ne sont pas identiques." : "")'>


                <!-- First Name -->
                <label for='idFirstNameForm'>Prénom (*) :</label>
                <input id='idFirstNameForm' name='firstNameFromForm' type='text'
                       value='${requestScope.myValidAttributesRequestKey['firstNameFromFormValid']}'  size="30" minlength="2" maxlength="50" required autofocus  />
                <span class="error">${requestScope.myErrorsRequestKey['firstNameFromFormError']}</span> <br/>


                <!-- Last Name -->
                <label for='idLastNameForm'>Nom (*) :</label>
                <input id='idLastNameForm' name='lastNameFromForm' type='text'
                       value='${requestScope.myValidAttributesRequestKey['lastNameFromFormValid']}'  size="30" minlength="2" maxlength="50" required />
                <span class="error">${requestScope.myErrorsRequestKey['lastNameFromFormError']}</span> <br/>


                <!-- Username -->
                <label for='idUsernameForm'>Username (*) :</label>
                <input id='idUsernameForm' name='usernameFromForm' type='text'
                       value='${requestScope.myValidAttributesRequestKey['usernameFromFormValid']}' size="30" minlength="5" maxlength="50" required />
                <span class="error">${requestScope.myErrorsRequestKey['usernameFromFormError']}</span> <br/>


                <!-- Password -->
                <label for='idPasswordForm'>Password (*) :</label>
                <input  id='idPasswordForm' name='passwordFromForm' type='password'
                        value='${requestScope.myValidAttributesRequestKey['passwordFromFormValid']}' size="30"  minlength="3" maxlength="50" required />
                <span class="error">${requestScope.myErrorsRequestKey['passwordFromFormError']}</span> <br/>


                <!-- Confirmation Password -->
                <label for='idConfirmationPasswordForm'>Confirmez le Password (*) :</label>
                <input  id='idConfirmationPasswordForm' name='confirmationPasswordFromForm' type='password'
                        value='${requestScope.myValidAttributesRequestKey['confirmationPasswordFromFormValid']}' size="30" minlength="3" maxlength="50" required />
                <span class="error">${requestScope.myErrorsRequestKey['confirmationPasswordFromFormError']}</span> <br/>



                <!-- Birthdate -->
                <label for='idBirthdateForm'>Date de naissance (*) :</label>
                <input id='idBirthdateForm' name='birthdateFromForm' type='date'
                       value='${requestScope.myValidAttributesRequestKey['birthdateFromFormValid']}' required  />
                <span class="error">${requestScope.myErrorsRequestKey['birthdateFromFormError']}</span> <br/>




                <!-- Gender -->

                <c:if test="${not empty sessionScope.genderValues}">
                <label for='idGenderForm'>Genre (*) : </label>
                <select id='idGenderForm' name='genderFromForm' required >

                        <c:forEach var="gender" items="${sessionScope.genderValues}" >
                            <option value="${gender}"> ${gender} </option>
                        </c:forEach>
                </select>
                <span class="error">${requestScope.myErrorsRequestKey['genderFromFormError']}</span>
                <br/>
                </c:if>





                <!-- Email address -->
                <label for='idEmailAddressForm'>Adresse e-mail (*) :</label>
                <input id='idEmailAddressForm' name='emailAddressFromForm' type='email'
                       value='${requestScope.myValidAttributesRequestKey['emailAddressFromFormValid']}' size="30" minlength="5" maxlength="100" required />
                <span class="error">${requestScope.myErrorsRequestKey['emailAddressFromFormError']}</span> <br/>


                <!-- Is active -->
                <!-- useless - niveau code
                <label for='idUsernameForm'>Active ? :</label>
                <input id='idUsernameForm' name='usernameFromForm' type='text' value=''  /> <br/>
                -->


                <%--
                <!-- Role => Pas ici : Eleve par defaut-->
                <!--
                <label for='idRoleForm'>Role (*) :</label>
                <input id='idRoleForm' name='roleFromForm' type='text'
                       value='${RequestScope.myValidAttributesRequestKey['roleFromFormValid']}'  />
                <span class="error">${RequestScope.myErrorsRequestKey['roleFromFormError']}</span>
                <aqa>null pour le moment mais need les lister pour en select un </aqa>
                <br/>
                -->
                --%>


                <button id="signInButton" class="btn btn-sm btn-outline-success" value="CreateOne" name="ActionForm" type="submit">Valider</button>

            </form>


        </div>
    </div>


<div class="mySmallContainer">

        <a href="${pageContext.request.contextPath}/signin" id="signInButton" class="btn btn-sm btn-outline-primary" >
                Retour à la page de connexion
        </a>
    </div>


