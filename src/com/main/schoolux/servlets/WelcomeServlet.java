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

// Voir : https://docs.roguewave.com/en/hydraexpress/4.6.0/html/rwsfservletug/4-4.html
// Tout en bas pour la raison de cette servlet
@WebServlet(name = "WelcomeServlet", urlPatterns = {"/"}, loadOnStartup = -1)
public class WelcomeServlet extends HttpServlet {

    // LOGGER + PATH CONSTANTS + SERVLET MESSAGES LISTS
    private final static Logger LOG = Logger.getLogger(AccountServlet.class);

    public final static String WELCOME_VIEW = AppConfig.WELCOME_VIEWS_ROOT_PATH+"welcome.jsp";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyLogUtil.enterServlet(this,new Exception(),request);
        request.getRequestDispatcher(WELCOME_VIEW).forward(request,response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyLogUtil.enterServlet(this,new Exception(),request);
        this.doGet(request,response);
    }
}
