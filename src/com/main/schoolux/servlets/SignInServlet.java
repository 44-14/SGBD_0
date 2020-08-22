package com.main.schoolux.servlets;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;








/* l'attribut loadOnStartup permet de charget la servlet directement au démarrage de l'appli, et pas au moment de la 1ère requête reçue) */
@WebServlet(name = "SignInServlet", urlPatterns = "/signin", loadOnStartup = 1)
public class SignInServlet extends HttpServlet {




    // LOGGER + PATH CONSTANTS
    private final static Logger LOG = Logger.getLogger(SignInServlet.class);
    public final String FORM_VIEW ="/WEB-INF/JSP/signInForm.jsp";
    public final String CONFIRMATION_VIEW = "/WEB-INF/JSP/confirmationSignIn.jsp";
    public final String HOME_VIEW ="/WEB-INF/JSP/home.jsp";






    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.info("===  DoGet() de SignInServlet -  BEGIN  ===");
        // ou LOG.log(Level.INFO, "========MonMessage======");


        // Ecrit le urlPatterns de la servlet traitant la requete
        LOG.log(Level.INFO, "Servlet path :"+request.getServletPath().toString());

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

        if(request.getSession().getAttribute("isLogged") != null){
            boolean isLogged = (boolean) request.getSession().getAttribute("isLogged");
            if (isLogged) {
                request.getRequestDispatcher(HOME_VIEW).forward(request,response);
            }

        }else{
            request.getRequestDispatcher(FORM_VIEW).forward(request, response);
        }


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.info("===  doPost() de SignInServlet -  BEGIN  ===");


        String usernameSession = request.getParameter("usernameFromForm");
        String passwordSession = request.getParameter("passwordFromForm");

        HttpSession session = request.getSession(true);
        session.setAttribute("usernameSessionKey", usernameSession);
        session.setAttribute("passwordSessionKey", passwordSession);



        if (usernameSession.equals("Bond") && passwordSession.equals("007")) {

            session.setAttribute("isLogged",true);

            // à modifier par la homepage ou compte
            request.getRequestDispatcher(CONFIRMATION_VIEW).forward(request, response);
            // par ensuite, rediriger vers la AccountServlet
        } else {
            session.setAttribute("isLogged", false);
            request.getRequestDispatcher(FORM_VIEW).forward(request, response);
        }


    }
}

