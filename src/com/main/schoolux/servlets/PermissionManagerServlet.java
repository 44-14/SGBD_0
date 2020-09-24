package com.main.schoolux.servlets;

import com.AppConfig;
import com.main.schoolux.services.PermissionService;
import com.main.schoolux.services.RoleService;
import com.main.schoolux.utilitaries.MyLogUtil;
import com.main.schoolux.utilitaries.MyStringUtil;
import com.main.schoolux.utilitaries.MyURLUtil;
import com.main.schoolux.validations.CommonValidation;
import com.main.schoolux.validations.PermissionValidation;
import com.main.schoolux.validations.RoleValidation;
import com.persistence.entities.PermissionEntity;
import com.persistence.entities.RoleEntity;
import com.persistence.entityFinderImplementation.EMF;
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


// Rappels :
// CRUD DANS SERVLET -  insert - selectOne / selectAll - update / updateLogically - delete etc DANS SERVICE
// une balise dans la barre de navigation d'un user ayant la permission de gerer les permissions doit rediriger vers ici via /permissions
// vérifier que l'utilisateur mis en session via la signInServlet a bien accès à telle permission pour gérer les permissions et les lister etc
// verification de la presence dans la permission dans la jsp pour afficher les boutons dispo , mais aussi dans la servlet pour eviter les hacks
// donc dans la servlet, si signedUser.role.permissions .containts (code permission) alors tu process le readAll
// c'est bien les permissions qu'il faut directement vérifier et pas le role de l user vu que les roles sont dynamiques, il se peut que certains roles n'existent pas encore ou qu on modifie les permissions liées etc
// on peut le faire en debut de servlet ou dans le case (mieux) avec msg d erreur
// ou dans la jsp view meme ?
// un user connecté qui a la perm viewAllPerm les verra
// mais seul un user connecté qui a la perm EditPerm verra le bouton d edition
// cependant les permissions sont statiques donc c plutot pour les roles cette reflexion
// la seule view dispo des permissions sera celle de la liste et il faudra la permission ViewAllPErmissions




/* l'attribut loadOnStartup=1 permet de charget la servlet directement au démarrage de l'appli, et pas au moment de la 1ère requête reçue par la servlet */
@WebServlet(name = "PermissionManagerServlet", urlPatterns = "/permission/*", loadOnStartup = -1)
public class PermissionManagerServlet extends HttpServlet {

    // LOGGER + PATH CONSTANTS + SERVLET MESSAGES LISTS
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

        if (actionURI == null) {actionURI="permission";}

