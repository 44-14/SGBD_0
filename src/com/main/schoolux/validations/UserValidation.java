package com.main.schoolux.validations;

import com.main.schoolux.enumerations.Gender;
import com.main.schoolux.services.RoleService;
import com.main.schoolux.services.UserService;
import com.main.schoolux.utilitaries.MyLogUtil;
import com.main.schoolux.utilitaries.MyStringUtil;
import com.main.schoolux.viewModels.RoleVM;
import com.main.schoolux.viewModels.UserVM;
import com.persistence.entities.RoleEntity;
import com.persistence.entities.UserEntity;
import com.persistence.entityFinderImplementation.EMF;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


/* Amelioration :
faire une methode generale de validation
passer en paramètre String la raison de la validation (tosignin, tosignup, toedit , etc) + la requete
faire un switch sur la raison
invoquer une methode liée à cette raison
cela permettrait d avoir le code factorisé par rapport au check des erreurs.size >0
et des setAttribute en request ou session etc
refaire un switch sur la raison lors des dispatch si on a get des erreurs etc
 */

public class UserValidation {

    private final static Logger LOG = Logger.getLogger(UserValidation.class);


    /**
     * buils up a user entity in order to check the authentication.
     * @param request
     * @return
     */
    public static UserEntity toSignIn(HttpServletRequest request) {

        //MyLogUtil.enterStaticMethod(new Exception());

        Map<String, String> myValidAttributes = new HashMap<String, String>();
        Map<String, String> myErrors = new HashMap<String, String>();

        UserEntity myUser = new UserEntity();


        //////////
        /// APPELS AUX METHODES DE VALIDATION POUR CHAQUE REQUEST PARAMETER
        //////////


        CommonValidation.checkEmptyAndLength_Input(
                request.getParameter("usernameFromForm"),
                "usernameFromForm",
                5,
                50,
                myErrors,
                myValidAttributes
        );


        CommonValidation.checkEmptyAndLength_Input(
                request.getParameter("passwordFromForm"),
                "passwordFromForm",
                3,
                50,
                myErrors,
                myValidAttributes
        );


        if (myErrors.size() != 0) {
            LOG.debug("Errors : " + myErrors.size());
            // Stockage des inputs valides et des messages d'erreur dans l'objet request ou session  (choisir) : on prend request vu que les errors et valids ne servent ici que dans la jsp de réponse
            // Voir notes.txt => DIFFERENCE ATTRIBUTS ET PARAMETRES DANS LA REQUETE
            request.setAttribute("myErrorsRequestKey", myErrors);
            request.setAttribute("myValidAttributesRequestKey", myValidAttributes);
            //request.getSession().setAttribute("myErrorsSessionKey", myErrors);
            //request.getSession().setAttribute("myValidAttributesSessionKey", myValidAttributes);

            // Mise à null de l'objet qui sert de retour à la méthode
            myUser = null;

        } else {
            myUser.setUsername(request.getParameter("usernameFromForm"));
            myUser.setPassword(request.getParameter("passwordFromForm"));
        }

        LOG.info("=== END -  ToSignIn() in UserValidation ===");
        return myUser;


    }


    /**
     * builds up a user entity out of the http request. Must be persisted once returned
     * @param request
     * @return
     */




    public static UserEntity toSignUp(HttpServletRequest request){


        Map<String, String> myValidAttributes = new HashMap<String, String>();
        Map<String, String> myErrors = new HashMap<String, String>();

        UserEntity myUser = new UserEntity();


        //////////
        /// APPELS AUX METHODES DE VALIDATION POUR CHAQUE REQUEST PARAMETER
        //////////


        CommonValidation.checkEmptyAndLength_Input(
                request.getParameter("firstNameFromForm"),
                "firstNameFromForm",
                2,
                50,
                myErrors,
                myValidAttributes
        );

        CommonValidation.checkEmptyAndLength_Input(
                request.getParameter("lastNameFromForm"),
                "lastNameFromForm",
                2,
                50,
                myErrors,
                myValidAttributes
        );


        CommonValidation.checkEmptyAndLength_Input(
                request.getParameter("usernameFromForm"),
                "usernameFromForm",
                5,
                50,
                myErrors,
                myValidAttributes
        );


        CommonValidation.checkEmptyAndLength_Input(
                request.getParameter("passwordFromForm"),
                "passwordFromForm",
                3,
                50,
                myErrors,
                myValidAttributes
        );


        CommonValidation.checkEmptyAndLengthAndContainingString_Input(
                request.getParameter("emailAddressFromForm"),
                "emailAddressFromForm",
                5,
                100,
                "@",
                myErrors,
                myValidAttributes
        );


        CommonValidation.checkEmptyAndValidDate_Input(
                request.getParameter("birthdateFromForm"),
                "birthdateFromForm",
                myErrors,
                myValidAttributes
        );


       UserValidation.checkEmptyAndValidEnumValue_Input(
                request.getParameter("genderFromForm"),
                "genderFromForm",
                myErrors,
                myValidAttributes
        );


        if (myErrors.size() != 0) {
            LOG.debug("Errors size() : " + myErrors.size());
            LOG.debug("Error descriptif : " + myErrors.toString());
            // Stockage des inputs valides et des messages d'erreur dans l'objet request ou session  (choisir) : on prend request vu que les errors et valids ne servent ici que dans la jsp de réponse
            // Voir notes.txt => DIFFERENCE ATTRIBUTS ET PARAMETRES DANS LA REQUETE
            request.setAttribute("myErrorsRequestKey", myErrors);
            request.setAttribute("myValidAttributesRequestKey", myValidAttributes);
            //request.getSession().setAttribute("myErrorsSessionKey", myErrors);
            //request.getSession().setAttribute("myValidAttributesSessionKey", myValidAttributes);

            // Mise à null de l'objet qui sert de retour à la méthode
            myUser = null;

        } else {
            myUser.setFirstName(request.getParameter("firstNameFromForm"));
            myUser.setLastName(request.getParameter("lastNameFromForm"));
            myUser.setUsername(request.getParameter("usernameFromForm"));
            myUser.setPassword(request.getParameter("passwordFromForm"));
            myUser.setEmailAddress(request.getParameter("emailAddressFromForm"));
            myUser.setBirthdate(Date.valueOf(request.getParameter("birthdateFromForm")));
            myUser.setGender(request.getParameter("genderFromForm"));

            myUser.setInscriptionDate(new Timestamp(new java.util.Date().getTime()));


            // On met le role nommé "Default" par defaut
            EntityManager em = EMF.getEM();
            RoleService myRoleService = new RoleService(em);
            RoleEntity myRole = myRoleService.selectRoleByLabelOrNull("Default0");
            if (myRole!=null) {
                myUser.setRolesByIdRole(myRole);
            }


        }

        MyLogUtil.exitServlet(UserValidation.class,new Exception());
        return myUser;









    }



