package com.main.schoolux.utilitaries;


import javax.servlet.http.HttpServletRequest;

public class MyStringUtil {



    // Permet d'extraire ce qui suit le dernier / de l'URL de la requête entrante
    // exemple d'utilisation :    //
    // String action = MyStringUtil.getURL_Action(request);
    // LOG.info("Action de la requête : "+action);
    public static String getURL_Action(HttpServletRequest myRequest) {

        String myString = myRequest.getRequestURL().toString();
        myString = myString.substring(myString.lastIndexOf("/")+1);

        return myString;


    }







}
