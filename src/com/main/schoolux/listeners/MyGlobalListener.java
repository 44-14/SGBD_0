package com.main.schoolux.listeners;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

@WebListener()
public class MyGlobalListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {



    private static final Logger LOG = Logger.getLogger( MyGlobalListener.class);
    private int sessionCounter = 0;


    // Public constructor is required by servlet spec
    public MyGlobalListener() {
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
        LOG.log( Level.INFO, "\n============================================================\n" +
                                     "============  School UX -- Application started =============\n" +
                                     "============================================================");
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        LOG.log( Level.INFO, "\n============================================================\n" +
                "============  School UX -- Application stopped =============\n" +
                "============================================================");}







    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    /* Un écouteur sur l'activité des sessions HTTP */

    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */

        synchronized ( this ) {
            sessionCounter ++;
        }
        LOG.log( Level.INFO,"******** Session created - "+sessionCounter+" session(s) in memory  ******* ");
    }




    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */

        synchronized ( this ) {
            sessionCounter --;
        }
        LOG.log( Level.INFO,"*******  Session destroyed - "+sessionCounter+" session(s) in memory  ******* ");

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
                "***  New session attribute created : *** \nAttribute key : "+sbe.getName()+"\nAttribute original value : "+sbe.getValue());


                /* on peut rajouter ce qui suit pour avoir l'id de la section :
                +"\nSession: "+sbe.getSession());
                */

    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
        LOG.log(Level.INFO,
                "*** Session attribute deleted : ***  : \nAttribute key "+sbe.getName()+"\nAttribute value : "+sbe.getValue());


    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attribute
         is replaced in a session.
      */
        LOG.log(Level.INFO,
                "***  Session attribute modified en session : ***  \n" +
                        "Attribute key : "+sbe.getName()+"\n" +
                        "Old attribute value : "+sbe.getValue()+"\n" +
                        "New attribute value : "+sbe.getSession().getAttribute(sbe.getName()));




        /* Comme le sbe.getValue() retourne l'ancienne valeur de l'attribut modifié, il fallait trouver comment afficher la nouvelle valeur
        sbe.getSession() retourne un objet de type HttpSession
        Ce retour contient tous les attributs mis dans cette session, et ils sont joignables un par un en utlisant la methode getAttribut(string nomAttribut) qui retourne la value du nomAttribut
        donc sbe.getSession().getAttribute(String nomAttributATrouver);
        Hors, le nom de l'attribut à trouver en fait l'attribut qui a déclenché la méthode ici au vu de sa modification,
        donc on le trouve avec sbe.getName();
        Du coup on a
        sbe.getSession().getAttribute(sbe.getName()) qui nous retournera bien la nouvelle valeur de l'attribut modifié
        Pour l'afficher :
        LOG.log(Level.INFO, "Nouvelle valeur: "+sbe.getSession().getAttribute(sbe.getName())+"");
        Mais on l'a concatené dans le meme LOG.log juste au dessus pour que ça soit groupé dans l'affichage des logs.
        */
    }
}
