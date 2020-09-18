package com.main.schoolux.listeners;

/* Auto generated imports from intelliJ for weblisteners */
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;


/* imports from example */
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;


@WebListener()
public class MyHTTPRequestListener implements ServletRequestListener {


    private static final Logger LOG = Logger.getLogger( MyHTTPRequestListener.class);


    // Public constructor is required by servlet spec
    public MyHTTPRequestListener() {
    }



    // -------------------------------------------------------
    // ServletRequestListener implementation
    // -------------------------------------------------------
    /* Un écouteur sur l'activité des sessions HTTP */
    @Override
        public void requestInitialized(ServletRequestEvent sre) {
            HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
            //request.setAttribute("startTime", System.currentTimeMillis());

            LOG.log(Level.DEBUG, "=============================================================\n" +
                    "        ===================  REQUEST INITIALIZED  ===================  \n" +
                    "Request Method : " + ((HttpServletRequest) sre.getServletRequest()).getMethod() + "\n" +
                    "Request URL : " + ((HttpServletRequest) sre.getServletRequest()).getRequestURL() + "\n" +
                    "Request URI : " + ((HttpServletRequest) sre.getServletRequest()).getRequestURI());
        }



        @Override
        public void requestDestroyed(ServletRequestEvent sre) {
            HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
            /*long startTime = (Long) request.getAttribute( "startTime" );
            LOG.log( Level.INFO, "Request is produced in {0} milliseconds",System.currentTimeMillis() - startTime );
            */

            LOG.log(Level.DEBUG, "===========================================================================\n" +
                    "                   REQUEST  "+ ((HttpServletRequest) sre.getServletRequest()).getMethod()+" destroyed :  "+
                    ((HttpServletRequest) sre.getServletRequest()).getRequestURI()+
                    "\n        ===========================================================================");

        }

    }





























