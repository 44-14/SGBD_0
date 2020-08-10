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
@WebServlet(name = "AuthentificationServlet", urlPatterns = "/auth", loadOnStartup = 1)
public class AuthentificationServlet extends HttpServlet {


    private final static Logger LOG = Logger.getLogger(AuthentificationServlet.class);


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.info("=================  DoGet() de AuthentificationServlet -  BEGIN  ======================");
        /*le même que LOG.log(Level.INFO, "========MonMessage======");*/

        String usernameSession = request.getParameter("usernameFromForm");
        String passwordSession = request.getParameter("passwordFromForm");

        if (usernameSession == null) usernameSession = "";
        if (passwordSession == null) passwordSession = "";

        HttpSession session = request.getSession(true); /* le paramètre booléen mis à true permet de générer la session si elle n'existe pas, on évite ainsi un null) */
        session.setAttribute("usernameSessionKey", usernameSession);
        session.setAttribute("passwordSessionKey", passwordSession);

        request.getRequestDispatcher("/public/JSP/authentification.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.info("==================  doPost() de AuthentificationServlet -  BEGIN  ===================");


        String usernameSession = request.getParameter("usernameFromForm");
        String passwordSession = request.getParameter("passwordFromForm");

        HttpSession session = request.getSession(true);
        session.setAttribute("usernameSessionKey", usernameSession);
        session.setAttribute("passwordSessionKey", passwordSession);


        if (usernameSession.equals("Bond") && passwordSession.equals("007")) {
            session.setAttribute("isLoggedIn", true);
            request.getRequestDispatcher("/public/JSP/LoggedIn.jsp").forward(request, response);
        } else {
            session.setAttribute("isLoggedIn", false);
            request.getRequestDispatcher("/public/JSP/authentification.jsp").forward(request, response);
        }


    }
}

