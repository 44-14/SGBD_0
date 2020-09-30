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


        }
        return processedPermission;
    }
}


