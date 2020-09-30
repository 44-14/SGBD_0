package com.main.schoolux.utilitaries;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class MyLogUtil {

    private final static Logger LOG = Logger.getLogger(MyLogUtil.class);
    /*
    Pour utiliser le Logger.class :
    LOG.debug("monMessage");
    LOG.log(Level.DEBUG,"monMessage");
     */

    /**
     * Log formatting :  display some request informations
     * @param req
     */

    public static void enterFilter(HttpServletRequest req) {

        LOG.debug("========================== ENTERING AUTHENTICATION FILTER ===============================" +
                "\nFiltered request URI : " + req.getRequestURI() +
                "\nFiltered request Context path : " + req.getContextPath() +
                "\nFiltered request Servlet path : " + req.getServletPath() +
                "\nFiltered request Method : " + req.getMethod());


    }


    /**
     * Log formatting
     */
    public static void exitFilter() {
        LOG.debug("========================== EXITING AUTHENTICATION FILTER ===============================");
    }


    /**
     * Log formatting :  display the current running method and the current running servlet type
     * @param o servlet instance this
     * @param e new exception ()
     * @param
     */
    public static void enterServlet(Object o, Exception e) {


        LOG.debug("========================== ENTERING "+ e.getStackTrace()[0].getMethodName()+" IN "+o.getClass().getSimpleName()+" =============================== ");

        //LOG.debug(o.getClass());
        //LOG.debug( e.getStackTrace()[0].getMethodName());
    }


    /**
     * Log formatting :  display the current running method and the running servlet type + current processed request informations
     * @param o servlet instance this
     * @param e new exception ()
     * @param req
     */
    public static void enterServlet(Object o, Exception e,HttpServletRequest req) {


        LOG.debug("========================== ENTERING "+ e.getStackTrace()[0].getMethodName()+" IN "+o.getClass().getSimpleName()+" =============================== " +
                  "\nRequested Servlet path : " + req.getServletPath() +
                  "\nRequest URI : " + req.getRequestURI());
    }


    /**
     * Log formatting :  display the current running method and the current running servlet type
     * @param o servlet instance this
     * @param e new exception ()
     * @param
     */
    public static void exitServlet(Object o, Exception e) {

        LOG.debug("========================== EXITING "+ e.getStackTrace()[0].getMethodName()+" IN "+o.getClass().getSimpleName()+" =============================== ");

        //LOG.debug(o.getClass());
        //LOG.debug( e.getStackTrace()[0].getMethodName());
    }


    /**
     * Log formatting :  display the current running method and the current running object type
     * @param o instance this
     * @param e new exception ()
     * @param
     */
    public static void enterMethod(Object o, Exception e) {


        LOG.debug("========================== ENTERING "+ e.getStackTrace()[0].getMethodName()+" IN "+o.getClass().getSimpleName()+" =============================== ");

        //LOG.debug(o.getClass());
        //LOG.debug( e.getStackTrace()[0].getMethodName());
    }



    /**
     * Log formatting :  display the current running method and the current running object type
     * @param o servlet instance this
     * @param e new exception ()
     * @param
     */
    public static void exitMethod(Object o, Exception e) {

        LOG.debug("========================== EXITING "+ e.getStackTrace()[0].getMethodName()+" IN "+o.getClass().getSimpleName()+" =============================== ");

        //LOG.debug(o.getClass());
        //LOG.debug( e.getStackTrace()[0].getMethodName());
    }


    /**
     * Removes attribute after checking if it exists in the given scope
     * @param request current processed request
     * @param scope page - request - session
     * @param nameAttribute the attribute to remove if exists
     */

    public static void removeAttribute (HttpServletRequest request, String scope , String nameAttribute) {

        if (nameAttribute != null)
        {

            if (scope.toLowerCase()=="session") {
                HttpSession mySession = request.getSession();
                if (mySession.getAttribute(nameAttribute) != null) {
                    LOG.debug("Removing session attribute : "+nameAttribute);
                    mySession.removeAttribute(nameAttribute);
                }
                else if (scope.toLowerCase()=="request") {
                    if(request.getAttribute(nameAttribute)!=null) {
                        LOG.debug("Removing request attribute : "+nameAttribute);
                        request.removeAttribute(nameAttribute);
                    }
                }
            }
        }
    }







}