        switch (actionURI) {

            case "permission": // same as readall case
            case "readAll":
                LOG.debug("Case readAll : user attempts to get the permission list view");
                this.readAllPermissions(request, response);

                break;

            case "createOne_getForm":
                LOG.debug("User attempts to get the create permission form view");
                this.createOnePermission_getForm(request,response);

                break;

            default:
                LOG.debug("Entering default case");
                //throw new IllegalStateException("Unexpected value: " + actionURI);
                MyLogUtil.exitServlet(this, new Exception());
                request.getRequestDispatcher(request.getServletPath()).forward(request, response);
                /* send the request to /permission ?  La methode reste la meme, si c'etait post ça restera un post vers /permission
                 ==> For a RequestDispatcher obtained via getRequestDispatcher(), the ServletRequest object has
                 its path elements and parameters adjusted to match the path of the target resource.
                 donc la request perd sa cible de base donc l uri ? à vérif
                 */

                // request.getRequestDispatcher(X) va modifier la request et rajouter X après le context comme ça reste au sein du meme contexte, mais les parameters et attributs de la requete restent préservés ainsi que la méthode
                // tandis que response.sendRedirect(une URI) va détruire la requete en cours et forcer le client à en faire une nouvelle GET, les attributs et paramètres de l'ancienne requête seront perdus
                // dans le cas du sendRedirect(une URI) il faut bien mettre une URI complète car on pourrait amener le client à requeter vers une autre application donc un autre contexte
                // pour faire l'URI :   request.getContextPath()+"/urlPattern géré par l'appli"
                // Si la requete etait une POST, ca deviendra une GET
        }
    }



    ////////
    //  POST
    ////////
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterServlet(this,new Exception(),request);


        String actionForm = request.getParameter("actionFromForm"); // "actionFromForm" = button name attribute in .jsp
        LOG.debug("Form button action : " + actionForm);

        if (!MyStringUtil.hasContent(actionForm))
        //if (actionForm==null || actionForm.isEmpty())
        {
            LOG.debug("The form button action is null or empty \nRedirecting to /permission"); // POST devient GET via sendRedirect
            request.getSession(true).setAttribute("redirectErrorMessage","Aucune action du formulaire n'a été récupérée");
        }
        else {
            LOG.debug("Form button action : "+actionForm);

            int id = CommonValidation.checkValid_Id(request.getParameter("idPermissionFromForm"));
            if (id == -1)
            {
                LOG.debug("Form submmitted hidden id input cannot be parsed into an int type variable\nRedirecting to /permission");
                request.getSession(true).setAttribute("redirectErrorMessage","Aucun id n'a été récupéré");
            }
            else
            {
                LOG.debug("id parse : "+id);
                // instancier l 'EntityManager em et les differents services dans les methodes remplacant chaque case
                switch (actionForm) {

                    case "editOne_getForm":
                        LOG.debug("User attempts to get the permission editing form");
                        this.editOnePermission_getForm(request,response,id);
                        // la méthode doit envoyer vers la permissionEdit.jsp avec des inputs auto completés par les valeurs du role récupéré
                        // ici on a pas de model intermédiaire, on voit pq c'est interessant d'en faire afin d'avoir un objet qui correspond bien aux inputs du formulaire sans les champs inutiles
                        // c le meme que pour le roleDetails.jsp donc le case readOne sauf que la vue sera un formulaire avec input pré-rempli et pas une table

                        break;

                    case "readOne":
                        LOG.debug("User attempts to get the details of one permission");
                        this.readOnePermission(request,response,id);
                        break;

                    case "createOne" :
                        LOG.debug("User attempts to create a new permission");
                        this.createOnePermission(request,response);
                        break;

                    case "editOne":
                        LOG.debug("User attempts to edit a permission");
                        this.editOnePermission(request,response,id);
                        break;

                    case "deleteOne":
                        LOG.debug("User attempts to delete a permission");
                        this.deleteOnePermission(request,response,id);
                        break;

                    default:
                        request.getSession(true).setAttribute("postErrorMessage","L'action sollicitée par le bouton n'est pas encore traitable\nRedirecting to /permission");
                        response.sendRedirect("/permission");

                }
            }
        }
    }



















    //////               //////
    //  METHODES DES SWITCH  //         // celles de doPost nécessite un sendRedirect en cas d'erreur pour passer d'une methode POST à une methde GET, celles de doGet peuvent retourner vers leur servlet via RequestDispatcher
    //////               //////         // celles de doGet  peuvent retourner vers leur servlet via RequestDispatcher ou la doGet d'une autre servlet




    // switch GET
    // OK
    // Lister toutes les permissions
    private void readAllPermissions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // PLUS MTN
        // Pas besoin d'instancier un EntityManager ici car la methode selectAllOrNull() du service utilise EntityFinderImpl.class instancie son propre EntityManager em
        // PLUS MTN
        // De plus, le try, le finally et le em.close() sont déjà dans la méthode findByNamedQuery de EntityFinderImpl.class et on en fait pas d'autres
        // De plus, pas de transaction pour une lecture en db donc pas nécessaire pour les read/find/select

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
            request.setAttribute("dispatchErrorMessage", "Le service n'a pas su récupérer la liste de permissions");
            MyLogUtil.exitServlet(this, new Exception());
            request.getRequestDispatcher(PERMISSION_LIST_VIEW).forward(request, response); // mais affichera que l error message via un if dans la .jsp
        }
    }






    // switch GET
    // OK
    // Envoyer le formulaire de création d'une permission
    private void createOnePermission_getForm (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyLogUtil.exitServlet(this, new Exception());
        request.getRequestDispatcher(PERMISSION_CREATE_VIEW).forward(request, response);
    }






    // switch POST
    // OK
    // Afficher les détails d'une seule permission
    private void readOnePermission(HttpServletRequest request, HttpServletResponse response, int idPermission) throws ServletException, IOException {

        // PLUS MTN
        // Pas besoin d'instancier un EntityManager ici car la methode selectOneByIdOrNull() du service utilise EntityFinderImpl.class instancie son propre EntityManager em
        // PLUS MTN
        // De plus, le try, le finally et le em.close() sont déjà dans la méthode findByNamedQuery de EntityFinderImpl.class et on en fait pas d'autres
        // De plus, pas de transaction pour une lecture en db donc pas nécessaire pour les read/find/select

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();
        // Instanciation du service adapté
        PermissionService myPermissionService = new PermissionService(em);

        // Recupéreration de la permission ayant cet id en db
        PermissionEntity returnedPermission = myPermissionService.selectOneByIdOrNull(idPermission);
        if (returnedPermission != null) {
            request.setAttribute("myPermissionRequestKey", returnedPermission);
            MyLogUtil.exitServlet(this, new Exception());
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






    // switch POST
    // PermissionValidation.toCreatePermission() à faire
    // Créer une nouvelle permission
    private void createOnePermission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // faire les validations ? donc faudrait peut-être pas recevoir le role en paramètre ici vu qu on va le créer via la request en passant par la validation


        PermissionEntity validatedPermission = PermissionValidation.toCreatePermission(request);
        // serait le retour de la validation  // validation method statique ou pas ?
        // sachant que chaque session appelle la meme instance de servlet qui fait un multi threading sur les doGet doPost, je pense que statique = ok vu que tout se passe dans la methode, la classe validation n a aucun champ privé qui serait propre à chaque instance en particulier

        EntityManager em = EMF.getEM();

        // Instanciation du service adapté
        PermissionService myPermissionService = new PermissionService(em);

        EntityTransaction et = null;  // nécessaire de le faire avant le try pour pouvoir y accéder dans le finally, sinon la variable 'et' serait locale au try

        try {

            et = em.getTransaction();
            et.begin();

            myPermissionService.insert(validatedPermission);

            et.commit();
            request.getSession(true).setAttribute("redirectSuccessMessage", " La permission a bien été créé"); // en session finalement pour l'afficher meme après un sendRedirect

        } catch (Exception e) {
            LOG.debug(e.getMessage());
            LOG.debug(e);
            request.getSession(true).setAttribute("redirectErrorMessage", " La permission n'a pas été créé");
        } finally {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            MyLogUtil.exitServlet(this, new Exception());
            response.sendRedirect("/permission");
        }
    }




    // switch POST  -- RETOURNE FORMULAIRE
    // FAIRE PermissionValidation.toPopulateEditForm(Permission)
    // Retourner la vue du formulaire d'édition d'une permission avec valeurs existantes en db pré complétées
    private void editOnePermission_getForm(HttpServletRequest request, HttpServletResponse response, int idPermission) throws ServletException, IOException {

        // PLUS MTN
        // Pas besoin d'instancier un EntityManager ici car la methode selectOneByIdOrNull() du service utilise EntityFinderImpl.class instancie son propre EntityManager em
        // PLUS MTN
        // De plus, le try, le finally et le em.close() sont déjà dans la méthode findByNamedQuery de EntityFinderImpl.class et on en fait pas d'autres
        // De plus, pas de transaction pour une lecture en db donc pas nécessaire pour les read/find/select

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();
        // Instanciation du service adapté
        PermissionService myPermissionService = new PermissionService(em);

        // Recupéreration de la permission ayant cet id en db
        PermissionEntity returnedPermission = myPermissionService.selectOneByIdOrNull(idPermission);
        if (returnedPermission != null) {
            PermissionEntity populatingPermission = PermissionValidation.toPopulateEditForm(returnedPermission);
            if(populatingPermission != null) {
                request.setAttribute("myPermissionRequestKey", populatingPermission);
                MyLogUtil.exitServlet(this, new Exception());
                request.getRequestDispatcher(PERMISSION_EDIT_VIEW).forward(request, response);
            }
            else {
                request.getSession(true).setAttribute("redirectErrorMessage", "La permission récupérée n'a pas pu être transformé en populating permission");
            }
        }
        else {
            LOG.debug("No existing permission with id = " + idPermission + "\n Redirecting to /permission");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer la permission en vue de peupler le formulaire d'édition");
            MyLogUtil.exitServlet(this, new Exception());
            // On sait pas envoyer vers la methode doGet vu que c est une POST request qu'on a, getRequestDispatcher ne modifie pas la methode de la requete donc ça restera un POST
            // Si on fait un sendRedirect qui permet de modifier un POST en GET, alors on perdre le errorMessage mis en request attribute
            // Donc le mieux c'est d'envoyer vers la page details.jsp malgré qu'on ne les ai pas, faire un if error not empty on l affiche, else on affiche la table de details
            response.sendRedirect("/permission");
        }
    }



    // switch POST
    // Editer un permission
    private void editOnePermission(HttpServletRequest request, HttpServletResponse response, int idPermission) throws ServletException, IOException {

        // Pas besoin d'instancier un EntityManager ici car la methode selectOneByIdOrNull() du service utilise EntityFinderImpl.class instancie son propre EntityManager em
        // De plus, le try, le finally et le em.close() sont déjà dans la méthode findByNamedQuery de EntityFinderImpl.class et on en fait pas d'autres
        // De plus, pas de transaction pour une lecture en db donc pas nécessaire pour les read/find/select

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();
        // Instanciation du service adapté
        PermissionService myPermissionService = new PermissionService(em);

        // Recupéreration du role ayant cet id en db
        PermissionEntity returnedPermission = myPermissionService.selectOneByIdOrNull(idPermission);
        if (returnedPermission != null) {
            request.setAttribute("myPermissionRequestKey", returnedPermission);
            MyLogUtil.exitServlet(this, new Exception());
            request.getRequestDispatcher(PERMISSION_EDIT_VIEW).forward(request, response);
        }
        else {
            LOG.debug("No existing permission with id = " + idPermission + "\n Redirecting to /permission");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer la permission en vue de l'éditer en db");
            MyLogUtil.exitServlet(this, new Exception());
            // On sait pas envoyer vers la methode doGet vu que c est une POST request qu'on a, getRequestDispatcher ne modifie pas la methode de la requete donc ça restera un POST
            // Si on fait un sendRedirect qui permet de modifier un POST en GET, alors on perdre le errorMessage mis en request attribute
            // Donc le mieux c'est d'envoyer vers la page details.jsp malgré qu'on ne les ai pas, faire un if error not empty on l affiche, else on affiche la table de details
            response.sendRedirect("/role");
        }
    }



    // switch POST
    // fonctionne
    // Supprimer une permission (de manière effective)
    private void deleteOnePermission(HttpServletRequest request, HttpServletResponse response, int idPermission) throws ServletException, IOException {

        // Pas besoin d'instancier un EntityManager ici car la methode selectOneByIdOrNull() du service utilise EntityFinderImpl.class instancie son propre EntityManager em
        // MTN SI
        // De plus, le try, le finally et le em.close() sont déjà dans la méthode findByNamedQuery de EntityFinderImpl.class et on en fait pas d'autres
        // MTN NON
        // De plus, pas de transaction pour une lecture en db donc pas nécessaire pour les read/find/select -> seulement si on fait qu'une lecture, mais une lecture suivie d'un remove = besoin que les 2 requetes utilisent un seul et meme entityManager, et qu'il n y ait pas de em.close() entre elles

        // Instanciation de l'EntityManager context:
        EntityManager em = EMF.getEM();
        // Instanciation du service adapté
        PermissionService myPermissionService = new PermissionService(em);

        // Recupéreration de la permssion ayant cet id en db
        PermissionEntity returnedPermission = myPermissionService.selectOneByIdOrNull(idPermission);
        if (returnedPermission != null) {

            // PLUS MAINTENANT
            //myPermissionService.setEm(em); // car le service qu'on a instancié avant utilisait EntityImpl qui génère son propre em et le close , mais pour le delete on passera par ServiceImpl.class auquel il faut fourni l'em

            EntityTransaction et = null;  // nécessaire de le faire avant le try pour pouvoir y accéder dans le finally, sinon la variable 'et' serait locale au try

            try {
                LOG.debug("begin try ");
                et = em.getTransaction();
                et.begin();
                myPermissionService.delete(returnedPermission);
                et.commit();
                request.getSession(true).setAttribute("redirectSuccessMessage", " La permission a bien été supprimée"); // en session finalement pour l'afficher meme après un sendRedirect
                LOG.debug("end try");
            } catch (Exception e) {
                LOG.debug(e.getMessage());
                LOG.debug(e);
                request.getSession(true).setAttribute("redirectErrorMessage", " La permission n'a pas été supprimée");
            } finally {
                if (et != null && et.isActive()) {
                    et.rollback();
                }
                MyLogUtil.exitServlet(this, new Exception());
                response.sendRedirect(request.getContextPath()+"/permission");
            }
        }

        else {
            LOG.debug("No existing permission with id = " + idPermission + "\n Redirecting to /permission");
            request.getSession(true).setAttribute("redirectErrorMessage", "Le service n'a pas su récupérer la permission en vue de la supprimer");
            MyLogUtil.exitServlet(this, new Exception());
            // On sait pas envoyer vers la methode doGet vu que c est une POST request qu'on a, getRequestDispatcher ne modifie pas la methode de la requete donc ça restera un POST
            // Si on fait un sendRedirect qui permet de modifier un POST en GET, alors on perdre le errorMessage mis en request attribute
            // Donc le mieux c'est d'envoyer vers la page details.jsp malgré qu'on ne les ai pas, faire un if error not empty on l affiche, else on affiche la table de details
            response.sendRedirect(request.getContextPath()+"/permission");
        }
    }





}






