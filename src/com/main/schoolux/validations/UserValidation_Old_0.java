package com.main.schoolux.validations;

import com.main.schoolux.utilitaries.MyIntUtil;
import com.persistence.entities.RoleEntity;
import com.persistence.entities.UserEntity;
import com.persistence.entityFinderImplementation.EntityFinderImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;




public class UserValidation_Old_0 {

    private final static Logger LOG = Logger.getLogger(UserValidation_Old_0.class);


    /** Validations liées à la création d'un utilisateur **/
    public UserEntity UserValidation_Create(HttpServletRequest request) {

        LOG.info("=== START - createValidation() in UserValidation ===");


        Map<String, String> myValidAttributes = new HashMap<String, String>();
        Map<String, String> myErrors = new HashMap<String, String>();

        UserEntity myUser = new UserEntity();

        /*pour le service
        myUser.setActive(true);
        myUser.setInscriptionDate();
        */

        /* on aurait pu sauvegarder le nom des paramètres dans une constante pour éviter de réécrire des gros noms comme ça
            exampe : private final String FIRSTNAME_PARAM = "firstNameFromForm";
         */

        //
        // Recupération des paramètres de la request
        //

        // Amelioration : on pourrait directement passer le request.getParameter("Nomparam") en paramètre de la méthode de validation correspondante
        // exemple :  this.validationFirstName(request.getParameter("firstNameFromForm"));
        // ici on fait en 2 étapes, mais ça permet de peupler la myValidAttributes avec un nom de variable et pas un retour encore une fois

        String firstNameRequest = request.getParameter("firstNameFromForm");
        String lastNameRequest = request.getParameter("lastNameFromForm");
        String usernameRequest = request.getParameter("usernameFromForm");
        String passwordRequest = request.getParameter("passwordFromForm");
        String confirmationPasswordRequest = request.getParameter("confirmationPasswordFromForm");

        String phoneNumberRequest = request.getParameter("phoneNumberFromForm");
        String birthdateRequest = request.getParameter("birthdateFromForm");
        String genderRequest = request.getParameter("genderFromForm");
        String emailAddressRequest = request.getParameter("emailAddressFromForm");

        String titleRequest = request.getParameter("titleFromForm");
        String photoRequest = request.getParameter("photoFromForm");
        String roleRequest = request.getParameter("roleFromForm");
        String parentRequest = request.getParameter("parentFromForm");

        //LOG.info("test contenu de la requete http dans la validation " + firstNameRequest + "  " + usernameRequest);


        /////
        // APPELS AUX METHODES DE VALIDATION POUR CHAQUE PARAMETRE
        /////


        /*
        // 1ere version avec les methodes qui retournaient du void

        try {
            // on tente la validation du champ en appelant une méthode membre
            this.validationFirstName(firstNameRequest);

            // si il n y a pas eu d exception générée, on continue le bloc try
            // on stocke le resultat correct dans le HashMap myValidAttributes
            // en vue de le refournir à formulaire si une erreur survient sur un autre champ => UX
            myValidAttributes.put("firstNameValid", firstNameRequest);

            // on set l'attribut dans l object UserEntity qui servira de retour
            //myUser.setFirstName(firstNameRequest);

        } catch (Exception e){
            myErrors.put("firstNameError",e.getMessage());
        }
        */


        // 2eme version avec les méthodes de validation qui ont un retour typé pouvant directement peupler l'instance d'UserEntity

        try {
            // on tente la validation du champ en appelant une méthode membre + son retour set l'attribut dans l'instance d'UserEntity qui servira à son tour de retour à la méthode principale
            myUser.setFirstName(this.validationFirstName(firstNameRequest));

            // si il n y a pas eu d exception générée, on continue le bloc try
            // on restocke la string initiale dans le HashMap myValidAttributes
            // en vue de le refournir à formulaire si une erreur survient sur un autre champ => UX
            myValidAttributes.put("firstNameValid", firstNameRequest);
        } catch (Exception e) {
            myErrors.put("firstNameError", e.getMessage());
        }


        try {
            myUser.setLastName(this.validationLastName(lastNameRequest));
            myValidAttributes.put("lastNameValid", lastNameRequest);
        } catch (Exception e) {
            myErrors.put("lastNameError", e.getMessage());
        }


        try {
            myUser.setUsername(this.validationUsername(usernameRequest));
            myValidAttributes.put("usernameValid", usernameRequest);
        } catch (Exception e) {
            myErrors.put("usernameError", e.getMessage());
        }


        try {
            myUser.setPassword(this.validationPassword(passwordRequest, confirmationPasswordRequest));
            myValidAttributes.put("passwordValid", passwordRequest);
            myValidAttributes.put("confirmationPasswordValid", confirmationPasswordRequest);
            //remarque : confirmationPassword n'est pas un champ présent dans la table users, donc il n'est pas dans la classe UserEntity
        } catch (Exception e) {
            myErrors.put("passwordError", e.getMessage());
            myErrors.put("confirmationPasswordError", e.getMessage());
        }


        try {
            myUser.setPhoneNumber(this.validationPhoneNumber(phoneNumberRequest));
            myValidAttributes.put("phoneNumberValid", phoneNumberRequest);
        } catch (Exception e) {
            myErrors.put("phoneNumberError", e.getMessage());
        }


        try {
            myUser.setBirthdate(this.validationBirthdate(birthdateRequest));
            myValidAttributes.put("birthdateValid", birthdateRequest);
        } catch (Exception e) {
            myErrors.put("birthdateError", e.getMessage());
        }


        try {
            myUser.setGender(this.validationGender(genderRequest));
            myValidAttributes.put("genderValid", genderRequest);
        } catch (Exception e) {
            myErrors.put("genderError", e.getMessage());
        }


        try {// ici on met direct l'adresse dans la HashMap myValidAttributes parce qu'on veut pas que ça disparaisse si on revient sur le formulaire
            myValidAttributes.put("emailAddressValid", emailAddressRequest);
            myUser.setEmailAddress(this.validationEmail(emailAddressRequest));
            //myValidAttributes.put("emailAddressValid", emailAddressRequest);
        } catch (Exception e) {
            myErrors.put("emailAddressError", e.getMessage());
        }


        try {
            myUser.setTitle(this.validationTitle(titleRequest));
            myValidAttributes.put("titleValid", titleRequest);
        } catch (Exception e) {
            myErrors.put("titleError", e.getMessage());
        }


        try {
            myUser.setPhoto(this.validationPhoto(photoRequest));
            myValidAttributes.put("photoValid", photoRequest);
        } catch (Exception e) {
            myErrors.put("photoError", e.getMessage());
        }

        try {// à régler
            myUser.setRolesByIdRole(this.validationRole(roleRequest));
            myValidAttributes.put("roleValid", roleRequest);
        } catch (Exception e) {
            myErrors.put("roleError", e.getMessage());
        }

        try {// à régler
            myUser.setUsersByIdParent(this.validationParent(parentRequest, roleRequest));
            myValidAttributes.put("parentValid", parentRequest);
        } catch (Exception e) {
            myErrors.put("parentError", e.getMessage());
        }



        /* Initialisation du résultat global de la validation. */
        // if ( erreurs.isEmpty()) {
        // resultat = "Succès de l'inscription.";
        // } else {
        // resultat = "échec de l'inscription.";
        // }

        /* Stockage du résultat et des messages d'erreur dans l'objet request ou session  (choisir)*/
        // Voir notes.txt => DIFFERENCE ATTRIBUTS ET PARAMETRES DANS LA REQUETE
        //request.setAttribute("myErrorsRequestKey", myErrors);
        //request.setAttribute("myValidAttributesRequestKey", myValidAttributes);
        request.getSession().setAttribute("myErrorsSessionKey", myErrors);
        request.getSession().setAttribute("myValidAttributesSessionKey", myValidAttributes);



        /* A mettre dans le controller
        // Transmission de la paire d'objets request/response à la JSP adaptée
        if (myErrors.size() != 0) {
            request.getRequestDispatcher(VUE_FORM).forward(request, response);
        }
        else {
            this.getServletContext().getRequestDispatcher(VUE_SUCCESS).forward(request, response);
        }
        */


        // mise à null de l'objet userEntity au cas où il y aurait eu une erreur
        // on teste ensuite dans le contrôleur si ce retour est null pour savoir vers quoi dispatcher
        if (myErrors.size() != 0) {
            myUser = null;
        }

        LOG.info("=== END - createValidation() in UserValidation ===");
        return myUser;


    }// END createValidation()


