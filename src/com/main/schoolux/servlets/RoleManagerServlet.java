package com.main.schoolux.servlets;

import com.AppConfig;
import com.main.schoolux.services.PermissionService;
import com.main.schoolux.services.RolePermissionService;
import com.main.schoolux.services.RoleService;
import com.main.schoolux.utilitaries.MyLogUtil;
import com.main.schoolux.utilitaries.MyStringUtil;
import com.main.schoolux.utilitaries.MyURLUtil;
import com.main.schoolux.validations.CommonValidation;
import com.main.schoolux.validations.RoleValidation;
import com.persistence.entities.PermissionEntity;
import com.persistence.entities.RoleEntity;
import com.persistence.entities.RolePermissionEntity;
import com.persistence.entityFinderImplementation.EMF;
import com.sun.webkit.Invoker;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// La reference est la permissionServlet + ce qui lui est lié, il y figure tous les commentaires de cheminement qui ont été dégrossis ici

// l'attribut loadOnStartup=1 permet de charget la servlet directement au démarrage de l'appli, et pas au moment de la 1ère requête reçue par la servlet
@WebServlet(name = "RoleManagerServlet", urlPatterns = {"/role/*"}, loadOnStartup = -1)
public class RoleManagerServlet extends HttpServlet {

    // LOGGER + PATH CONSTANTS
    private final static Logger LOG = Logger.getLogger(RoleManagerServlet.class);

    public final static String ROLE_CREATE_VIEW = AppConfig.ROLE_VIEWS_ROOT_PATH+"roleCreate.jsp";
    public final static String ROLE_EDIT_VIEW = AppConfig.ROLE_VIEWS_ROOT_PATH+"roleEdit.jsp";
    public final static String ROLE_LIST_VIEW = AppConfig.ROLE_VIEWS_ROOT_PATH+"roleList.jsp";
    public final static String ROLE_DETAILS_VIEW = AppConfig.ROLE_VIEWS_ROOT_PATH+"roleDetails.jsp";



    //////
    // INIT   appelée 1 seule fois à la création de l'instance de la servlet -- faire des choses qui serviront pour toutes les requetes de toutes les sessions
    //////
    /*
    @Override
    public void init() throws ServletException {
        super.init();
    }
     */



    ////////
    //  GET
    ////////
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterServlet(this, new Exception(), request);


        String actionURI = MyURLUtil.URI_LastExploitablePart(request);
        LOG.debug("URI action = " + actionURI);

        if (actionURI == null) { actionURI = "role"; }


