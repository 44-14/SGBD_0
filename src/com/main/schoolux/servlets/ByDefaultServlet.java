package com.main.schoolux.servlets;

import com.AppConfig;
import com.main.schoolux.utilitaries.MyLogUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* Voir : https://docs.roguewave.com/en/hydraexpress/4.6.0/html/rwsfservletug/4-4.html
   Tout en bas de la source pour la raison de la servlet   => en gros ne jamais avoir d'erreur pour des url qui ne matchaient aucun pattern traité
   Attention : avec le pattern "/", les requetes GET des .css et .js étaient aussi traitées ici et les fichiers n'étaient plus accédés (site blanc et noir sans css)
   Il a fallu rajouter dans le web.xml :

       <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.js</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.css</url-pattern>
    </servlet-mapping>

    il existe donc une servlet nommée "default" que l'on ne voit pas dans les files ici et qui permet gérer les .css et .js
    C'etait aussi elle qui récupérait les url ne matchant aucun pattern traité, et réalisait un print stack trace dans le navigateur pour afficher l'erreur
 */

@WebServlet(name = "ByDefaultServlet", urlPatterns = {"/"} , loadOnStartup = -1)
public class ByDefaultServlet extends HttpServlet {

    // LOGGER + PATH CONSTANTS + SERVLET MESSAGES LISTS
    private final static Logger LOG = Logger.getLogger(ByDefaultServlet.class);

    public final static String BYDEFAULT_VIEW = AppConfig.BYDEFAULT_VIEWS_ROOT_PATH+"byDefault.jsp";




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyLogUtil.enterServlet(this,new Exception(),request);

        request.getRequestDispatcher(BYDEFAULT_VIEW).forward(request, response);
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyLogUtil.enterServlet(this,new Exception(),request);
        this.doGet(request,response);
    }
}