    /* Passe par signUp
    public static UserEntity toCreateUser(HttpServletRequest request, int selectedRoleId) {
        return null;
    }


     */

    /**
     * builds up a user view model to be displayed in the edit form
     * @param attachedUser
     * @return
     */


    public static UserVM toPopulateEditForm(UserEntity attachedUser) {

        UserVM populatingUser = new UserVM();

        // Amélioration => méthode de validation pour chaque champ
        // Intérêt limité vu que tout matche en terme de types et de values, les null sont acceptés etc mais cela pourrait changer donc il faut respecter le pattern
        try {
            populatingUser.setId(attachedUser.getId());

            populatingUser.setId(attachedUser.getId());
            populatingUser.setUsername(attachedUser.getUsername());
            populatingUser.setPassword(attachedUser.getPassword());
            populatingUser.setRolesByIdRole(attachedUser.getRolesByIdRole());

            return populatingUser;
        } catch (Exception e){
            LOG.debug(e.getMessage());
            return null;
        }
    }


    /**
     * Builds up a user entity to be updated once returned
     * @param request
     * @param idUserToEdit
     * @return
     */
    public static UserEntity toEdit(HttpServletRequest request, int idUserToEdit){


        Map<String, String> myValidAttributes = new HashMap<String, String>();
        Map<String, String> myErrors = new HashMap<String, String>();

        UserEntity validatedUser =null;


        //////////
        /// APPELS AUX METHODES DE VALIDATION POUR CHAQUE REQUEST PARAMETER
        //////////


        CommonValidation.checkEmptyAndLength_Input(
                request.getParameter("usernameFromForm"),
                "usernameFromForm",
                5,
                50,
                myErrors,
                myValidAttributes
        );


        CommonValidation.checkEmptyAndLength_Input(
                request.getParameter("passwordFromForm"),
                "passwordFromForm",
                3,
                50,
                myErrors,
                myValidAttributes
        );


        if (myErrors.size() != 0){
            LOG.debug("Errors size() : " + myErrors.size());
            LOG.debug("Error descriptif : " + myErrors.toString());
            request.setAttribute("myErrorsRequestKey", myErrors);
            request.setAttribute("myValidAttributesRequestKey", myValidAttributes);

        }
        else {

            // Validation du rôle :
            int idChecked = CommonValidation.checkValid_Id(request.getParameter("roleFromForm"));
            if (idChecked!= -1) {
                EntityManager em = EMF.getEM();
                RoleService myRoleService = new RoleService(em);
                RoleEntity myRole = myRoleService.selectOneByIdOrNull(idChecked);

                if (myRole != null) {

                    UserService myUserService = new UserService(em);
                    validatedUser = myUserService.selectOneByIdOrNull(idUserToEdit);
                    if (validatedUser!=null) {

                        validatedUser.setRolesByIdRole(myRole);
                        validatedUser.setUsername(request.getParameter("usernameFromForm"));
                        validatedUser.setPassword(request.getParameter("passwordFromForm"));
                    }
                }

            }
        }

        MyLogUtil.exitServlet(UserValidation.class.getSimpleName(),new Exception());
        return validatedUser;



    }


    /**
     * Check if the parameter input is empty, and if it isnt, check the value does exist in the Enum
     * @param input
     * @param inputLabel
     * @param errors
     * @param valids
     */
    public static void checkEmptyAndValidEnumValue_Input(String input, String inputLabel, Map<String, String> errors, Map<String, String> valids){

        if (!MyStringUtil.hasContent(input)) {
            errors.put(inputLabel + "Error", "Ce champ est requis");
        } else {
            try {
                Gender myGender = Gender.valueOf(input);
            }catch(IllegalArgumentException e) {
                errors.put(inputLabel + "Error","Le genre sélectionné"+input+"n'est pas tranformable en une value de l'enum Gender");
                LOG.debug("Le genre sélectionné"+input+"n'est pas tranformable en une value de l'enum Gender",e);
            }
        }
        valids.put(inputLabel + "Valid", input);

    }


}


