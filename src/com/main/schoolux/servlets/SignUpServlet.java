package com.main.schoolux.servlets;

import com.AppConfig;
import com.main.schoolux.enumerations.Gender;
import com.main.schoolux.services.RoleService;
import com.main.schoolux.services.UserService;
import com.main.schoolux.utilitaries.MyLogUtil;
import com.main.schoolux.utilitaries.MyURLUtil;
import com.main.schoolux.validations.UserValidation;
import com.main.schoolux.validations.UserValidation_Old_0;
import com.persistence.entities.RoleEntity;
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
import java.io.IOException;


// Rappels :
// dans la SignUpServlet, vérifier si deja connecté, ne pas renvoyer le formulaire d'inscription mais un msg disant déjà connecté pour ne pas qu une personne connectée puisse faire une nouvelle inscription
/* le bouton  "s'inscrire" de la page de connexion doit diriger vers ici via /signup */


/* l'attribut loadOnStartup=1 permet de charget la servlet directement au démarrage de l'appli, et pas au moment de la 1ère requête reçue par la servlet) */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/signup/*"}, loadOnStartup = 1)
public class SignUpServlet extends HttpServlet {


    // LOGGER + PATH CONSTANTS
    private static final Logger LOG = Logger.getLogger(SignUpServlet.class);

    public final static String SIGNUP_FORM_VIEW = AppConfig.SIGNUP_VIEWS_ROOT_PATH + "signUpForm.jsp";
    public final static String SIGNUP_CONFIRMATION_VIEW = AppConfig.SIGNUP_VIEWS_ROOT_PATH + "signUpConfirmation.jsp";



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterServlet(this, new Exception(), request);


        String actionURI = MyURLUtil.URI_LastExploitablePart(request);
        LOG.debug("URI action = " + actionURI);

        if (actionURI == null) { actionURI = "signup"; }

        switch (actionURI) {

            case "signup":
                LOG.debug("case signup : user attempts to get the sign up form");
                MyLogUtil.exitServlet(this, new Exception());
                request.getSession(true).setAttribute("genderValues", Gender.values());
                request.getRequestDispatcher(SIGNUP_FORM_VIEW).forward(request, response);
                break;


            case "default":
                LOG.debug("case default : redirection to /signup");
                MyLogUtil.exitServlet(this, new Exception());
                response.sendRedirect(request.getContextPath() + "/signup");
                break;
        }
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterServlet(this, new Exception(), request);

        this.signUp(request, response);

    }


    private void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserEntity myUser = UserValidation.toSignUp(request);
        if (myUser == null) {
            // alors il y a eu des erreurs, celles-ci sont placées dans la HashMap myErrors en session
            request.getRequestDispatcher(SIGNUP_FORM_VIEW).forward(request, response);
        } else {

            EntityManager em = EMF.getEM();
            UserService myUserService = new UserService(em);
            EntityTransaction et = null;

            try {
                et = em.getTransaction();
                et.begin();
                myUserService.insertAndFlush(myUser);

                RoleService myRoleService = new RoleService(em);
                RoleEntity myRoleToUpdate = myRoleService.selectOneByIdOrNull(myUser.getRolesByIdRole().getId());
                myRoleToUpdate.getUsersById().add(myUser);
                myRoleService.update(myRoleToUpdate);

                et.commit();
                request.getSession(true).setAttribute("redirectSuccessMessage", " L'utilisateur a bien été créé");
                request.getRequestDispatcher(SIGNUP_CONFIRMATION_VIEW).forward(request, response);
            } catch (Exception e) {
                //LOG.debug(e.getMessage());
                //LOG.debug("\n\n\n\n\n");
                LOG.debug(e);
                request.getSession(true).setAttribute("redirectErrorMessage", " L'utilisateur n'a pas été créé");

            } finally {
                em.clear();

                LOG.debug("em open ? : " + em.isOpen());
                if (em.isOpen()) {
                    em.close();
                    LOG.debug("em open ? : " + em.isOpen());
                }

                if (et != null && et.isActive()) {
                    et.rollback();
                }
            }

        }
    }
}





