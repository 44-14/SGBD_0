package com.main.schoolux.validations;

import com.main.schoolux.services.RoleService;
import com.main.schoolux.utilitaries.MyIntUtil;
import com.main.schoolux.viewModels.PermissionVM;
import com.main.schoolux.viewModels.RoleVM;
import com.persistence.entities.PermissionEntity;
import com.persistence.entities.RoleEntity;
import com.persistence.entities.RolePermissionEntity;
import com.persistence.entityFinderImplementation.EMF;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Ce qui est lié à la PermissionServlet contient toutes les infos en commentaires

public class PermissionValidation {

    private final static Logger LOG = Logger.getLogger(PermissionValidation.class);


    public static PermissionEntity toEditPermission(HttpServletRequest request) {
        PermissionEntity processedPermission = new PermissionEntity();
        return processedPermission;
    }


    public static PermissionVM toPopulateEditForm(PermissionEntity attachedPermission) {
        // do something with attachedRole to transform it in populatingRole to populate the edit form
        PermissionVM populatingPermission = new PermissionVM();

        // Amélioration => méthode de validation pour chaque champ
        // Intérêt limité vu que tout matche en terme de types et de values, les null sont acceptés etc mais cela pourrait changer donc il faut respecter le pattern
        try {
            populatingPermission.setId(attachedPermission.getId());
            populatingPermission.setLabel(attachedPermission.getLabel());
            populatingPermission.setAbbreviation(attachedPermission.getAbbreviation());
            populatingPermission.setDescription(attachedPermission.getDescription());
            populatingPermission.setRolesPermissionsById(attachedPermission.getRolesPermissionsById());
            return populatingPermission;
        } catch (Exception e){
            LOG.debug(e.getMessage());
            return null;
        }

    }


    public static PermissionEntity toCreatePermission(HttpServletRequest request, List<Integer> selectedRolesIdList) {

        Map<String, String> myValidAttributes = new HashMap<String, String>();
        Map<String, String> myErrors = new HashMap<String, String>();

        PermissionEntity processedPermission = new PermissionEntity();


        /// APPELS AUX METHODES DE VALIDATION POUR CHAQUE PARAMETRE

        CommonValidation.checkEmptyAndLength_Input(
                request.getParameter("labelFromForm"),
                "labelFromForm",
                5,
                100,
                myErrors,
                myValidAttributes
        );

        CommonValidation.checkEmptyAndLength_Input(
                request.getParameter("abbreviationFromForm"),
                "abbreviationFromForm",
                6,
                10,
                myErrors,
                myValidAttributes
        );

        CommonValidation.checkLength_Input(
                request.getParameter("descriptionFromForm"),
                "descriptionFromForm",
                0,
                2000,
                myErrors,
                myValidAttributes
        );

        // selectedRolesIdList sera accessible par la méthode qui a appelé toCreatePermission car elle a envoyé sa référence en argument vers ici
        selectedRolesIdList = CommonValidation.CheckIds_SelectMultiple(
                request.getParameterValues("rolesFromForm"),
                "rolesFromForm",
                selectedRolesIdList,
                myErrors,
                myValidAttributes);


        // ParticularValidation pour des champs précis qui eux retourneraient cette validation
        // genre en méthode privée ici
        // private String checkData_Input ( request,myErrors,myValidAttributes)
        // appel :  String dataInput = this.checkData_Input(req,myErrors,myValidAttributs)
        // si myErrors.size() !=0 alors object.data = dataInput;


        if (myErrors.size() != 0) {
            LOG.debug("Errors : " + myErrors.size());
            // Stockage des inputs valides et des messages d'erreur dans l'objet request ou session
            // on prend request vu que les errors et valids ne servent ici que dans la jsp de réponse
            // Voir notes.txt => DIFFERENCE ATTRIBUTS ET PARAMETRES DANS LA REQUETE
            request.setAttribute("myErrorsRequestKey", myErrors);
            request.setAttribute("myValidAttributesRequestKey", myValidAttributes);
            //request.getSession().setAttribute("myErrorsSessionKey", myErrors);
            //request.getSession().setAttribute("myValidAttributesSessionKey", myValidAttributes);

            // Mise à null de l'objet qui sert de retour à la méthode
            processedPermission = null;

        } else {

            processedPermission.setLabel(request.getParameter("labelFromForm"));
            processedPermission.setAbbreviation(request.getParameter("abbreviationFromForm"));
            processedPermission.setDescription(request.getParameter("descriptionFromForm"));



            /*
                PROCEDURE POUR LES ROLES DU SELECT MULTIPLE
                DANS LA VALIDATION :
                Recuperer les id via getParameterValues(nomAttribut) + les vérifier + modifier la List<Integer> selectedRolesIdList reçue en paramètre
                Comme la référence ne change pas, on pourra accéder à cette liste depuis le controlleur meme sans en faire un return ici
                Retourner la permission completée (label - abbreviation - description) ou null si fail
                DANS LE CONTROLLER
                Ouvrir transaction
                Persister la permission + récupérer l'entityPermission nouvellement créer ayant aussi un id (ou est ce qu'un objet persisté est modifié directement ? à verif
                Instancier un RoleService
                boucler sur la liste d'id de roles selectionnés
                faire un selectOne à chaque itération,
                si le retour n'est pas null on instancie un RolePermissionEntity avec le role retourné par selectOne + la permission retournée qu on vient de créer
                on persiste le RolePErmissionEntity à chaque tour
                on ferme la transaction try catch etc

            */



            /* CODE A METTRE DANS LE CONTROLLEUR POUR LES ROLES DU SELECT SIMPLE

            for (Integer selectedRoleId : selectedRolesIdList)
            {
                EntityManager em = EMF.getEM(); // ATTENTION : englober toute la transaction avec cet em
                RoleService myRoleService = new RoleService(em);
                 //List<RoleEntity> myRoleList = (ArrayList<RoleEntity>) request.getSession().getAttribute("myRoleListForSelectInputSessionKey"); // abandonné
                 List<RoleEntity> myRoleList = myRoleService.selectAllOrNull();
                for (RoleEntity myRole : myRoleList)
                {
                    if (selectedRoleId == myRole.getId()){
                    RolePermissionEntity myRolePermission = new RolePermissionEntity();
                    myRolePermission.setRolesByIdRole(myRole);
                    myRolePermission.setPermissionsByIdPermission(newlyCreatedPermission);
                    persis etc
                    }
                }
            }

             */
        }
        return processedPermission;
    }
}













