package com.main.schoolux.servlets;

import com.AppConfig;
import com.main.schoolux.services.PermissionService;
import com.main.schoolux.services.PermissionServiceToReview;
import com.main.schoolux.services.UserService;
import com.main.schoolux.utilitaries.MyStringUtil;
import com.main.schoolux.utilitaries.MyUrlUtil;
import com.main.schoolux.validations.CommonValidation;
import com.main.schoolux.validations.UserValidation;
import com.persistence.entities.PermissionEntity;
import com.persistence.entities.RolePermissionEntity;
import com.persistence.entities.UserEntity;
import com.persistence.entityFinderImplementation.EMF;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// Rappels :
// CRUD DANS SERVLET -  insert - selectOne / selectAll - update / updateLogically - delete etc DANS SERVICE
// une balise dans la barre de navigation d'un user ayant la permission de gerer les permissions doit rediriger vers ici via /permissions
// vérifier que l'utilisateur mis en session via la signInServlet a bien accès à telle permission pour gérer les permissions et les lister etc
// c'est bien les permissions qu'il faut directement vérifier et pas le role de l user vu que les roles sont dynamiques, il se peut que certains roles n'existent pas encore ou qu on modifie les permissions liées etc
// on peut le faire en debut de servlet ou dans le case (mieux) avec msg d erreur
// ou dans la jsp view meme ?
// un user connecté qui a la perm viewAllPerm les verra
// mais seul un user connecté qui a la perm EditPerm verra le bouton d edition
// cependant les permissions sont statiques donc c plutot pour les roles cette reflexion
// la seule view dispo des permissions sera celle de la liste et il faudra la permission ViewAllPErmissions

/* l'attribut loadOnStartup=1 permet de charget la servlet directement au démarrage de l'appli, et pas au moment de la 1ère requête reçue par la servlet) */
@WebServlet(name = "PermissionManagerServlet", urlPatterns = {"/permission","/permission/*"}, loadOnStartup = -1)
public class PermissionManagerServlet extends HttpServlet {


    // LOGGER + PATH CONSTANTS + SERVLET MESSAGES LISTS
    private final static Logger LOG = Logger.getLogger(PermissionManagerServlet.class);

    public final static String PERMISSION_LIST_VIEW = AppConfig.PERMISSION_VIEWS_ROOT_PATH+"permissionList.jsp";
    public final static String PERMISSION_DETAILS_VIEW = AppConfig.PERMISSION_VIEWS_ROOT_PATH+"permissionDetails.jsp";

    private List<String> PermissionManagerServletMessages;
    private List<String> ServletSuccessMessages;



    //////
    // INIT
    //////
    @Override
    public void init() throws ServletException {
        super.init();
        this.PermissionManagerServletMessages = new ArrayList<>();
        this.ServletSuccessMessages = new ArrayList<>();
    }



    //////
    //  GET
    //////
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.debug("======  doGet() in PermissionManagerServlet  ======");
        // ou LOG.log(Level.DEBUG, "========MonMessage======");

        // urlPatterns de la servlet traitant la requete
        LOG.debug("Servlet Path :"+request.getServletPath());
        LOG.debug("Request URI :"+ request.getRequestURI());


        String actionURL = MyUrlUtil.URL_AfterLastSlash(request);
        LOG.debug("URL Action : " + actionURL);

