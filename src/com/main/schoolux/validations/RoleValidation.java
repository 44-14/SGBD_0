package com.main.schoolux.validations;

import com.main.schoolux.services.PermissionService;
import com.main.schoolux.services.RolePermissionService;
import com.main.schoolux.services.RoleService;
import com.main.schoolux.services.UserService;
import com.main.schoolux.utilitaries.MyLogUtil;
import com.main.schoolux.viewModels.RoleVM;
import com.persistence.entities.PermissionEntity;
import com.persistence.entities.RoleEntity;
import com.persistence.entities.RolePermissionEntity;
import com.persistence.entityFinderImplementation.EMF;
import org.apache.log4j.Logger;

import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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


    /**
     * Make a role view model out of a role entity
     *
     * @param attachedRole the role entity
     * @return
     */
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
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            return null;
        }

    }


    public static RoleEntity toEditRole(HttpServletRequest request, int idRoleToEdit, List<RolePermissionEntity> myRolePermList) {

        Map<String, String> myValidAttributes = new HashMap<String, String>();
        Map<String, String> myErrors = new HashMap<String, String>();

        List<Integer> selectedPermissionsIdList = new ArrayList<Integer>();


        // APPELS AUX METHODES DE VALIDATION POUR CHAQUE PARAMETRE
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
                myValidAttributes
        );


        if (myErrors.size() != 0) {
            LOG.debug("Errors size() : " + myErrors.size());
            LOG.debug("Error descriptif : " + myErrors.toString());
            request.setAttribute("myErrorsRequestKey", myErrors);
            request.setAttribute("myValidAttributesRequestKey", myValidAttributes);
            return null;
        } else { // Pas eu d'erreurs et les strings des permissionsFromForm ont tous été parsés en Integer

            if (selectedPermissionsIdList != null) // la methode qui valide met à null si il n'y a aucun parameter permissionsFromForm
            {
                EntityManager em = EMF.getEM();

                // Instanciation des 3 services
                RoleService myRoleService = new RoleService(em);
                PermissionService myPermissionService = new PermissionService(em);
                RolePermissionService myRolePermissionService = new RolePermissionService(em);

                // Recupération du rôle dans le contexte via l'idRole reçu en paramètre qui est validé dans le controleur
                RoleEntity myRole = myRoleService.selectOneByIdOrNull(idRoleToEdit);

                if (myRole != null) {


                    myRole.setLabel(request.getParameter("labelFromForm"));
                    myRole.setAbbreviation(request.getParameter("abbreviationFromForm"));
                    myRole.setDescription(request.getParameter("descriptionFromForm"));


                    for (int idPermission : selectedPermissionsIdList) {

                        // On recupere une permission sur base de l'id récupéré par itération de la foreach en cours
                        PermissionEntity myPermission = myPermissionService.selectOneByIdOrNull(idPermission);
                        if (myPermission != null) {

                            // On crée et sertit l'objet RolePermission représentant la jointure
                            RolePermissionEntity myRP_Entity = new RolePermissionEntity();
                            myRP_Entity.setPermissionsByIdPermission(myPermission);
                            myRP_Entity.setRolesByIdRole(myRole);

                            myRolePermList.add(myRP_Entity);


                        }
                    }

                    // On modifie le pointeur de la liste entière plutot que de vérifier si chaque RolePerm est déjà dedans
                    // donc il ne faut pas faire des myRole.getRolesPermissionsById().add(myRP_Entity) dans la boucle foreach au dessus
                    // OU alors c'est à faire dans le controller une fois que chaque RolePerm aura été inséré
                    myRole.setRolesPermissionsById(myRolePermList);

                    // Ici les 2 références vers myRole et myRolePermList sont les mêmes que celles passées en paramètre à l'appel de la méthode de validation
                    // Donc elles seront toutes les 2 accessibles par le controller
                    // On vérifiera dans ce controller si le retour est false ou true, si c'est true alors nos 2 références ne sont pas nulles et contiennent ce qu'il faut
                    return myRole;
                }
            }


            MyLogUtil.exitServlet(UserValidation.class.getSimpleName(), new Exception());
            return null;
        }
    }


    /**
     * Makes a role entity to be persisted once returned
     *
     * @param request
     * @param selectedPermissionsIdList
     * @return
     */

    public static RoleEntity toCreateRole(HttpServletRequest request, List<Integer> selectedPermissionsIdList) {


        Map<String, String> myValidAttributes = new HashMap<String, String>();
        Map<String, String> myErrors = new HashMap<String, String>();

        RoleEntity processedRole = new RoleEntity();


        // APPELS AUX METHODES DE VALIDATION POUR CHAQUE PARAMETRE
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

