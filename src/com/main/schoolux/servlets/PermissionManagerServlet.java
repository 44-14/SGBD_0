package com.main.schoolux.servlets;

import com.AppConfig;
import com.main.schoolux.services.PermissionService;
import com.main.schoolux.services.RolePermissionService;
import com.main.schoolux.services.RoleService;
import com.main.schoolux.utilitaries.MyLogUtil;
import com.main.schoolux.utilitaries.MyStringUtil;
import com.main.schoolux.utilitaries.MyURLUtil;
import com.main.schoolux.validations.CommonValidation;
import com.main.schoolux.validations.PermissionValidation;
import com.main.schoolux.validations.RoleValidation;
import com.main.schoolux.viewModels.PermissionVM;
import com.persistence.entities.PermissionEntity;
import com.persistence.entities.RoleEntity;
import com.persistence.entities.RolePermissionEntity;
import com.persistence.entityFinderImplementation.EMF;
import com.sun.deploy.net.HttpRequest;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;





/* l'attribut loadOnStartup=1 permet de charget la servlet directement au démarrage de l'appli, et pas au moment de la 1ère requête reçue par la servlet */
@WebServlet(name = "PermissionManagerServlet", urlPatterns = "/permission/*", loadOnStartup = -1)
public class PermissionManagerServlet extends HttpServlet {

    // LOGGER + PATH CONSTANTS
    private final static Logger LOG = Logger.getLogger(PermissionManagerServlet.class);

    public final static String PERMISSION_CREATE_VIEW = AppConfig.PERMISSION_VIEWS_ROOT_PATH + "permissionCreate.jsp";
    public final static String PERMISSION_EDIT_VIEW = AppConfig.PERMISSION_VIEWS_ROOT_PATH + "permissionEdit.jsp";
    public final static String PERMISSION_LIST_VIEW = AppConfig.PERMISSION_VIEWS_ROOT_PATH + "permissionList.jsp";
    public final static String PERMISSION_DETAILS_VIEW = AppConfig.PERMISSION_VIEWS_ROOT_PATH + "permissionDetails.jsp";



    //////
    // INIT   appelée 1 seule fois à la création de l'instance de la servlet -- faire des choses qui serviront pour toutes les requetes de toutes les sessions
    //////
    /*
    @Override
    public void init() throws ServletException {
        super.init();
    }
     */



    //////
    //  GET
    //////
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterServlet(this,new Exception(),request);



        String actionURI = MyURLUtil.URI_LastExploitablePart(request);
        LOG.debug("URI action = " +actionURI);

        if (actionURI == null) { actionURI="permission"; }