/*

    // Je r�cup�re les voitures pr�sentes dans la base de donn�e
    List<Voiture> listVoitures = null;

		try {
                VoitureService serviceVoiture = new VoitureService(em); ok
                listVoitures = serviceVoiture.findAllVoitures(); ok
                } finally {
                em.clear();
                em.close();
                }

                // Je r�cup�re toutes les informations concernant les r�servations pr�sentes
                // dans la base de donn�e

                // SCHOOLUX on le fera mais le sens de navigation importe : on veut get les permissions à partir des roles  VOIR EN BAS LE CODE DE JSP-MVC-Master
                // donc on fera un getpermissions(listRoles)
                List<Reservation> listReservations = getAllAboutReservations(listVoitures);


        request.setAttribute("Reservations", listReservations);
        RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/ReservationViews/ReservationList.jsp");
        view.forward(request, response);
        }





      // Les voitures �tant owner au niveau de la relation voiture - r�servation,
	// je parcours la list des voitures disponibles pour acc�der aux informations de
	// leurs r�servation

	private List<Reservation> getAllAboutReservations(List<Voiture> listVoitures) {
		List<Reservation> listRes = new ArrayList<Reservation>();
		List<Voiture> voituresConcernantReservation = null;

		for (Voiture voiture : listVoitures) {
			if (!voiture.getReservations().isEmpty()) {
				voituresConcernantReservation = new ArrayList<Voiture>();
				voituresConcernantReservation.add(voiture);
				for (Reservation reservation : voiture.getReservations()) {
					reservation.setVoitures(voituresConcernantReservation);
					listRes.add(reservation);
				}
			}
		}
		return listRes;
	}



}}

 */