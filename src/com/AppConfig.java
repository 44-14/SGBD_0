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

    public final static Map<String, String> myFreeAccessURIList = new HashMap<String,String>();

    static {
        AppConfig.myFreeAccessURIList.put("SIGNIN_URI_WITHOUT_CONTEXT", "/signin");
        AppConfig.myFreeAccessURIList.put("SIGNUP_URI_WITHOUT_CONTEXT", "/signup");
        AppConfig.myFreeAccessURIList.put("RESOURCES_URI_WITHOUT_CONTEXT", "/resources");
        AppConfig.myFreeAccessURIList.put("PUBLIC_URI_WITHOUT_CONTEXT", "/public");

    }

    public static int requestCounter=0;


    public AppConfig() {
    }
}