        switch (actionURI) {

            case "permission": // same as readall case
            case "readAll":
                LOG.debug("Case readAll : user attempts to get the permission list view");
                this.readAllPermissions(request, response);

                break;

            case "createOne_getForm": //OK
                LOG.debug("User attempts to get the create permission form view");
                this.createOnePermission_getForm(request,response);

                break;

            default:
                LOG.debug("Entering default case");
                //throw new IllegalStateException("Unexpected value: " + actionURI);
                MyLogUtil.exitServlet(this, new Exception());
                request.getRequestDispatcher(request.getServletPath()).forward(request, response); // renvoie une GET vers /permission

        }
    }



    ////////
    //  POST
    ////////
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterServlet(this,new Exception(),request);

        MyLogUtil.removeAttribute(request, "session", "redirectErrorMessage");
        MyLogUtil.removeAttribute(request, "session", "redirectSuccessMessage");

        String actionForm = request.getParameter("actionFromForm"); // "actionFromForm" = button name attribute in .jsp
        LOG.debug("*=*=*=*=*=*=*=*=*=*=*=*=*=*   FORM BUTTON ACTION :  " + actionForm +"   *=*=*=*=*=*=*=*=*=*=*=*=*=*");

        if (!MyStringUtil.hasContent(actionForm))
        //if (actionForm==null || actionForm.isEmpty())
        {
            LOG.debug("The form button action is null or empty \nRedirecting to /permission"); // POST devient GET via sendRedirect
            request.getSession(true).setAttribute("redirectErrorMessage","Aucune action du formulaire n'a été récupérée");
            response.sendRedirect(request.getContextPath()+"/permission");
        }
        else {
            int id = CommonValidation.checkValid_Id(request.getParameter("idPermissionFromForm"));
            LOG.debug("Returned id from id validation : "+id);
            if (id == -1 && !actionForm.equals("createOne")) // la 2eme condition car createOne ne nécessite pas de récupérer d'id vu qu'il sera auto-généré à l'insert
            {
                LOG.debug("Form submmitted hidden id input cannot be parsed into an int type variable\nRedirecting to /permission");// POST devient GET via sendRedirect
                request.getSession(true).setAttribute("redirectErrorMessage","Aucun id n'a été récupéré");
                response.sendRedirect("/permission");
            }
            else
            {
                // instancier l 'EntityManager em et les differents services dans les methodes remplacant chaque case
                switch (actionForm) {

                    /*case "editOne_getForm":
                        LOG.debug("User attempts to get the permission editing form");
                        this.editOnePermission_getForm(request,response,id);
                        // la méthode doit envoyer vers la permissionEdit.jsp avec des inputs auto completés par les valeurs du role récupéré
                        // ici on a pas de model intermédiaire, on voit pq c'est interessant d'en faire afin d'avoir un objet qui correspond bien aux inputs du formulaire sans les champs inutiles
                        // c le meme que pour le roleDetails.jsp donc le case readOne sauf que la vue sera un formulaire avec input pré-rempli et pas une table


                        break;

                     */

                    case "readOne":
                        LOG.debug("User attempts to get the details of a permission");
                        this.readOnePermission(request,response,id);
                        break;

                    case "createOne":
                        LOG.debug("User attempts to create a new permission");
                        this.createOnePermission(request,response);
                        break;

                    /*case "editOne": Pas dans le cahier charge
                        LOG.debug("User attempts to edit a permission");
                        this.editOnePermission(request,response,id);
                        break;

                     */

                    case "deleteOne": //OK
                        LOG.debug("User attempts to delete a permission");
                        this.deleteOnePermission(request,response,id);
                        break;

                    default:
                        request.getSession(true).setAttribute("redirectErrorMessage","L'action sollicitée par le bouton n'est pas encore traitable\nRedirecting to /permission");
                        response.sendRedirect("/permission");

                }

            }
        }
    }



















    //////               //////
    //  METHODES DES SWITCH  //         // celles de doPost nécessite un sendRedirect en cas d'erreur pour passer d'une methode POST à une methde GET, celles de doGet peuvent retourner vers leur servlet via RequestDispatcher
    //////               //////         // celles de doGet  peuvent retourner vers leur servlet via RequestDispatcher ou la doGet d'une autre servlet

    // Amelioration : Factorisation des methodes, on passerait en plus un objet o en argument dont on récuperarait le type T
    // On remplace PermissionEntity par T à chaque endroit, et on passerait aussi ce type en argument au constructeur générique d'un Service etc
    // En fait on pourrait faire un Service entier générique appelé CRUD service qui factoriserait toutes les méthodes communes






    ////
    // SWITCH GET
    ////




    // Lister toutes les permissions
    private void readAllPermissions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();
        // Instanciation du service adapté
        PermissionService myPermissionService = new PermissionService(em);

        // Récuperation de la liste des permissions en db
        List<PermissionEntity> myPermissionList = myPermissionService.selectAllOrNull();
        if (myPermissionList != null) {
            // mise en request attribute de la liste (  la valeur des attributs sont des Object.class  ; la valeur des paramètres sont des String.class )
            request.setAttribute("myPermissionListRequestKey", myPermissionList);
            request.setAttribute("pageTitle","Permission list");
            MyLogUtil.exitServlet(this, new Exception());
            request.getRequestDispatcher(PERMISSION_LIST_VIEW).forward(request, response);
        }
        else
        {
            LOG.debug("Could not get the permission list");
            // on retourne vers la page de la liste en affichant un message d'erreur  => faire un if  errorMessage n est pas empty => afficher le msg  else afficher la table
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer la liste de permissions");
            MyLogUtil.exitServlet(this, new Exception());
            request.getRequestDispatcher(PERMISSION_LIST_VIEW).forward(request, response); // mais affichera que l error message via un if dans la .jsp
        }
    }





    // Envoyer le formulaire de création d'une permission
    private void createOnePermission_getForm (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.exitMethod(this, new Exception());

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();
        // Instanciation du service adapté pour récupérer la liste de roles pour les afficher dans un input select
        RoleService myRoleService = new RoleService(em);

        // Récuperation de la liste des permissions en db
        List<RoleEntity> myRoleList = myRoleService.selectAllOrNull();
        if (myRoleList != null) {
            // mise en session attribute de la liste (  la valeur des attributs sont des Object.class  ; la valeur des paramètres sont des String.class )
            request.getSession().setAttribute("myRoleListForSelectInputSessionKey", myRoleList);
            request.setAttribute("pageTitle","Permission Creation");
            MyLogUtil.exitMethod(this, new Exception());
            request.getRequestDispatcher(PERMISSION_CREATE_VIEW).forward(request, response);
        }
        else
        {
            LOG.debug("Could not get the role list");
            // on retourne vers la page de la liste en affichant un message d'erreur  => faire un if  errorMessage n est pas empty => afficher le msg  else afficher la table
            request.setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer le formulaire de création de permission");
            MyLogUtil.exitServlet(this, new Exception());
            request.getRequestDispatcher(PERMISSION_CREATE_VIEW).forward(request, response); // mais affichera que l error message via un if dans la .jsp
        }
    }








    ////
    // SWITCH POST
    ////






    // Afficher les détails d'une seule permission
    private void readOnePermission(HttpServletRequest request, HttpServletResponse response, int idPermission) throws ServletException, IOException {

        MyLogUtil.enterMethod(this,new Exception());

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();
        // Instanciation du service adapté
        PermissionService myPermissionService = new PermissionService(em);

        // Recupéreration de la permission ayant cet id en db
        PermissionEntity returnedPermission = myPermissionService.selectOneByIdOrNull(idPermission);
        if (returnedPermission != null) {
            request.setAttribute("myPermissionRequestKey", returnedPermission);
            MyLogUtil.exitMethod(this, new Exception());
            request.getRequestDispatcher(PERMISSION_DETAILS_VIEW).forward(request, response);
        }
        else {
            LOG.debug("No existing permission with id = " + idPermission + "\n Redirecting to /permission");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer la permission en vue d'en afficher ses détails");
            MyLogUtil.exitServlet(this, new Exception());
            // On sait pas envoyer vers la methode doGet vu que c est une POST request qu'on a, getRequestDispatcher ne modifie pas la methode de la requete donc ça restera un POST
            // Si on fait un sendRedirect qui permet de modifier un POST en GET, alors on perdre le errorMessage mis en request attribute
            // Donc le mieux c'est d'envoyer vers la page details.jsp malgré qu'on ne les ai pas, faire un if error not empty on l affiche, else on affiche la table de details
            response.sendRedirect("/permission");
        }
    }






    // Créer une nouvelle permission
    private void createOnePermission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterMethod(this,new Exception());


        List<Integer> selectedRolesIdList = new ArrayList<Integer>();

        // Statique ou pas ?  sachant que chaque session appelle la meme instance de servlet qui fait un multi threading sur les doGet doPost, je pense que statique = ok vu que tout se passe dans la methode, la classe validation n a aucun champ privé qui serait propre à chaque instance en particulier
        PermissionEntity validatedPermission = PermissionValidation.toCreatePermission(request,selectedRolesIdList);

        if (validatedPermission==null)
        {
            LOG.debug("Object failed validations");
            request.getSession(true).setAttribute("redirectErrorMessage", " La permission a échoué aux validations");
        }
        else
        {
            LOG.debug("Object successed validations");

            EntityManager em = EMF.getEM();

            // Instanciation du service adapté
            //PermissionService myPermissionService = new PermissionService(em);
            EntityTransaction et = null;  // nécessaire de le faire avant le try pour pouvoir y accéder dans le finally, sinon la variable 'et' serait locale au try

            try {

                et = em.getTransaction();
                et.begin();
                // Instanciation du service adapté
                PermissionService myPermissionService = new PermissionService(em);
                LOG.debug("validatedPermission avant insert flush : "+validatedPermission.toString()); // retourne 0
                myPermissionService.insertAndFlush(validatedPermission); // le flush fait que l'objet envoyé pour être attaché pointe directement vers l'objet attaché une fois la méthode terminée, alors que sans , il continue de pointer vers l'objet détaché sans id actualisé par le generation.TYPE
                LOG.debug("validatedPermission après insert flush : "+validatedPermission.toString()); // retourne 0 sans le flush, retourne les infos de l'objet attaché avec le flush
                // POUR LES ROLES DU SELECT MULTIPLE

                // Pour pas en instancier un dans chaque itération de boucle
                RoleService myRoleService = new RoleService(em);
                RolePermissionService myRolePermissionService = new RolePermissionService(em);
                for (Integer selectedRoleId : selectedRolesIdList)
                {
                    LOG.debug("Dans la boucle 1 ");

                    //List<RoleEntity> myRoleList = (ArrayList<RoleEntity>) request.getSession().getAttribute("myRoleListForSelectInputSessionKey"); // abandonné
                    //List<RoleEntity> myRoleList = myRoleService.selectAllOrNull(); // ou faire un selectOne simple sur chaque id dans la boucle, c mieux
                    //LOG.debug("Role list size : "+myRoleList.size());
                    RoleEntity myRole = myRoleService.selectOneByIdOrNull(selectedRoleId);
                    if (myRole!=null && validatedPermission!=null)
                    {
                        LOG.debug("Dans le if : correspondance trouvée : selectedRoleId = "+selectedRoleId+" donne l'entité : "+myRole.toString());

                        RolePermissionEntity myRolePermission = new RolePermissionEntity();
                        //myRolePermissionService.insertAndFlush(myRolePermission);

                        myRolePermission.setRolesByIdRole(myRole);
                        myRolePermission.setPermissionsByIdPermission(validatedPermission);
                        //myRolePermissionService.update(myRolePermission);
                        myRolePermissionService.insertAndFlush(myRolePermission);

                        //myRole.getRolesPermissionsById().add(myRolePermission);
                        validatedPermission.getRolesPermissionsById().add(myRolePermission);

                        //myRoleService.update(myRole);
                        myPermissionService.update(validatedPermission);


                    }


                }
                // FIN SELECT MULTIPLE
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
        MyLogUtil.exitServlet(this, new Exception());
        response.sendRedirect(request.getContextPath()+"/permission");
    }




    // switch POST  -- RETOURNE FORMULAIRE
    // FAIRE PermissionValidation.toPopulateEditForm(Permission)
    // Retourner la vue du formulaire d'édition d'une permission avec valeurs existantes en db pré complétées
    private void editOnePermission_getForm(HttpServletRequest request, HttpServletResponse response, int idPermission) throws ServletException, IOException {

        MyLogUtil.enterMethod(this,new Exception());


        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();
        // Instanciation du service adapté
        PermissionService myPermissionService = new PermissionService(em);

        // Recupéreration de la permission ayant cet id en db
        PermissionEntity attachedPermission = myPermissionService.selectOneByIdOrNull(idPermission);

        if (attachedPermission != null) {
            // Transformation en view model

            PermissionVM populatingPermission = PermissionValidation.toPopulateEditForm(attachedPermission);
            if(populatingPermission != null) {
                request.setAttribute("myPermissionVMRequestKey", populatingPermission);

                // Recupération de l'ensemble des rôles du contexte pour sertir le select-multiple du formulaire d'édition du role
                RoleService myRoleService = new RoleService(em);
                List <RoleEntity> myRoleList = myRoleService.selectAllOrNull();
                if (myRoleList!=null) {
                    request.getSession(true).setAttribute("myRoleListForSelectInputSessionKey", myRoleList);
                    MyLogUtil.exitMethod(this, new Exception());
                    request.getRequestDispatcher(PERMISSION_EDIT_VIEW).forward(request, response);
                    return;
                }
                else
                {
                    request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer l'ensemble des rôles pour sertir le select multiple");
                }

            }
            else {
                request.getSession(true).setAttribute("redirectErrorMessage", "La permission récupérée n'a pas pu être transformée pour peupler le formulaire");
            }
        }
        else {
            LOG.debug("No existing permission with id = " + idPermission + "\n Redirecting to /permission");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer la permission en db en vue de peupler le formulaire d'édition");


        }
        // On sait pas envoyer vers la methode doGet vu que c est une POST request qu'on a, getRequestDispatcher ne modifie pas la methode de la requete donc ça restera un POST
        // Si on fait un sendRedirect qui permet de modifier un POST en GET, alors on perdre le errorMessage mis en request attribute
        // Donc le mieux c'est d'envoyer vers la page details.jsp malgré qu'on ne les ai pas, faire un if error not empty on l affiche, else on affiche la table de details
        MyLogUtil.exitMethod(this, new Exception());
        response.sendRedirect(request.getContextPath()+"/permission");
    }



    // switch POST
    // Editer un permission
    private void editOnePermission(HttpServletRequest request, HttpServletResponse response, int idPermission) throws ServletException, IOException {

        MyLogUtil.enterMethod(this,new Exception());

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();
        // Instanciation du service adapté
        PermissionService myPermissionService = new PermissionService(em);

        // Recupéreration de la permission ayant cet id en db
        PermissionEntity attachedPermission = myPermissionService.selectOneByIdOrNull(idPermission);
        if (attachedPermission != null) {
            request.setAttribute("myPermissionRequestKey", attachedPermission);
            MyLogUtil.exitServlet(this, new Exception());
            request.getRequestDispatcher(PERMISSION_EDIT_VIEW).forward(request, response);
        }
        else {
            LOG.debug("No existing permission with id = " + idPermission + "\n Redirecting to /permission");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer la permission en vue de l'éditer en db");
            MyLogUtil.exitServlet(this, new Exception());

            response.sendRedirect(request.getContextPath()+"/role");
        }
    }



    // switch POST
    // fonctionne
    // Supprimer une permission (de manière effective)
    private void deleteOnePermission(HttpServletRequest request, HttpServletResponse response, int idPermission) throws ServletException, IOException {


        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();
        // Instanciation du service adapté
        PermissionService myPermissionService = new PermissionService(em);

        // Recupéreration de la permission ayant cet id en db
        PermissionEntity attachedPermission = myPermissionService.selectOneByIdOrNull(idPermission);
        if (attachedPermission != null) {
            //String displayMessageLabel = attachedPermission.getLabel();
            // PLUS MAINTENANT
            //myPermissionService.setEm(em); // car le service qu'on a instancié avant utilisait EntityImpl qui génère son propre em et le close , mais pour le delete on passera par ServiceImpl.class auquel il faut fourni l'em

            EntityTransaction et = null;  // nécessaire de le faire avant le try pour pouvoir y accéder dans le finally, sinon la variable 'et' serait locale au try

            try {
                et = em.getTransaction();
                et.begin();

                myPermissionService.delete(attachedPermission);
                et.commit();
                request.getSession(true).setAttribute("redirectSuccessMessage", " La permission  "+attachedPermission.getLabel()+"  a bien été supprimée"); // en session finalement pour l'afficher meme après un sendRedirect

            } catch (Exception e) {
                LOG.debug(e.getMessage());
                LOG.debug(e);
                request.getSession(true).setAttribute("redirectErrorMessage", " La permission "+attachedPermission.getLabel()+"  n'a pas été supprimée");
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
                MyLogUtil.exitMethod(this, new Exception());
                response.sendRedirect(request.getContextPath()+"/permission");
            }
        }

        else {
            LOG.debug("No existing permission with id = " + idPermission + "\n Redirecting to /permission");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer la permission en vue de la supprimer");
            MyLogUtil.exitMethod(this, new Exception());

            response.sendRedirect(request.getContextPath()+"/permission");
        }
    }



}

