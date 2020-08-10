package com.main.schoolux.servlets;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;





@WebServlet(name = "LoginServlet"  )
public class LoginServlet extends HttpServlet {


    private final static Logger log = Logger.getLogger(LoginServlet.class);






    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }






    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /* Affichage d'un message dans les logs de jounalisation, nécessite l'instanciation de la classe Logger qui se trouve en haut */
        log.info("======================== in doGET LoginServlet ====================================");

        /* precise qu'on renvoie un contenu de type html */
        response.setContentType("text/html");





    }
}
