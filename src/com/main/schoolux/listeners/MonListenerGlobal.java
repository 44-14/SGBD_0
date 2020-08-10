package com.main.schoolux.listeners;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class MonListenerGlobal implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {



    private static final Logger LOG = Logger.getLogger( MonListenerGlobal.class);
    private int sessionCounter = 0;


    // Public constructor is required by servlet spec
    public MonListenerGlobal() {
    }





    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    /*Un écouteur sur l'activité de l'application Web */

    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        LOG.log( Level.INFO, "======  School UX -- Application started ====== " );
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        LOG.log( Level.INFO, "======  School UX -- Application stopped ====== " );
    }







    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    /* Un écouteur sur l'activité des sessions HTTP */

    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */

        synchronized ( this ) {
            sessionCounter ++;
        }
        LOG.log( Level.INFO,"======= Session created - "+sessionCounter+" session(s) in memory ======");
    }




    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */

        synchronized ( this ) {
            sessionCounter --;
        }
        LOG.log( Level.INFO,"======= Session destroyed - "+sessionCounter+" session(s) in memory ======");

    }





    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

     /*
    Method Summary
    java.lang.String	getName()
    Returns the name with which the attribute is bound to or unbound from the session.
            HttpSession	getSession()
    Return the session that changed.
            java.lang.Object	getValue()
    Returns the value of the attribute that has been added, removed or replaced.

    Source : https://tomcat.apache.org/tomcat-5.5-doc/servletapi/javax/servlet/http/HttpSessionBindingEvent.html
    */


    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
        LOG.log(Level.INFO,
                "====== Attribut ajouté en session : \nNom de l'attribut: "+sbe.getName()+"\nValeur: "+sbe.getValue()+"\nSession: "+sbe.getSession()+"");

    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
        LOG.log(Level.INFO,
                "====== Attribut supprimé en session : \nNom de l'attribut: "+sbe.getName()+"\nValeur: "+sbe.getValue()+"\nSession: "+sbe.getSession()+"");

    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attribute
         is replaced in a session.
      */
        LOG.log(Level.INFO,
                "====== Attribut remplacé en session : \nNom de l'attribut: "+sbe.getName()+"\nValeur: "+sbe.getValue()+"\nSession: "+sbe.getSession()+"");

    }
}