        switch (actionURI) {

            case "role": // same as readAll case
            case "readAll":
                LOG.debug("Case readAll : User attempts to get the role list view");
                this.readAllRoles(request, response);
                break;


            case "createOne_getForm":
                LOG.debug("Case createOne_getForm : User attempts to get the create role form view");
                this.createOneRole_getForm(request,response);
                break;


            default:
                LOG.debug("Entering default case");
                MyLogUtil.exitServlet(this, new Exception());
                request.getRequestDispatcher(request.getServletPath()).forward(request, response); // relance sous forme de GET  /role
        }
    }



    ////////
    //  POST
    ////////
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterServlet(this,new Exception(),request);

        MyLogUtil.removeAttribute(request, "session", "redirectErrorMessage");
        MyLogUtil.removeAttribute(request, "session", "redirectSuccessMessage");


        String actionForm = request.getParameter("actionFromForm"); // actionFromForm = button name attribute in .jsp
        LOG.debug("*=*=*=*=*=*=*=*=*=*=*=*=*=*   FORM BUTTON ACTION :  " + actionForm +"   *=*=*=*=*=*=*=*=*=*=*=*=*=*");

        if (!MyStringUtil.hasContent(actionForm))
        {
            LOG.debug("The form button action is null or empty \nRedirecting to /user"); // POST devient GET via sendRedirect
            request.getSession(true).setAttribute("redirectErrorMessage","Aucune action du formulaire n'a été récupérée");
            response.sendRedirect(request.getContextPath()+"/role");
        }
        else {
            int id = CommonValidation.checkValid_Id(request.getParameter("idRoleFromForm"));
            LOG.debug("Returned id from id validation : "+id);
            if (id == -1 && !actionForm.equals("createOne")) // la 2eme condition car createOne ne nécessite pas de récupérer d'id vu qu'il sera auto-généré à l'insert
            {
                LOG.debug("Form submmitted an hidden id input cannot be parsed into an int type variable \nRedirecting to /role"); // POST devient GET via sendRedirect
                request.getSession(true).setAttribute("redirectErrorMessage","Aucun id n'a été récupéré");
                response.sendRedirect(request.getContextPath()+"/role");
            }
            else
            {
                // instancier l 'EntityManager em et les differents services dans les methodes englobant les instructions de chaque case
                switch (actionForm) {

                    case "editOne_getForm":
                        LOG.debug("User attempts to get the role editing form");
                        this.editOneRole_getForm(request,response,id);
                        // la méthode doit envoyer vers la roleEdit.jsp avec des inputs auto completés par les valeurs du role récupéré
                        // ici on a pas de model intermédiaire, on voit pq c'est interessant d'en faire afin d'avoir un objet qui correspond bien aux inputs du formulaire sans les champs inutiles
                        // c le meme cheminement que pour le roleDetails.jsp donc le case readOne sauf que la vue sera un formulaire avec input pré-rempli et pas une table
                        break;


                    case "readOne":
                        LOG.debug("User attempts to get the details of a role");
                        this.readOneRole(request,response,id);
                        break;


                    case "createOne" :
                        LOG.debug("User attempts to create a new role");
                        this.createOneRole(request,response);
                        break;


                    case "editOne":
                        LOG.debug("User attempts to edit a role");
                        this.editOneRole(request,response,id);
                        break;


                    case "deleteOne":
                        LOG.debug("User attempts to delete a role");
                        this.deleteOneRole(request,response,id);
                        break;


                    default:
                        request.getSession(true).setAttribute("redirectErrorMessage","L'action sollicitée par le bouton n'est pas encore traitable\nRedirecting to /role");
                        response.sendRedirect(request.getContextPath()+"/role");

                }
            }
        }
    }














    //////               //////
    //  METHODES DES SWITCH  //
    //////               //////




    ////
    // switch GET
    ////



    // switch GET
    // Lister tous les roles
    private void readAllRoles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();

        // Instanciation du service adapté
        RoleService myRoleService = new RoleService(em);

        // Récuperation de la liste des roles en db
        List<RoleEntity> myRoleList = myRoleService.selectAllOrNull();
        if (myRoleList != null) {
            // mise en request attribute de la liste (  la valeur des attributs sont des Object.class  ; la valeur des paramètres sont des String.class )
            request.setAttribute("myRoleListRequestKey", myRoleList);
            request.setAttribute("pageTitle","Role list");
            MyLogUtil.exitServlet(this, new Exception());
            request.getRequestDispatcher(ROLE_LIST_VIEW).forward(request, response);
        }
        else
        {
            LOG.debug("Could not get the role list");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer la liste de roles");
            MyLogUtil.exitMethod(this, new Exception());
            request.getRequestDispatcher(ROLE_LIST_VIEW).forward(request, response); // mais affichera que l error message via un if dans la .jsp
        }
    }



    // switch GET
    // Envoyer le formulaire de création d'un role
    private void createOneRole_getForm (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterMethod(this, new Exception());

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();
        // Instanciation du service adapté pour récupérer la liste de permissions afin de les afficher dans un input select multiple
        PermissionService myPermissionService = new PermissionService(em);

        // Récuperation de la liste des permissions en contexte
        List<PermissionEntity> myPermissionList = myPermissionService.selectAllOrNull();
        if (myPermissionList != null) {
            // mise en session attribute de la liste (  la valeur des attributs sont des Object.class  ; la valeur des paramètres sont des String.class )
            request.getSession().setAttribute("myPermissionListForSelectInputSessionKey", myPermissionList);
            request.setAttribute("pageTitle","Role Creation");
            MyLogUtil.exitMethod(this, new Exception());
            request.getRequestDispatcher(ROLE_CREATE_VIEW).forward(request, response);
        }
        else
        {
            LOG.debug("Could not get the role list");
            // on retourne vers la page de la liste en affichant un message d'erreur  => faire un if  errorMessage n est pas empty => afficher le msg  else afficher la table
            request.setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer le formulaire de création de permission");
            MyLogUtil.exitServlet(this, new Exception());
            request.getRequestDispatcher(ROLE_CREATE_VIEW).forward(request, response); // mais affichera que l error message via un if dans la .jsp
        }
    }




    ////
    // switch POST
    ////





    // switch POST
    // Afficher les détails d'un seul role
    private void readOneRole(HttpServletRequest request, HttpServletResponse response, int idRole) throws ServletException, IOException {

        MyLogUtil.enterMethod(this,new Exception());

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();

        // Instanciation du service adapté
        RoleService myRoleService = new RoleService(em);

        // Recupéreration du role ayant cet id en db
        RoleEntity returnedRole = myRoleService.selectOneByIdOrNull(idRole);
        if (returnedRole != null) {
            request.setAttribute("myRoleRequestKey", returnedRole);
            MyLogUtil.exitMethod(this, new Exception());
            request.getRequestDispatcher(ROLE_DETAILS_VIEW).forward(request, response);
        }
        else {
            LOG.debug("No existing role with id = " + idRole + "\n Redirecting to /role");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer le role en vue d'en afficher ses détails");
            MyLogUtil.exitMethod(this, new Exception());
            response.sendRedirect(request.getContextPath()+"/role");
        }
    }











    // switch POST
    // Créer un nouveau role
    private void createOneRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterMethod(this, new Exception());


        List<Integer> selectedPermissionsIdList = new ArrayList<Integer>();

        RoleEntity validatedRole = RoleValidation.toCreateRole(request, selectedPermissionsIdList);

        if (validatedRole==null){
            LOG.debug("Object failed validations");
            request.getSession(true).setAttribute("redirectErrorMessage", " Le role a échoué aux validations");
        }
        else {
            LOG.debug("Object successed validations");

            EntityManager em = EMF.getEM();
            EntityTransaction et = null;  // nécessaire de le faire avant le try pour pouvoir y accéder dans le finally, sinon la variable 'et' serait locale au try

            try {

                et = em.getTransaction();
                et.begin();

                // Instanciation du service adapté
                RoleService myRoleService = new RoleService(em);

                LOG.debug("validatedPermission avant insert flush : "+validatedRole.toString()); // retourne 0
                myRoleService.insertAndFlush(validatedRole); // le flush fait que l'objet envoyé pour être attaché pointe directement vers l'objet attaché une fois la méthode terminée, alors que sans , il continue de pointer vers l'objet détaché sans id actualisé par le generation.TYPE
                LOG.debug("validatedPermission après insert flush : "+validatedRole.toString()); // retourne 0 sans le flush, retourne les infos de l'objet attaché avec le flush


                // Instanciation des autres services avant la boucle
                PermissionService myPermissionService = new PermissionService(em);
                RolePermissionService myRolePermissionService = new RolePermissionService(em);

                // Boucle sur les id des permissions sélectionnées afin de récupérer les PermissionEntity liées à ces id
                for (Integer selectedPermissionId : selectedPermissionsIdList) {
                    LOG.debug("Dans la boucle itérant sur la liste des id de permissions sélectionnées ");
                    PermissionEntity myPermission = myPermissionService.selectOneByIdOrNull(selectedPermissionId);

                    if (myPermission != null) {
                        LOG.debug("IF : correspondance trouvée : selectedPermissionId = " + selectedPermissionId + " donne l'entité : " + myPermission.toString());

                        // on a un role ainsi qu'une permission tous les 2 valides et attachés
                        // on va les utiliser pour sertir un nouveau RolePermissionEntity
                        RolePermissionEntity myRolePermission = new RolePermissionEntity();
                        //myRolePermissionService.insertAndFlush(myRolePermission);

                        myRolePermission.setRolesByIdRole(validatedRole);
                        myRolePermission.setPermissionsByIdPermission(myPermission);
                        //myRolePermissionService.update(myRolePermission);

                        // On persiste l'instance RolePermission, celle-ci étant sertie des 2 objets attachés
                        myRolePermissionService.insertAndFlush(myRolePermission);

                        // On ajoute maintenant l'instance RolePermission dans les collections des 2 objets attachés qu'on updatera ensuite
                        // Cette manipulation est liée à la maj du cache qui ne se fait qu'au démarrage de l'appli quand elle scanne la db
                        // => voir Projet SGBD (2020-06-03 at 09_32 GMT-7) de 1:26:28 à 1:29:00
                        validatedRole.getRolesPermissionsById().add(myRolePermission);
                        myPermission.getRolesPermissionsById().add(myRolePermission);

                        myRoleService.update(validatedRole);
                        myPermissionService.update(myPermission);
                    }
                }

                et.commit();
                request.getSession(true).setAttribute("redirectSuccessMessage", " La permission a bien été créé"); // en session finalement pour l'afficher meme après un sendRedirect

                } catch (Exception e) {
                    //LOG.debug(e.getMessage());
                    //LOG.debug("\n\n\n\n\n");
                    LOG.debug(e);
                    request.getSession(true).setAttribute("redirectErrorMessage", " La permission n'a pas été créé");

                } finally {
                    em.clear();

                    LOG.debug("em open ? : "+em.isOpen());
                    if (em.isOpen())
                    {
                        em.close();
                        LOG.debug("em open ? : "+em.isOpen());
                    }

                    if (et != null && et.isActive()) {
                        et.rollback();
                    }
                }
            }
            MyLogUtil.exitMethod(this, new Exception());
            response.sendRedirect(request.getContextPath()+"/role");
        }















    // switch POST  -- RETOURNE FORMULAIRE
    // FAIRE RoleValidation.toPopulateEditForm(role)
    // Retourner la vue du formulaire d'édition d'un role avec valeurs existantes en db pré complétées
    private void editOneRole_getForm(HttpServletRequest request, HttpServletResponse response, int idRole) throws ServletException, IOException {

        MyLogUtil.enterMethod(this,new Exception());

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();

        // Instanciation du service adapté
        RoleService myRoleService = new RoleService(em);

        // Recupéreration du role ayant cet id en db
        RoleEntity attachedRole = myRoleService.selectOneByIdOrNull(idRole);
        if (attachedRole != null) {
            RoleEntity populatingRole = RoleValidation.toPopulateEditForm(attachedRole);
            if(populatingRole != null) {
                request.setAttribute("myRoleRequestKey", populatingRole);
                MyLogUtil.exitMethod(this,new Exception());
                request.getRequestDispatcher(ROLE_EDIT_VIEW).forward(request, response);
            }
            else {
                MyLogUtil.exitMethod(this,new Exception());
                request.getSession(true).setAttribute("redirectErrorMessage", "Le rôle récupéré n'a pas pu être transformé pour peupler le formulaire");
            }
        }
        else {
            LOG.debug("No existing role with id = " + idRole + "\n Redirecting to /role");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer le role en db en vue de peupler le formulaire d'édition");

            MyLogUtil.exitMethod(this,new Exception());
            response.sendRedirect(request.getContextPath()+"/role");
        }
    }








    // switch POST  // TO DO // CLOSE TO createOne
    // Editer un role
    private void editOneRole(HttpServletRequest request, HttpServletResponse response, int idRole) throws ServletException, IOException {

        MyLogUtil.enterMethod(this, new Exception());


        List<Integer> selectedPermissionsIdList = new ArrayList<Integer>();

        RoleEntity validatedRole = RoleValidation.toCreateRole(request, selectedPermissionsIdList);


        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();

        // Instanciation du service adapté
        RoleService myRoleService = new RoleService(em);

        // Recupéreration du role ayant cet id en db
        RoleEntity attachedRole = myRoleService.selectOneByIdOrNull(idRole);
        if (attachedRole != null) {
            request.setAttribute("myRoleRequestKey", attachedRole);
            MyLogUtil.exitMethod(this, new Exception());
            request.getRequestDispatcher(ROLE_EDIT_VIEW).forward(request, response);
        }
        else {
            LOG.debug("No existing role with id = " + idRole + "\n Redirecting to /role");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer le role en vue de l'éditer en db");
            MyLogUtil.exitServlet(this, new Exception());
           response.sendRedirect(request.getContextPath()+"/pzemiqqionssion");
        }
    }








    // switch POST
    // Supprimer un role (de manière effective)
    private void deleteOneRole(HttpServletRequest request, HttpServletResponse response, int idRole) throws ServletException, IOException {

        MyLogUtil.enterMethod(this,new Exception());

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();

        // Instanciation du service adapté
        RoleService myRoleService = new RoleService(em);

        // Recupéreration du role ayant cet id en db
        RoleEntity attachedRole = myRoleService.selectOneByIdOrNull(idRole);
        if (attachedRole != null) {

            EntityTransaction et = null;  // nécessaire de le faire avant le try pour pouvoir y accéder dans le finally, sinon la variable 'et' serait locale au try

            try {

                et = em.getTransaction();
                et.begin();

                myRoleService.delete(attachedRole);

                et.commit();
                request.getSession(true).setAttribute("redirectSuccessMessage", " Le rôle  "+attachedRole.getLabel()+"  a bien été supprimé"); // en session finalement pour l'afficher meme après un sendRedirect

            } catch (Exception e) {
                LOG.debug(e.getMessage());
                LOG.debug(e);
                request.getSession(true).setAttribute("redirectErrorMessage", " Le rôle "+attachedRole.getLabel()+" n'a pas été supprimé");
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
                MyLogUtil.exitMethod(this, new Exception());
                response.sendRedirect(request.getContextPath()+"/role");
            }
        }

        else {
            LOG.debug("No existing role with id = " + idRole + "\n Redirecting to /role");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer le rôle en vue de le supprimer");
            MyLogUtil.exitMethod(this, new Exception());
            // On sait pas envoyer vers la methode doGet vu que c est une POST request qu'on a, getRequestDispatcher ne modifie pas la methode de la requete donc ça restera un POST
            // Si on fait un sendRedirect qui permet de modifier un POST en GET, alors on perdre le errorMessage mis en request attribute
            // Donc le mieux c'est d'envoyer vers la page details.jsp malgré qu'on ne les ai pas, faire un if error not empty on l affiche, else on affiche la table de details
            response.sendRedirect(request.getContextPath()+"/role");
        }
    }




}

