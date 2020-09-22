package com.main.schoolux.utilitaries;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
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

    public static void enterFilter(HttpServletRequest req) {

        LOG.debug("========================== ENTERING AUTHENTICATION FILTER ===============================" +
                "\nFiltered request URI : " + req.getRequestURI() +
                "\nFiltered request Context path : " + req.getContextPath() +
                "\nFiltered request Servlet path : " + req.getServletPath() +
                "\nFiltered request Method : " + req.getMethod());


    }


    public static void exitFilter() {
        LOG.debug("========================== EXITING AUTHENTICATION FILTER ===============================");
    }


    /**
     * Permet d'afficher la méthode et le type de la servlet en cours d'exécution
     * @param o l'instance de la servlet this
     * @param e new exception ()
     */
    public static void enterServlet(Object o, Exception e) {


        LOG.debug("========================== ENTERING "+ e.getStackTrace()[0].getMethodName()+" IN "+o.getClass().getSimpleName()+" =============================== ");

        //LOG.debug(o.getClass());
        //LOG.debug( e.getStackTrace()[0].getMethodName());
    }


    /**
     * Permet d'afficher la méthode et le type de la servlet en cours d'exécution ainsi que des informations sur la requête en cours de traitement
     * @param o l'instance de la servlet this
     * @param e new exception ()
     * @param req la requete reçue par la servlet
     */
    public static void enterServlet(Object o, Exception e,HttpServletRequest req) {


        LOG.debug("========================== ENTERING "+ e.getStackTrace()[0].getMethodName()+" IN "+o.getClass().getSimpleName()+" =============================== " +
                  "\n Requested Servlet path : " + req.getServletPath() +
                  "\n Request URI : " + req.getRequestURI());
    }



    public static void exitServlet(Object o, Exception e) {

        LOG.debug("========================== EXITING "+ e.getStackTrace()[0].getMethodName()+" IN "+o.getClass()+" =============================== ");

        //LOG.debug(o.getClass());
        //LOG.debug( e.getStackTrace()[0].getMethodName());
    }





 /*
    Les 2 methodes ci-dessous sont le chemin vers la création de enterServlet()



    public static void TrackMethodEntry(Object o) {
        LOG.debug(o.getClass());
        //LOG.debug(this.getClass().getEnclosingMethod().getName(); // -> null pointer
        //LOG.debug(this.getClass().getEnclosingMethod().toString()); //-> null pointer
        //LOG.debug( new Exception().getStackTrace()[0].getMethodName()); // fonctionne pas car retournera TrackMethodEntry
        // la surcharge avec Exception e en paramètre fonctionne
    }




    public static void TrackMethodEntry(Object o,Exception e) {

        LOG.debug(o.getClass());
        //LOG.debug(this.getClass().getEnclosingMethod().getName(); // -> null pointer
        //LOG.debug(this.getClass().getEnclosingMethod().toString()); //-> null pointer
        LOG.debug( e.getStackTrace()[0].getMethodName());

        // exemple d'appel : début du doGet() d'une servlet
        // myTrackingUtil.TrackMethodEntry(this, new Exception());
        // this = l'object duquel la methode doGet est invoquee donc la servlet
        // l exception permet de chopper la current running method

        // remarque :
        // quand on instancie un objet dans les parenthèses arguments d'un appel à methode
        // la methode en cours est celle dans laquelle on fait l'appel à cette méthode
        // et pas la methode appelée à laquelle on va passer le paramètre

        // donc dans la methode doGet
        // LOG.debug( new Exception().getStackTrace()[0].getMethodName());
        // retournera bien doGet et pas debug comme MethodName

        //https://www.geeksforgeeks.org/get-name-of-current-method-being-executed-in-java/
    }

  */










}