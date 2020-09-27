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

@WebServlet(name = "HomeServlet", urlPatterns = {"/home","/home/*"}, loadOnStartup = 1)
public class HomeServlet extends HttpServlet {

    // LOGGER + PATH CONSTANTS + SERVLET MESSAGES LISTS
    private final static Logger LOG = Logger.getLogger(HomeServlet.class);

    public final static String HOME_VIEW = AppConfig.HOME_VIEWS_ROOT_PATH+"home.jsp";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterServlet(this,new Exception(),request);
        request.getRequestDispatcher(HOME_VIEW).forward(request,response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doGet(request,response);


    }


}
;