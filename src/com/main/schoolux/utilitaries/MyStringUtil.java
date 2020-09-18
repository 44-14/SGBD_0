package com.main.schoolux.utilitaries;


import javax.servlet.http.HttpServletRequest;

public class MyStringUtil {



    // Permet d'extraire ce qui suit le dernier / de l'URL de la requête entrante
    // exemple d'utilisation :    //
    // String action = MyStringUtil.getURL_Action(request);
    // LOG.info("Action de la requête : "+action);
    public static String URL_AfterLastSlash(HttpServletRequest myRequest) {

        String myString = myRequest.getRequestURL().toString();
        myString = myString.substring(myString.lastIndexOf("/")+1);

        return myString;

        // Exemple d'URI : http://localhost:8080/SGBD_0_war_exploded/signout/abc
        // Retour de la méthode : abc

    }

    public static String URL_FromFirstExploitableSlash(HttpServletRequest myRequest) {


        return myRequest.getRequestURI().substring(myRequest.getContextPath().length());

        // Exemple d'URI : /SGBD_0_war_exploded/signout/abc
        // Retour de la méthode : /signout/abc


        // dans le case :
        // if (myActionString.startsWith("/signout")) { do that }

    }







}
