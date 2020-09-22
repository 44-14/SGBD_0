package com.main.schoolux.utilitaries;

import org.apache.log4j.Logger;


public class MyStringUtil {

    private static final Logger LOG = Logger.getLogger(MyStringUtil.class);




    /*
    WARNING: This method is extremely common, and should be in a utility class.
    (It really should be in the JDK, as a static method of the String class.)
     */

    /**
     * Vérifie si il y a un contenu existant dans la String
     * @param string la chaîne à vérifier
     * @return true si la string n'est pas null ni chaîne vide
     */

    public static boolean hasContent(String string) {
        LOG.debug("Checking if the string has content : " + string);
        return (string != null && string.trim().length() > 0);
    }


    public static boolean hasContent(Character myChar) {
        LOG.debug("Checking if the character has content : " + myChar);
        return (myChar != null);
    }


    /**
     *
     * @param myString la chaîne à découper
     * @param myChar  le caractère séparateur
     * @return  ce qui suit le caractère séparateur ou null
     */
    public static String AfterLastOccurenceOf_OrNull(String myString, Character myChar) {

        if (MyStringUtil.hasContent(myString) && MyStringUtil.hasContent(myChar))
        {
            return myString.substring(myString.lastIndexOf(myChar) + 1);
        }
        return null;
    }




}
