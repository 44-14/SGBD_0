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

@WebServlet(name = "AccountServlet" , urlPatterns = {"/account","/account/*"}, loadOnStartup = -1)
public class AccountServlet extends HttpServlet {


    // LOGGER + PATH CONSTANTS + SERVLET MESSAGES LISTS
    private final static Logger LOG = Logger.getLogger(AccountServlet.class);

    public final static String ACCOUNT_VIEW = AppConfig.ACCOUNT_VIEWS_ROOT_PATH+"account.jsp";




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterServlet(this,new Exception(),request);
        request.getRequestDispatcher(ACCOUNT_VIEW).forward(request,response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyLogUtil.enterServlet(this,new Exception(),request);

        /* if  hidden id du post = id du userConnecté en session  on dispatch vers /user en rajoutant les attributs nécessaires dans la request pour faire editer là bas
         */
    }


}
