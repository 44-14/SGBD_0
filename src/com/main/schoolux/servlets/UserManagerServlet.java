package com.main.schoolux.servlets;

import com.AppConfig;
import com.main.schoolux.services.PermissionService;
import com.main.schoolux.services.RolePermissionService;
import com.main.schoolux.services.RoleService;
import com.main.schoolux.services.UserService;
import com.main.schoolux.utilitaries.MyLogUtil;
import com.main.schoolux.utilitaries.MyStringUtil;
import com.main.schoolux.utilitaries.MyURLUtil;
import com.main.schoolux.validations.CommonValidation;
import com.main.schoolux.validations.RoleValidation;
import com.main.schoolux.validations.UserValidation;
import com.main.schoolux.viewModels.UserVM;
import com.persistence.entities.PermissionEntity;
import com.persistence.entities.RoleEntity;
import com.persistence.entities.RolePermissionEntity;
import com.persistence.entities.UserEntity;
import com.persistence.entityFinderImplementation.EMF;
import org.apache.log4j.Level;
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

// La reference est la permissionServlet + ce qui lui est lié, il y figure tous les commentaires de cheminement qui ont été dégrossis ici

/* l'attribut loadOnStartup permet de charget la servlet directement au démarrage de l'appli, et pas au moment de la 1ère requête reçue par le servlet */
@WebServlet(name = "UserManagerServlet", urlPatterns = "/user/*", loadOnStartup = -1)
public class UserManagerServlet extends HttpServlet {

    // LOGGER + PATH CONSTANTS + SERVLET MESSAGES LISTS
    private final static Logger LOG = Logger.getLogger(UserManagerServlet.class);

    public final static String USER_CREATE_VIEW = AppConfig.USER_VIEWS_ROOT_PATH + "userCreate.jsp";
    public final static String USER_EDIT_VIEW = AppConfig.USER_VIEWS_ROOT_PATH + "userEdit.jsp";
    public final static String USER_LIST_VIEW = AppConfig.USER_VIEWS_ROOT_PATH + "userList.jsp";
    public final static String USER_DETAILS_VIEW = AppConfig.USER_VIEWS_ROOT_PATH + "userDetails.jsp";


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

        if (actionURI == null) { actionURI = "user"; }


