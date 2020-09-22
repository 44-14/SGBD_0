package com;


import java.util.HashMap;
import java.util.Map;

public final class AppConfig {

    // tout ce qui est ici sera partagé entre toutes les sessions simultanées etc

    public final static String PUBLIC_VIEWS_ROOT_PATH = "/public/views";

    public final static String VIEWS_ROOT_PATH ="/WEB-INF/views/";

    public final static String SIGNIN_VIEWS_ROOT_PATH =      VIEWS_ROOT_PATH+"signIn/";
    public final static String SIGNOUT_VIEWS_ROOT_PATH =     VIEWS_ROOT_PATH+"signOut/";
    public final static String SIGNUP_VIEWS_ROOT_PATH =      VIEWS_ROOT_PATH+"signUp/";
    public final static String HOME_VIEWS_ROOT_PATH =        VIEWS_ROOT_PATH+"home/";
    public final static String ACCOUNT_VIEWS_ROOT_PATH =     VIEWS_ROOT_PATH+"account/";
    public final static String PERMISSION_VIEWS_ROOT_PATH =  VIEWS_ROOT_PATH+"permission/";
    public final static String ROLE_VIEWS_ROOT_PATH =        VIEWS_ROOT_PATH+"role/";
    public final static String USER_VIEWS_ROOT_PATH =        VIEWS_ROOT_PATH+"user/";
    public final static String WELCOME_VIEWS_ROOT_PATH =     VIEWS_ROOT_PATH+"welcome/";

    public final static Map<String, String> myFreeAccessWIthoutContextURIList = new HashMap<String,String>();

    static {
        AppConfig.myFreeAccessWIthoutContextURIList.put("SIGNIN_URI_WITHOUT_CONTEXT", "/signin");
        AppConfig.myFreeAccessWIthoutContextURIList.put("SIGNUP_URI_WITHOUT_CONTEXT", "/signup");
        AppConfig.myFreeAccessWIthoutContextURIList.put("RESOURCES_URI_WITHOUT_CONTEXT", "/resources");
        AppConfig.myFreeAccessWIthoutContextURIList.put("PUBLIC_URI_WITHOUT_CONTEXT", "/public");

        /* //AppConfig.myFreeAccessURIList.put("NO_URI_WITHOUT_CONTEXT", "/");
        Correspond à http://localhost:8080/SGBD_0_war_exploded ou http://localhost:8080/SGBD_0_war_exploded/
        Le / est ajouté à la création de la requete automatique, que pour ce cas là car c'est le context de l'application.
        Cela accèdera au welcome-file dans le web.xml , c"est ok si c'est signin en welcome-file
         mais ça ne va pas si c'est home vu qu on accèderait à la homeServlet sans être connecté, ce qu'on ne veut pas dans notre cas
         */

    }

    public AppConfig() {
    }
}