        switch (actionURL) {
            case "readall":

                try{
                    this.readAllPermissions(request,response);
                }
                catch( Exception e)
                {
                    LOG.debug("Exception message : "+e.getMessage());
                }
                break;

             /*
            case "FuturTest" :
                break;
              */

            default:
                // request.getRequestDispatcher(X) va modifier la request et rajouter X après le context, mais les parameters et attributs de la requete restent préservés
                // tandis que response.sendRedirect(une URI) va détruire la requete en cours et forcer le client à en faire une nouvelle GET, les attributs et paramètres de l'ancienne requête seront perdus
                // Si la requete etait une POST, ca deviendra une GET
                request.getRequestDispatcher(request.getServletPath()+"/readall").forward(request,response);
        }



    }


    //////
    //  POST
    //////
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.debug("======  doPost() in PermissionManagerServlet  ======");

        // urlPatterns de la servlet traitant la requete
        LOG.debug("Servlet Path :" + request.getServletPath());
        LOG.debug("Request URI :" + request.getRequestURI());

        // Reset des messages de la servlet
        PermissionManagerServletMessages.clear();


        String actionForm = request.getParameter("actionFromForm");
        LOG.debug("Form requested action : " + actionForm);

        if (!MyStringUtil.hasContent(actionForm))
        //if (actionForm==null || actionForm.isEmpty())
        {
            LOG.debug("The requested action is null or empty \nRedirecting to /permission");
            PermissionManagerServletMessages.add("Aucune action du formulaire n'a été récupérée");
        }
        else {

            LOG.debug("Form request action : "+actionForm);

            int id = CommonValidation.checkValid_Id(request.getParameter("idPermissionFromForm"));
            if (id == -1)
            {
                PermissionManagerServletMessages.add("Pas d'id reçu");
            }
            else
            {
                EntityManager em = EMF.getEM();
                PermissionService myPermissionService = new PermissionService(em);

                switch (actionForm) {

                    case "readOne":

                        PermissionEntity returnedPermission = myPermissionService.selectOneByIdOrNull(id);
                        if (returnedPermission == null) {
                            LOG.debug("No existing permission with id = " + id);
                            PermissionManagerServletMessages.add("Le système n'a pas su récupérer de permission ayant l'id = " + id);
                        } else {
                            request.setAttribute("myPermissionRequestKey", returnedPermission);
                            request.getRequestDispatcher(PERMISSION_DETAILS_VIEW).forward(request, response);
                        }
                        break;

                    case "editOne":
                        // Pas dans le cahier des charges
                        //response.sendRedirect(request.getRequestURI());
                        //break;
                    case "deleteOne":
                        // Pas dans le cahier des charges
                        //response.sendRedirect(request.getRequestURI());
                        //break;

                    default:
                        PermissionManagerServletMessages.add("L'action sollicitée n'est pas encore traitable");

                }
            }
        }

        // on met les messages en session parce qu on va faire un sendRedirect qui détruit la request POST pour faire une GET mais on veut récupérer les messages une fois dans le doGet()
        request.getSession().setAttribute("PermissionManagerServletMessagesSessionKey", PermissionManagerServletMessages);
        // on fait un sendRedirect pour passer d'une methode POST à une GET et ainsi passer dans la doGet() methode de PermissionManagerServlet
        response.sendRedirect(request.getRequestURI());

    }




    //////               //////
    //  METHODES DES SWITCH  //
    //////               //////

    // Pour lister toutes les permissions
    private List<PermissionEntity> readAllPermissions_Old() {
        // Pas besoin d'em en paramètre ici car la methode selectAllOrNull() du service utilise EntityFinderImpl.class instanciant son propre em
        // De plus, le try, le finally et le em.close() sont déjà dans la méthode findByNamedQuery de EntityFinderImpl.class
        // De plus, pas de transaction pour une lecture en db donc pas nécessaire pour les read/find/select

        // Instanciation du service adapté
        PermissionService myPermissionService = new PermissionService();

        // Recupére et retourne la liste des permissions présentes dans la base de données
        return myPermissionService.selectAllOrNull();
    }


    private void readAllPermissions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Pas besoin d'em en paramètre ici car la methode selectAllOrNull() du service utilise EntityFinderImpl.class instanciant son propre em
        // De plus, le try, le finally et le em.close() sont déjà dans la méthode findByNamedQuery de EntityFinderImpl.class
        // De plus, pas de transaction pour une lecture en db donc pas nécessaire pour les read/find/select
        // Instanciation du service adapté
        PermissionService myPermissionService = new PermissionService();
        List<PermissionEntity> myPermissionsList = myPermissionService.selectAllOrNull();
        request.setAttribute("myPermissionsListRequestKey", myPermissionsList);
        request.getRequestDispatcher(PERMISSION_LIST_VIEW).forward(request, response);

    }












    // Pour afficher les détails d'une seule permission
    private PermissionEntity readOnePermission(HttpServletRequest request, HttpServletResponse response) {

 int idPermission = 3;
        // em nécessaire car selectOneByIdOrNull n'instancie pas son propre em cette méthode n'est pas dans EntityFinderImpl.class
        EntityManager em = EMF.getEM();
        // Instanciation du service adapté
        //PermissionService myPermissionService = new PermissionService();
        PermissionService myPermissionService = new PermissionService(em);

        // Recupére et retourne le record permission ciblé ayant cet id dans la base de données
        return myPermissionService.selectOneByIdOrNull(idPermission);

        //} finally {
            //em.clear();
            //em.close();
        //}


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