/*  POUR ROLES DU SELECT MULTIPLE

    List <RoleEntity> myRoleList = (List<RoleEntity>) request.getSession().getAttribute("myRoleListForSelectInputSessionKey");

            for (int selectedRoleId : myRolesIdsChecked)
                    for (:
                    ) {

                    }
                    ) {

                    }myRolesIdsChecked




                    ; String[] selected = r;
                    if (selected != null)
                    {
                    List <RoleEntity> myRoleList = (List<RoleEntity>) request.getSession().getAttribute("myRoleListForSelectInputSessionKey");

        processedPermission.getRolesPermissionsById().add(myRolePermissionEntity);
        }
        EntityManager em = EMF.getEM();


        for ( String role : selected)
        {
        request.getSession(""
        }
*/

























/*


    /////////
    /// METHODES DE VALIDATION DES PARAMETRES DE LA REQUETE
    //////////


    private String validationPassword(String password, String confirmationPassword) throws Exception {


        if (password == null || password.isEmpty()) {
            throw new Exception("Un mot de passe est requis");
        }

        if (!password.equals(confirmationPassword)) {
            throw new Exception("Les mots de passe ne sont pas identiques ");
        }

        return password;

    }


    /**
     * Validations liées à la création d'un utilisateur
     **/
/*    public UserEntity UserValidation_Create(HttpServletRequest request) {

        LOG.info("=== START - createValidation() in UserValidation ===");


        Map<String, String> myValidAttributes = new HashMap<String, String>();
        Map<String, String> myErrors = new HashMap<String, String>();

        UserEntity myUser = new UserEntity();


        CommonValidation.checkEmpty_Input(
                request.getParameter("firstNameFromForm"),
                "firstNameFromForm",
                myErrors,
                myValidAttributes);


        //////////
        /// RECUPERATION DES PARAMETRES DE LA REQUETE
        //////////

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


        /////
        // APPELS AUX METHODES DE VALIDATION POUR CHAQUE PARAMETRE
        /////







        /* Stockage du résultat et des messages d'erreur dans l'objet request ou session  (choisir)*/
        // Voir notes.txt => DIFFERENCE ATTRIBUTS ET PARAMETRES DANS LA REQUETE
        //request.setAttribute("myErrorsRequestKey", myErrors);
        //request.setAttribute("myValidAttributesRequestKey", myValidAttributes);
/*        request.setAttribute("myErrorsSessionKey", myErrors);
        request.setAttribute("myValidAttributesSessionKey", myValidAttributes);



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
/*        if (myErrors.size() != 0) {
            myUser = null;
        }

        LOG.info("=== END - createValidation() in UserValidation ===");
        return myUser;


    }// END createValidation()


    ////////
    // METHODES DE VALIDATION DES PARAMETRES DE LA REQUETE
    ////////
}

*/