    ////////
    // METHODES DE VALIDATION DES PARAMETRES DE LA REQUETE
    ////////


    private String validationFirstName(String firstName) throws Exception {
        LOG.info(" BEGIN - ValidationFirstName()");

        if (firstName == null || firstName.isEmpty()) {
            throw new Exception("Le prénom est requis");
        }

        return firstName;

    }


    private String validationLastName(String lastName) throws Exception {

        if (lastName == null || lastName.isEmpty()) {
            throw new Exception("Le nom est requis");
        }

        return lastName;

    }


    private String validationUsername(String username) throws Exception {
        // attention : ne teste pas la contrainte unique

        if (username == null || username.isEmpty()) {
            throw new Exception("Le nom d'utilisateur est requis");
        }

        return username;

    }


    private String validationPassword(String password, String confirmationPassword) throws Exception {

        if (password == null || password.isEmpty()) {
            throw new Exception("Un mot de passe est requis");
        }

        if (!password.equals(confirmationPassword)) {
            throw new Exception("Les mots de passe ne sont pas identiques ");
        }

        return password;

    }


    private String validationPhoneNumber(String phoneNumber) throws Exception {

        return phoneNumber;
    }

    private Date validationBirthdate(String birthdate) throws Exception {

        if (birthdate == null || birthdate.isEmpty()) {
            throw new Exception("Le date de naissance est requise");
        }

        Date myBirthdate = Date.valueOf(birthdate);
        return myBirthdate;


    }


