package com.main.schoolux.validations;

import com.main.schoolux.viewModels.RoleVM;
import com.persistence.entities.PermissionEntity;
import com.persistence.entities.RoleEntity;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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

public class RoleValidation {

    private final static Logger LOG = Logger.getLogger(RoleValidation.class);


    public static RoleEntity toEditRole(HttpServletRequest request) {

        RoleEntity processedRole = new RoleEntity();
        return processedRole;
    }



    public static RoleVM toPopulateEditForm(RoleEntity attachedRole) {
        // do something with attachedRole to transform it in populatingRole to populate the edit form
        RoleVM populatingRole = new RoleVM();


        // Amélioration => méthode de validation pour chaque champ
        // Intérêt limité vu que tout matche en terme de types et de values, les null sont acceptés etc mais cela pourrait changer donc il faut respecter le pattern
        try {
            populatingRole.setId(attachedRole.getId());
            populatingRole.setLabel(attachedRole.getLabel());
            populatingRole.setAbbreviation(attachedRole.getAbbreviation());
            populatingRole.setDescription(attachedRole.getDescription());
            populatingRole.setRolesPermissionsById(attachedRole.getRolesPermissionsById());
            populatingRole.setUsersById(attachedRole.getUsersById());
            return populatingRole;
        } catch (Exception e){
            LOG.debug(e.getMessage());
            return null;
        }

    }




    public static RoleEntity toCreateRole(HttpServletRequest request, List<Integer> selectedPermissionsIdList) {


        Map<String, String> myValidAttributes = new HashMap<String, String>();
        Map<String, String> myErrors = new HashMap<String, String>();

        RoleEntity processedRole = new RoleEntity();


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
        selectedPermissionsIdList = CommonValidation.CheckIds_SelectMultiple(
                request.getParameterValues("permissionsFromForm"),
                "permissionsFromForm",
                selectedPermissionsIdList,
                myErrors,
                myValidAttributes);


        // VERIFICATION EXISTENCE D ERREURS ET ASSIGNATION DU RETOUR EN CONSEQUENCE
        if (myErrors.size() != 0) {
            LOG.debug("Errors size : " + myErrors.size());
            // mise des hashmap en attributs de scope request
            request.setAttribute("myErrorsRequestKey", myErrors);
            request.setAttribute("myValidAttributesRequestKey", myValidAttributes);
            //request.getSession().setAttribute("myErrorsSessionKey", myErrors);
            //request.getSession().setAttribute("myValidAttributesSessionKey", myValidAttributes);

            // Mise à null de l'objet qui sert de retour à la méthode, informant de l'apparition d'erreurs
            processedRole = null;

        } else {

            processedRole.setLabel(request.getParameter("labelFromForm"));
            processedRole.setAbbreviation(request.getParameter("abbreviationFromForm"));
            processedRole.setDescription(request.getParameter("descriptionFromForm"));
        }
        return processedRole;
    }
}

