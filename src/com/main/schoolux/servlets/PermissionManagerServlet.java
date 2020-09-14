package com.main.schoolux.servlets;

import com.main.schoolux.services.PermissionServiceToReview;
import com.main.schoolux.utilitaries.MyStringUtil;
import com.persistence.entities.PermissionEntity;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


// CRUD DANS SERVLET - insertNew  selectOne selectAll  updateLogically etc DANS SERVICE


/* une balise dans la barre de navigation d'un user ayant la permission de gerer les permissions doit rediriger vers ici via /permissions
/* l'attribut loadOnStartup permet de charger la servlet directement au démarrage de l'appli, et pas au moment de la 1ère requête reçue) */
@WebServlet(name = "PermissionManagerServlet", urlPatterns = "/permissions/*", loadOnStartup = 1)
public class PermissionManagerServlet extends HttpServlet {


    // LOGGER + PATH CONSTANTS
    private final static Logger LOG = Logger.getLogger(PermissionManagerServlet.class);
    public final String LIST_VIEW = "/WEB-INF/JSP/permissionsList.jsp";
    public final String DETAILS_VIEW = "/WEB-INF/JSP/permissionDetails.jsp";


    private String action = "";


    // CRUD DANS SERVLET - insertNew  selectOne selectAll  updateLogically etc DANS SERVICE





    ////
    // GET
    ////
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.info("===  DoGet() de SignUpServlet -  BEGIN  ===");
        // ou LOG.log(Level.INFO, "========MonMessage======");

        LOG.log(Level.INFO, "Servlet path :" + request.getServletPath().toString());

        // faut vérifier si le type est co sinon dispatch vers servlet signin
        // si l'user est bien co, faut verifier que ca soit un admin pour pouvoir lister les perms
        // ou plutot qu il ait bien la permission permettant de lister les perms, vu que role dynamique on pourrait créer via l'appli les roles admin-2 admin-3 etc
        // et le check sur ces roles n'existeraient pas dans le code, donc ils ne sauraient pas lister les permissions


        /*
        Le retour affiché dans le LOG qui suit a été déplacé au niveau de MyStringUtil afin d'avoir la méthode statique disponible pour toutes les servlets
        LOG.info(request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/")+1));

        Objectif => utiliser un switch avec des cases correspondant à la fin de l'url afin de déterminer l'action
        L'urlPattern de la servlet PermissionManagerServlet contient bien une étoile à savoir /permissions/*
        Tout ce qui contient nomAppli/permissions/<xyz>  sera redirigé vers cette servlet permissionManagerServlet
        Et on analysera ce qu'est exactement xyz via un switch qui dispatchera dans un case correspondant  create - read - update - delete avec verif permissions
         */

        action = MyStringUtil.myGetURL_Action(request);
        LOG.info("Action de la requête : " + action);

        switch (action) {
            case "readall":

                List<PermissionEntity> myPermissionsList = this.readAllPermissions();
                request.setAttribute("myPermissionsListRequestKey", myPermissionsList);

                request.getRequestDispatcher(LIST_VIEW).forward(request, response);
                break;


            case "readone":
                int id=3;
                PermissionEntity myPermission = this.readOnePermission(id);
                request.setAttribute("myPermissionRequestKey", myPermission);

                request.getRequestDispatcher(DETAILS_VIEW).forward(request, response);
                break;



            default:
                String test = request.getServletPath();
                LOG.info(test);
                request.getRequestDispatcher(request.getServletPath()+"/readall").forward(request,response);
        }



    }








    ////
    // POST
    ////
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.info("===  doPost() de SignUpServlet -  BEGIN  ===");


        String usernameSession = request.getParameter("usernameFromForm");
        String passwordSession = request.getParameter("passwordFromForm");

        HttpSession session = request.getSession(true);
        session.setAttribute("usernameSessionKey", usernameSession);
        session.setAttribute("passwordSessionKey", passwordSession);


        if (usernameSession.equals("Bond") && passwordSession.equals("007")) {
            session.setAttribute("isLoggedIn", true);
            request.getRequestDispatcher("/public/JSP/signInConfirmation.jsp").forward(request, response);
        } else {
            session.setAttribute("isLoggedIn", false);
            request.getRequestDispatcher("/public/JSP/signInForm.jsp").forward(request, response);
        }


    }














    ////
    // METHODES DU SWITCH
    ////

    // Pour lister toutes les permissions
    private List<PermissionEntity> readAllPermissions() {


        // pas besoin dans le cas d'un read = find
        // EntityManager em = EMF.getEM();

        // Liste conteneur => finalement on fait directement un return du retour de la méthode read.all
        // List<PermissionEntity> myPermissionsList = null;

        // Instanciation du service adapté
        PermissionServiceToReview myPermissionService = new PermissionServiceToReview();

        // le try, le finally et le close est géré dans la méthode findByNamedQuery de l'EntityFinderImpl instancié dans la méthode appelée du service
        // pas de transaction pour une lecture en db donc pour read - find - select
        //try {

            // Recupére les records permissions présents dans la base de données
            return myPermissionService.selectAllPermissions();

        //} finally {
           // em.clear();
           // em.close();
        //}


    }





    // Pour afficher les détails d'une seule permission
    private PermissionEntity readOnePermission(int idPermission) {


        // pas besoin dans le cas d'un read = find
        // EntityManager em = EMF.getEM();

        // Instanciation du service adapté
        PermissionServiceToReview myPermissionService = new PermissionServiceToReview();
        // le try, le finally et le close est géré dans la méthode findOne de l'EntityFinderImpl instancié dans la méthode appelée du service
        // pas de transaction pour une lecture en db donc pour read - find - select
        //try {
        //try {

            // Recupére le record permission ciblé présent dans la base de données
            return myPermissionService.selectOnePermissionById(idPermission);

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