        switch (actionURI) {

            case "user": // same as readAll case
            case "readAll":
                LOG.debug("Case readAll : user attempts to get the user list view");
                this.readAllUsers(request, response);
                break;


            case "createOne_getForm":
                LOG.debug("Case createOne_getForm : User attempts to get the create role form view");
                this.createOneUser_getForm(request,response);
                break;


            default:
                LOG.debug("Entering default case");
                MyLogUtil.exitServlet(this, new Exception());
                request.getRequestDispatcher(request.getServletPath()).forward(request, response);// relance sous forme de GET  /role

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
            response.sendRedirect(request.getContextPath()+"/user");
        }
        else {
            int id = CommonValidation.checkValid_Id(request.getParameter("idUserFromForm"));
            LOG.debug("Returned id from id validation : "+id);
            if (id == -1 && !actionForm.equals("createOne")) // la 2eme condition car createOne ne nécessite pas de récupérer d'id vu qu'il sera auto-généré à l'insert
            {
                LOG.debug("Form submmitted an hidden id input cannot be parsed into an int type variable \nRedirecting to /user"); // POST devient GET via sendRedirect
                request.getSession(true).setAttribute("redirectErrorMessage","Aucun id n'a été récupéré");
                response.sendRedirect(request.getContextPath()+"/role");
            }
            else
            {
                // instancier l 'EntityManager em et les differents services dans les methodes englobant les instructions de chaque case
                switch (actionForm) {

                    case "editOne_getForm":
                        LOG.debug("User attempts to get the user editing form");
                        this.editOneUser_getForm(request,response,id);
                        // la méthode doit envoyer vers la userEdit.jsp avec des inputs auto completés par les valeurs du user récupéré
                        // ici on a pas de model intermédiaire, on voit pq c'est interessant d'en faire afin d'avoir un objet qui correspond bien aux inputs du formulaire sans les champs inutiles
                        // c le meme cheminement que pour le userDetails.jsp donc le case readOne sauf que la vue sera un formulaire avec input pré-rempli et pas une table
                        break;


                    case "readOne":
                        LOG.debug("User attempts to get the details of an user");
                        this.readOneUser(request,response,id);
                        break;


                    case "createOne" :
                        LOG.debug("User attempts to create a new user");
                        this.createOneUser(request,response);
                        break;


                    case "editOne":
                        LOG.debug("User attempts to edit an user");
                        //this.editOneUser(request,response,id);
                        break;


                    case "deleteOne":
                        LOG.debug("User attempts to delete an user");
                        this.deleteOneUser(request,response,id);
                        break;


                    default:
                        request.getSession(true).setAttribute("redirectErrorMessage","L'action sollicitée par le bouton n'est pas encore traitable\nRedirecting to /user");
                        response.sendRedirect(request.getContextPath()+"/user");

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
    // Lister tous les users
    private void readAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();

        // Instanciation du service adapté
        UserService myUserService = new UserService(em);

        // Récuperation de la liste des users en db
        List<UserEntity> myUserList = myUserService.selectAllOrNull();
        if (myUserList != null) {
            // mise en request attribute de la liste (  la valeur des attributs sont des Object.class  ; la valeur des paramètres sont des String.class )
            request.setAttribute("myUserListRequestKey", myUserList);
            request.setAttribute("pageTitle","User list");
            MyLogUtil.exitServlet(this, new Exception());
            request.getRequestDispatcher(USER_LIST_VIEW).forward(request, response);
        } else {
            LOG.debug("Could not get the user list");
            request.setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer la liste d'utilisateurs");
            MyLogUtil.exitMethod(this, new Exception());
            request.getRequestDispatcher(USER_LIST_VIEW).forward(request, response); // mais affichera que l error message via un if dans la .jsp
        }
    }













    // switch GET
    // Envoyer le formulaire de création d'un user
    private void createOneUser_getForm (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterMethod(this, new Exception());

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();
        // Instanciation du service adapté pour récupérer la liste de roles afin de les afficher dans un input select multiple ( si n à n) sinon select unique (si n à 1 donc 1 role per user)
        RoleService myRoleService = new RoleService(em);

        // Récuperation de la liste des roles en contexte
        List<RoleEntity> myRoleList = myRoleService.selectAllOrNull();
        if (myRoleList != null) {
            // mise en session attribute de la liste (  la valeur des attributs sont des Object.class  ; la valeur des paramètres sont des String.class )
            request.getSession().setAttribute("myRoleListForSelectInputSessionKey", myRoleList);
            request.setAttribute("pageTitle","User Creation");
            MyLogUtil.exitMethod(this, new Exception());
            request.getRequestDispatcher(USER_CREATE_VIEW).forward(request, response);
        }
        else
        {
            LOG.debug("Could not get the role list");
            // on retourne vers la page de la liste en affichant un message d'erreur  => faire un if  errorMessage n est pas empty => afficher le msg  else afficher la table
            request.setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer le formulaire de création d'utilisateur");
            MyLogUtil.exitServlet(this, new Exception());
            request.getRequestDispatcher(USER_CREATE_VIEW).forward(request, response); // mais affichera que l error message via un if dans la .jsp  et else = le formulaire
        }
    }




    ////
    // switch POST
    ////





    // switch POST
    // Afficher les détails d'un seul user
    private void readOneUser(HttpServletRequest request, HttpServletResponse response, int idUser) throws ServletException, IOException {

        MyLogUtil.enterMethod(this,new Exception());

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();

        // Instanciation du service adapté
        UserService myUserService = new UserService(em);

        // Recupéreration du role ayant cet id en db
        UserEntity returnedUser = myUserService.selectOneByIdOrNull(idUser);
        if (returnedUser != null) {
            request.setAttribute("myUserRequestKey", returnedUser);
            MyLogUtil.exitMethod(this, new Exception());
            request.getRequestDispatcher(USER_DETAILS_VIEW).forward(request, response);
        }
        else {
            LOG.debug("No existing user with id = " + idUser + "\n Redirecting to /user");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer l'user en vue d'en afficher ses détails");
            MyLogUtil.exitMethod(this, new Exception());
            response.sendRedirect(request.getContextPath()+"/user");
        }
    }











    // switch POST
    // Créer un nouveau utilisateur
    private void createOneUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Ici on a le cas d'une relation 1-n entre user et role, donc un seul role par user. Dans le cas d'une relation n-n, la logique du code est la même que permissionServlet.createOnePermission()
        // Si n à n, l'input serait un select multiple donc on récupère les values idrole dans une list via request.getParameterValues qui retourne un tableu de String
        // Si n à 1 , l'input est un select donc on récupère un seul idrole via request.getParameter
        // Le code de la transaction est différent en fonction du type de relationaussi vu que dans le cas du 1-n, il n y a pas d'Entity qui corresponde à la table intermédiaire (UserRoleEntity n'existe pas dans notre cas)

        MyLogUtil.enterMethod(this, new Exception());

        Integer selectedRoleId= -1;

        UserEntity validatedUser = UserValidation.toCreateUser(request, selectedRoleId);

        if (validatedUser==null ||  selectedRoleId!= -1 || selectedRoleId==null){ // il faut d'office un role sinon on ne crée pas l'user
            LOG.debug("Object failed validations");
            request.getSession(true).setAttribute("redirectErrorMessage", " Les infos users ont échoué aux validations");
        }
        else {
            LOG.debug("Object successed validations");

            EntityManager em = EMF.getEM();
            EntityTransaction et = null;  // nécessaire de le faire avant le try pour pouvoir y accéder dans le finally, sinon la variable 'et' serait locale au try

            try {

                et = em.getTransaction();
                et.begin();

                // Instanciation du service adapté
                UserService myUserService = new UserService(em);

                LOG.debug("validatedPermission avant insert flush : "+validatedUser.toString()); // retourne 0
                myUserService.insertAndFlush(validatedUser); // le flush fait que l'objet envoyé pour être attaché pointe directement vers l'objet attaché une fois la méthode terminée, alors que sans , il continue de pointer vers l'objet détaché sans id actualisé par le generation.TYPE
                LOG.debug("validatedPermission après insert flush : "+validatedUser.toString()); // retourne 0 sans le flush, retourne les infos de l'objet attaché avec le flush


                // Instanciation du service pour l'objet RoleEntity correspondant à l'idRole que l'on a
                RoleService myRoleService = new RoleService(em);

                RoleEntity myRole = myRoleService.selectOneByIdOrNull(selectedRoleId);
                if (myRole !=null) {
                    LOG.debug("IF : correspondance trouvée : selectedRoleId = " + selectedRoleId + " donne l'entité : " + myRole.toString());

                    // on a un user ainsi qu'un role tous les 2 valides et attachés au context em
                    // on doit rajouter l'objet RoleEntity dans le champ RoleEntity de Userentity, et l'objet UserEntity doit être ajouté dans la collection<UserEntity> de RoleEntity
                    validatedUser.setRolesByIdRole(myRole); // RolesByIdRole n'est pas une collection  mais un champ RoleEntity
                    myRole.getUsersById().add(validatedUser);

                    myUserService.update(validatedUser);
                    myRoleService.update(myRole);
                }

                et.commit();
                request.getSession(true).setAttribute("redirectSuccessMessage", " L'user a bien été créé"); // en session finalement pour l'afficher meme après un sendRedirect

            } catch (Exception e) {
                //LOG.debug(e.getMessage());
                //LOG.debug("\n\n\n\n\n");
                LOG.debug(e);
                request.getSession(true).setAttribute("redirectErrorMessage", " L'user n'a pas été créé");

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
        response.sendRedirect(request.getContextPath()+"/user");
    }















    // switch POST  -- RETOURNE FORMULAIRE
    // FAIRE RoleValidation.toPopulateEditForm(role)
    // Retourner la vue du formulaire d'édition d'un role avec valeurs existantes en db pré complétées
    private void editOneUser_getForm(HttpServletRequest request, HttpServletResponse response, int idUser) throws ServletException, IOException {

        MyLogUtil.enterMethod(this,new Exception());

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();

        // Instanciation du service adapté
        UserService myUserService = new UserService(em);

        // Recupéreration de l'user ayant cet id dans le contexte
        UserEntity attachedUser =  myUserService.selectOneByIdOrNull(idUser);
        if (attachedUser != null) {
            UserVM populatingUser = UserValidation.toPopulateEditForm(attachedUser);
            if(populatingUser != null) {
                request.setAttribute("myUSerVMRequestKey", populatingUser);
                MyLogUtil.exitMethod(this,new Exception());
                request.getRequestDispatcher(USER_EDIT_VIEW).forward(request, response);
                return;
            }
            else {
                MyLogUtil.exitMethod(this,new Exception());
                request.getSession(true).setAttribute("redirectErrorMessage", "L'user récupéré n'a pas pu être transformé pour peupler le formulaire");
            }
        }
        else {
            LOG.debug("No existing user with id = " + idUser + "\n Redirecting to /user");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer l'user dans le contexte en vue de peupler le formulaire d'édition");
        }
            MyLogUtil.exitMethod(this,new Exception());
            response.sendRedirect(request.getContextPath()+"/user");

    }







    /*
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
    */







    // switch POST
    // Supprimer un user (de manière effective)
    private void deleteOneUser(HttpServletRequest request, HttpServletResponse response, int idUser) throws ServletException, IOException {

        MyLogUtil.enterMethod(this,new Exception());

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();

        // Instanciation du service adapté
        UserService myUserService = new UserService(em);

        // Recupéreration du role ayant cet id en db
        UserEntity attachedUser = myUserService.selectOneByIdOrNull(idUser);
        if (attachedUser != null) {

            EntityTransaction et = null;  // nécessaire de le faire avant le try pour pouvoir y accéder dans le finally, sinon la variable 'et' serait locale au try

            try {

                et = em.getTransaction();
                et.begin();

                myUserService.delete(attachedUser);

                et.commit();
                request.getSession(true).setAttribute("redirectSuccessMessage", " L'user "+attachedUser.getUsername()+"  a bien été supprimé"); // en session finalement pour l'afficher meme après un sendRedirect

            } catch (Exception e) {
                LOG.debug(e.getMessage());
                LOG.debug(e);
                request.getSession(true).setAttribute("redirectErrorMessage", " L'user "+attachedUser.getUsername()+"  n'a pas été supprimé");
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
                MyLogUtil.exitMethod(this, new Exception());
                response.sendRedirect(request.getContextPath()+"/user");
            }
        }

        else {
            LOG.debug("No existing user with id = " + idUser + "\n Redirecting to /user");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer l'user en vue de le supprimer");
            MyLogUtil.exitMethod(this, new Exception());
            // On sait pas envoyer vers la methode doGet vu que c est une POST request qu'on a, getRequestDispatcher ne modifie pas la methode de la requete donc ça restera un POST
            // Si on fait un sendRedirect qui permet de modifier un POST en GET, alors on perdre le errorMessage mis en request attribute
            // Donc le mieux c'est d'envoyer vers la page details.jsp malgré qu'on ne les ai pas, faire un if error not empty on l affiche, else on affiche la table de details
            response.sendRedirect(request.getContextPath()+"/user");
        }
    }




}



















