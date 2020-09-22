package com.main.schoolux.utilitaries;


import javax.crypto.spec.DESedeKeySpec;
import javax.servlet.http.HttpServletRequest;

public class MyURLUtil {




    // NE PAS UTILISER => PRENDRE URI_LastExploitablePart
    // Permet de récupérer la string qui suit le dernier slash présent de l'URI
    // Exemple d'URI : http://localhost:8080/SGBD_0_war_exploded/signout/abc
    // Retour de la méthode : abc
    // retourne chaine vide si l'url se termine par un /
    public static String URI_AfterLastSlash(HttpServletRequest myRequest) {

        String URI = myRequest.getRequestURI();
        return MyStringUtil.AfterLastOccurenceOf_OrNull( URI ,'/');

        /*Fonctionnait mais pas si l uri finissait par un slash  + on a factorisé pour tout caractère et pas juste le slash
         URI.substring(URI.lastIndexOf("/")+1);
        return myString;
         */
    };


    /**
     * Retourne la dernière partie exploitable de l'URI afin de l'utiliser dans le switch
     * Si l'URI finit par un / alors la méthode retourne ce qui suivait le / précédent
     * Si l'URI ne finit pas par un / alors la méthode retourne ce qui suivait le dernier /
     * @param myRequest la requête traitée par la servlet
     * @return une partie d'URI exploitable pour un switch ou null
     */

    public static String URI_LastExploitablePart(HttpServletRequest myRequest) {

        String URI = myRequest.getRequestURI();

        String scindedURI = MyStringUtil.AfterLastOccurenceOf_OrNull( URI ,'/');

        if (MyStringUtil.hasContent(scindedURI)){
            return scindedURI;
        }
        else {
            // Retire le dernier slash qui finit l'uri et fait qu'on recupère une chaîne vide
            // https://openclassrooms.com/forum/sujet/l-inverse-de-substring
           scindedURI=URI.substring(0, URI.lastIndexOf('/'));
           scindedURI = MyStringUtil.AfterLastOccurenceOf_OrNull( scindedURI ,'/');
           if (MyStringUtil.hasContent(scindedURI)){
                return scindedURI;
            }
           return null;
        }
    }



    // Permet de récupérer ce qui suit le contexte dans l'URI
    // Exemple d'URI : /SGBD_0_war_exploded/signout/abc
    // Retour de la méthode : /signout/abc
    public static String URI_WithoutContext(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }







}
