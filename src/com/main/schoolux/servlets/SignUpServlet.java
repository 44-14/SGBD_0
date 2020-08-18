package com.main.schoolux.servlets;

import com.main.schoolux.validations.UserValidation;
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



/* = la servlet d inscription
rediriger le bouton <s'inscrire> de la signIn.jsp vers ici */


@WebServlet(name = "SignUpServlet", urlPatterns = "/signup", loadOnStartup = 1)
public class SignUpServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SignUpServlet.class);
    public final String VUE_FORM ="/public/JSP/signUp.jsp";
    //public final String VUE_SUCCESS = "/WEB-INF/confirmationSignUp.jsp";



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.info("===  DoGet() de SignUpServlet -  BEGIN  ===");
        // ou LOG.log(Level.INFO, "========MonMessage======");
        LOG.log(Level.INFO, "Servlet path :"+request.getServletPath().toString());


        // créer en mm tps la session
        request.getRequestDispatcher(VUE_FORM).forward(request, response);
    }




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.info("===  doPost() de SignUpServlet -  BEGIN  ===");


        String test = request.getParameter("firstNameFromForm");
        LOG.info(test);
        UserValidation myValidation = new UserValidation();
        UserEntity myUser = myValidation.createValidation(request);


        if (myUser==null) {
            {
            // alors y a eu des erreurs qui sont dans la HashMap myErrors en session
            request.getRequestDispatcher(VUE_FORM).forward(request, response);
            }
        }
        else {
            request.getRequestDispatcher("/public/JSP/confirmationSignUp.jsp").forward(request,response);
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
            request.getRequestDispatcher("/public/JSP/confirmationSignIn.jsp").forward(request, response);
        } else {
            session.setAttribute("isLoggedIn", false);
            request.getRequestDispatcher("/public/JSP/signIn.jsp").forward(request, response);
        }
        */

    }
}



