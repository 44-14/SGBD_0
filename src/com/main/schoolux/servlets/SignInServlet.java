package com.main.schoolux.servlets;

import com.AppConfig;
import com.main.schoolux.services.UserService;
import com.main.schoolux.validations.UserValidation;
import com.persistence.entities.UserEntity;
import com.persistence.entityFinderImplementation.EMF;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/* l'attribut loadOnStartup permet de charget la servlet directement au démarrage de l'appli, et pas au moment de la 1ère requête reçue par la servlet) */
@WebServlet(name = "SignInServlet", urlPatterns = {"/signin","/signout"}, loadOnStartup = 1)
public class SignInServlet extends HttpServlet {


    // LOGGER + PATH CONSTANTS + SERVLET MESSAGES LISTS
    private final static Logger LOG = Logger.getLogger(SignInServlet.class);

    public final static String SIGNIN_FORM_VIEW= AppConfig.SIGNIN_VIEWS_ROOT_PATH+"signInForm.jsp";
    public final static String SIGNIN_CONFIRMATION_VIEW= AppConfig.SIGNIN_VIEWS_ROOT_PATH+"signInConfirmation.jsp";
    public final static String SIGNOUT_CONFIRMATION_VIEW= AppConfig.SIGNOUT_VIEWS_ROOT_PATH+"signOutConfirmation.jsp";



    private List<String> ServletMessages;
    private List<String> ServletSuccessMessages;

    @Override
    public void init() throws ServletException {
        super.init();
        this.ServletMessages = new ArrayList<>();
        this.ServletSuccessMessages = new ArrayList<>();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.debug("======  DoGet() in SignInServlet ======");
        // ou LOG.log(Level.DEBUG, "========MonMessage======");



        // Ecrit le urlPatterns de la servlet traitant la requete
        LOG.debug("Servlet path :"+request.getServletPath().toString());
        String requestURI = request.getRequestURI().toString();
        LOG.debug("Request URI :"+requestURI);


        ServletMessages.clear();

        /*
        //Les  LOG qui suivent ont été déplacé au niveau d'un listener d'evenements auto-générés dans la classe MyHTTPRequestListener,
        LOG.log(Level.INFO, "URI Request :"+request.getRequestURI().toString());
        LOG.log(Level.INFO, "URL Request :"+request.getRequestURL().toString());
         */




        //Structure à faire

        /* faire username + mdp dans le formulaire de co
        on arrive dans la servlet via l action ${pageContext.request.contextPath}/signin
        appeler une methode de validationConnexion qui recup la requete
        recuperer les requestParameters aka mdp et username
        sur base du username, on recupere l objet user lié via service ou l entityfinderImpl je sais pas
        si objet user récupéré = null, alors l username n existe pas
        push dans la hashmap error
        elseon vérifie les password
        si ca renvoie false, erreur password à push dans la hashmap
        else on renvoie l'objet user au controlleur
        qui check si il est null
        si il est pas null, on le met en session
        puis on dispatch vers la homepage en etant connecté

        si diff = return

         */



        /* video
        String usernameSession = request.getParameter("usernameFromForm");
        String passwordSession = request.getParameter("passwordFromForm");
        if (usernameSession == null) usernameSession = "";
        if (passwordSession == null) passwordSession = "";
        HttpSession session = request.getSession(true); // le paramètre booléen mis à true permet de générer la session si elle n'existe pas, on évite ainsi un null)
        session.setAttribute("usernameSessionKey", usernameSession);
        session.setAttribute("passwordSessionKey", passwordSession);
        */


        // plutot faire une methode statique signedInChecker(request) ??



        // Grâce au MyAuthenticationFilter, on est sûr qu'il y a un signedUser dans la session si on atteint l'uri /signout

        if (requestURI.endsWith("/signout")) {
                request.getSession().removeAttribute("signedUser");
                ServletMessages.add("Vous avez été déconnecté");
                request.setAttribute("ServletMessagesRequestKey",ServletMessages);
                request.getRequestDispatcher(SIGNOUT_CONFIRMATION_VIEW).forward(request,response);
        }

        else if (request.getSession().getAttribute("signedUser")!=null) {
            ServletMessages.add("Vous êtes déjà connecté");
            request.setAttribute("ServletMessagesRequestKey",ServletMessages);
            //request.setAttribute("ServletMessage","Vous êtes déjà connecté");
        }

        //DumpUtil.getFullRequestMapDumped(request);
        request.getRequestDispatcher(SIGNIN_FORM_VIEW).forward(request,response);
        //request.getRequestDispatcher("/WEB-INF/views/signin/signInForm.jsp").forward(request,response);


    }











    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.debug("=====  doPost() in SignInServlet  ======");


        // Mettre en statique le UserValidation pour pas instancier
        UserEntity myUserToCheck = UserValidation.ToSignIn(request);

        if (myUserToCheck==null) {
            {
                // alors il y a eu des erreurs, celles-ci sont placées dans la HashMap myErrors en paramètre de requête
                request.getRequestDispatcher(SIGNIN_FORM_VIEW).forward(request, response);
            }
        }
        else {

            EntityManager em = EMF.getEM();
            UserService myUserService = new UserService(em);
            UserEntity returnedUser = myUserService.selectUserByUsernameOrNull(myUserToCheck.getUsername());

            if (returnedUser==null || !(returnedUser.getPassword().equals(myUserToCheck.getPassword())))
            {
                ServletMessages.add("Le couple username/password est incorrect");
                request.setAttribute("ServletMessagesRequestKey",ServletMessages);
                request.getRequestDispatcher(SIGNIN_FORM_VIEW).forward(request,response);
            }

            else {
                request.getSession(false).setAttribute("signedUser", returnedUser);
                request.getRequestDispatcher(SIGNIN_CONFIRMATION_VIEW).forward(request, response);
                // par ensuite, rediriger vers la AccountServlet ou /home  je pense homeServlet via le bouton continuez
            }
        }


    }
}

