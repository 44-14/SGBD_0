package com.main.schoolux.utilitaries;


import javax.servlet.http.HttpServletRequest;

public class MyStringUtil {



    // Permet d'extraire ce qui suit le dernier / de l'URL de la requête entrante
    // exemple d'utilisation :    //
    // String action = MyStringUtil.getURL_Action(request);
    // LOG.info("Action de la requête : "+action);
    public static String myGetURL_Action(HttpServletRequest myRequest) {

        String myString = myRequest.getRequestURL().toString();
        myString = myString.substring(myString.lastIndexOf("/")+1);

        return myString;

    }

    public static String myGetURL_Action_Bis(HttpServletRequest myRequest) {

        return myRequest.getRequestURI().substring(myRequest.getContextPath().length());
        // dans le case :
        // if (myActionString.startsWith("/delete")) { do that }

    }







}