    private String validationGender(String gender) throws Exception {

        if (gender == null || gender.isEmpty()) {
            throw new Exception("Le genre est requis");
        }

        return gender;

    }


    private String validationEmail(String emailAddress) throws Exception {

        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new Exception("L'adresse email est requise");
        }

        if (!emailAddress.contains("@")) {

            throw new Exception("L'adresse email doit contenir un @");
        }

        return emailAddress;
    }


    private String validationTitle(String title) throws Exception {

        return title;
    }


    private String validationPhoto(String photo) throws Exception {

        return photo;
    }


    private RoleEntity validationRole(String role) throws Exception {

        if (role == null || role.isEmpty()) {
            throw new Exception("Le rôle est requis");
        } else {
            int idRole = MyIntUtil.myTryParseInt(role,-1);
            if (idRole == -1) {
                throw new Exception("La string role ne contient pas un int parsable");
            } else {
                RoleEntity myRole = new RoleEntity();

                EntityFinderImpl<RoleEntity> efi = new EntityFinderImpl<>();

                myRole = efi.findOne(myRole, idRole);

                return myRole;


                /*
                EntityFinder<PermissionEntity> myEntityFinder = new EntityFinderImpl<PermissionEntity>();

                PermissionEntity myPermission = new PermissionEntity();

                return myEntityFinder.findOne(myPermission, id);
                */

            }


        }
    }
























    private UserEntity validationParent(String parent, String role) throws Exception {
    /*
        if (role.equals("eleve 1") && (parent == null || parent.isEmpty()) {
            throw new Exception("L'identité du parent est requise");

        }
    */
        UserEntity parentUser = new UserEntity();
        return parentUser;
    }

} // END UserValidation class

