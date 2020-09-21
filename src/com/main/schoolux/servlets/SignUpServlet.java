package com.main.schoolux.servlets;

import com.AppConfig;
import com.main.schoolux.utilitaries.MyUrlUtil;
import com.main.schoolux.validations.UserValidation_Old_0;
import com.persistence.entities.UserEntity;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// Rappels :
// dans la SignUpServlet, vérifier si deja connecté, ne pas renvoyer le formulaire d'inscription mais un msg disant déjà connecté pour ne pas qu une personne connectée puisse faire une nouvelle inscription
/* le bouton  "s'inscrire" de la page de connexion doit diriger vers ici via /signup */


/* l'attribut loadOnStartup=1 permet de charget la servlet directement au démarrage de l'appli, et pas au moment de la 1ère requête reçue par la servlet) */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/signup", "/signup/*"}, loadOnStartup = 1)
public class SignUpServlet extends HttpServlet {





    // LOGGER + PATH CONSTANTS
    private static final Logger LOG = Logger.getLogger(SignUpServlet.class);

    public final static String SIGNUP_FORM_VIEW = AppConfig.SIGNUP_VIEWS_ROOT_PATH+"signUpForm.jsp";
    public final static String SIGNUP_CONFIRMATION_VIEW= AppConfig.SIGNUP_VIEWS_ROOT_PATH+"signUpConfirmation.jsp";


    public final String SIGNUP_URI_WITHOUT_CONTEXT = "/signup";
    public String SIGNUP_URI;




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.info("===  DoGet() de SignUpServlet -  BEGIN  ===");
        // ou LOG.log(Level.INFO, "========MonMessage======");
        LOG.log(Level.INFO, "Servlet path :"+request.getServletPath().toString());


        this.SIGNUP_URI = request.getContextPath() + this.SIGNUP_URI_WITHOUT_CONTEXT; // construit /SGBD_0_war_exploded + /signup


        String exploitableURI = MyUrlUtil.URL_FromFirstExploitableSlash(request);

        if (!exploitableURI.equals(this.SIGNUP_URI_WITHOUT_CONTEXT))
        {
            response.sendRedirect(this.SIGNUP_URI);
        }
        else {
            // créer en mm tps la session
            request.getRequestDispatcher(SIGNUP_FORM_VIEW).forward(request, response);
        }
    }




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.info("===  doPost() de SignUpServlet -  BEGIN  ===");

        // Mettre en statique le UserValidation pour pas instancier ?
        UserValidation_Old_0 myValidation = new UserValidation_Old_0();
        UserEntity myUser = myValidation.UserValidation_Create(request);

        //UserEntity myUser = UserValidation.ToSignUp();

        if (myUser==null) {
            {
            // alors il y a eu des erreurs, celles-ci sont placées dans la HashMap myErrors en session
            request.getRequestDispatcher(SIGNUP_FORM_VIEW).forward(request, response);
            }
        }
        else {
            request.getRequestDispatcher(SIGNUP_CONFIRMATION_VIEW).forward(request,response);
        }


/*

        if myUser == null
                alors erreur >1
                    dispatch vue form en refilant la request qui possede la mise en session des erreurs et des valeurs ok
        else
            alors on a un userEntity clean, go appeler l entity manager + service pour le persister en verifiant les unique key

*/

        /*

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
        */

    }
}



