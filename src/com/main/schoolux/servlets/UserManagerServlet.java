package com.main.schoolux.servlets;

import com.AppConfig;
import com.main.schoolux.services.RoleService;
import com.main.schoolux.services.UserService;
import com.main.schoolux.utilitaries.MyLogUtil;
import com.main.schoolux.utilitaries.MyURLUtil;
import com.persistence.entities.RoleEntity;
import com.persistence.entities.UserEntity;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/* l'attribut loadOnStartup permet de charget la servlet directement au démarrage de l'appli, et pas au moment de la 1ère requête reçue par le servlet */
@WebServlet(name = "UserManagerServlet", urlPatterns = "/user/*", loadOnStartup = -1)
public class UserManagerServlet extends HttpServlet {

    // LOGGER + PATH CONSTANTS + SERVLET MESSAGES LISTS
    private final static Logger LOG = Logger.getLogger(UserManagerServlet.class);

    public final static String USER_CREATE_VIEW = AppConfig.USER_VIEWS_ROOT_PATH + "userCreate.jsp";
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

        if (actionURI == null) {
            actionURI = "user";
        }

        switch (actionURI) {

            case "user": // same as readall case
            case "readAll":
                LOG.debug("Case readAll : user attempts to get the user list view");
                try {
                    this.readAllUsers(request, response);
                } catch (Exception e) {
                    LOG.debug("Exception message : " + e.getMessage());
                }
                break;

            case "createOne":
                LOG.debug("User attempts to get the create user form view");
                MyLogUtil.exitServlet(this, new Exception());
                request.getRequestDispatcher(USER_CREATE_VIEW).forward(request, response);
                break;

            default:
                LOG.debug("Entering default case");
                //throw new IllegalStateException("Unexpected value: " + actionURI);
                MyLogUtil.exitServlet(this, new Exception());
                request.getRequestDispatcher(request.getServletPath()).forward(request, response);

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

        MyLogUtil.enterServlet(this, new Exception(), request);


    }


    //////               //////
    //  METHODES DES SWITCH  //         // celles de doPost nécessite un sendRedirect en cas d'erreur pour passer d'une methode POST à une methde GET, celles de doGet peuvent retourner vers leur servlet via RequestDispatcher
    //////               //////         // celles de doGet  peuvent retourner vers leur servlet via RequestDispatcher ou la doGet d'une autre servlet


    // switch GET
    // OK
    // Lister tous les users
    private void readAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Pas besoin d'instancier un EntityManager ici car la methode selectAllOrNull() du service utilise EntityFinderImpl.class instancie son propre EntityManager em
        // De plus, le try, le finally et le em.close() sont déjà dans la méthode findByNamedQuery de EntityFinderImpl.class et on en fait pas d'autres
        // De plus, pas de transaction pour une lecture en db donc pas nécessaire pour les read/find/select

        // Instanciation du service adapté
        UserService myUserService = new UserService();

        // Récuperation de la liste des users en db
        List<UserEntity> myUserList = myUserService.selectAllOrNull();
        if (myUserList != null) {
            // mise en request attribute de la liste (  la valeur des attributs sont des Object.class  ; la valeur des paramètres sont des String.class )
            request.setAttribute("myUserListRequestKey", myUserList);
            MyLogUtil.exitServlet(this, new Exception());
            request.getRequestDispatcher(USER_LIST_VIEW).forward(request, response);
        } else {
            LOG.debug("Could not get the user list");
            // on retourne vers la page de la liste en affichant un message d'erreur  => faire un if  errorMessage n est pas empty => afficher le msg  else afficher la table
            request.setAttribute("dispatchErrorMessage", "Le service n'a pas su récupérer la liste d'utilisateurs");
            MyLogUtil.exitServlet(this, new Exception());
            request.getRequestDispatcher(USER_LIST_VIEW).forward(request, response); // mais affichera que l error message via un if dans la .jsp
        }
    }
}

