package com.main.schoolux.servlets;

import com.main.schoolux.utilitaries.MyStringUtil;
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
@WebServlet(name = "UserManagerServlet", urlPatterns = "/admin/users/*", loadOnStartup = 1)
public class UserManagerServlet extends HttpServlet {


    private final static Logger LOG = Logger.getLogger(UserManagerServlet.class);
    private String action ="";



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.info("===  DoGet() de SignUpServlet -  BEGIN  ===");
        // ou LOG.log(Level.INFO, "========MonMessage======");

        LOG.log(Level.INFO, "Servlet path :"+request.getServletPath().toString());

/*
        String usernameSession = request.getParameter("usernameFromForm");
        String passwordSession = request.getParameter("passwordFromForm");

        if (usernameSession == null) usernameSession = "";
        if (passwordSession == null) passwordSession = "";

        HttpSession session = request.getSession(true); // le paramètre booléen mis à true permet de générer la session si elle n'existe pas, on évite ainsi un null)

        session.setAttribute("usernameSessionKey", usernameSession);
        session.setAttribute("passwordSessionKey", passwordSession);
*/







        /*
         Les  LOG qui suivent ont été déplacé au niveau d'un listener d'evenements auto-générés dans la classe MyHTTPRequestListener,
        LOG.log(Level.INFO, "URI Request :"+request.getRequestURI().toString());
        LOG.log(Level.INFO, "URL Request :"+request.getRequestURL().toString());
         */


        /*
        Le log qui suit a été déplacé au niveau de MyStringUtil afin d'avoir la méthode statique disponible pour toutes les servlets
        LOG.info(request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/")+1));

        Objectif => utiliser un switch avec des cases correspondant à la fin de l'url afin de déterminer l'action
        L'urlPattern de la servlet userServlet contient bien une étoile à savoir /user/*
        Tout ce qui contient nomAppli/user/xyz quoi sera redirigé vers cette servlet userServlet
        Et on analysera ce qu'est exactement xyz via un switch qui dispatchera dans un case correspondant  create - read - update - delete avec verif permissions
         */

        action = MyStringUtil.URL_AfterLastSlash(request);
        LOG.info("Action de la requête : "+action);

        switch (action) {
            case "signup" :
                request.getRequestDispatcher("/public/JSP/signInForm.jsp").forward(request, response);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }







        // créer en mm tps la session
        request.getRequestDispatcher("/public/JSP/signInForm.jsp").forward(request, response);
    }


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
}

