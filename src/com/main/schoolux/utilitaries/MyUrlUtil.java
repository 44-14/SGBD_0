package com.main.schoolux.utilitaries;


import javax.servlet.http.HttpServletRequest;

public class MyUrlUtil {





    // Permet de récupérer la string qui suit le dernier slash présent de l'URI
    // Exemple d'URI : http://localhost:8080/SGBD_0_war_exploded/signout/abc
    // Retour de la méthode : abc
    public static String URL_AfterLastSlash(HttpServletRequest myRequest) {

        String myString = myRequest.getRequestURL().toString();
        myString = myString.substring(myString.lastIndexOf("/")+1);

        return myString;



    }



    // Permet de récupérer ce qui suit le contexte dans l'URI
    // Exemple d'URI : /SGBD_0_war_exploded/signout/abc
    // Retour de la méthode : /signout/abc
    public static String URL_FromFirstExploitableSlash(HttpServletRequest myRequest) {

        return myRequest.getRequestURI().substring(myRequest.getContextPath().length());

        // dans le case :
        // if (myActionString.startsWith("/signout")) { do that }

    }







}
