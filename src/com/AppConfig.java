package com;



public final class AppConfig {


    public final static String PUBLIC_VIEWS_ROOT_PATH = "/public/views";
    public final static String VIEWS_ROOT_PATH = "/WEB-INF/views/";

    public final static String HOME_VIEW =AppConfig.VIEWS_ROOT_PATH+"home.jsp";

    public final static String SIGNIN_VIEWS_ROOT_PATH = VIEWS_ROOT_PATH+"signIn/";

    public final static String SIGNOUT_VIEWS_ROOT_PATH = VIEWS_ROOT_PATH+"signOut/";

    public final static String SIGNUP_VIEWS_ROOT_PATH = VIEWS_ROOT_PATH+"signUp/";

    public final static String SIGNIN_URI_WITHOUT_CONTEXT = "/signin";
    public final static String SIGNUP_URI_WITHOUT_CONTEXT = "/signup";




    private AppConfig() {
    }
}

