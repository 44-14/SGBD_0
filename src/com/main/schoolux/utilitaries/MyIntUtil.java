package com.main.schoolux.utilitaries;

import org.apache.log4j.Logger;

public class MyIntUtil {

    private final static Logger LOG = Logger.getLogger(MyURLUtil.class);



    // retourne la default value passée en paramètre si impossible de parser la string, mettre -1 par ex et tester le retour
    public static int myTryParseInt(String myString, int defaultValue) {

        try {
            return Integer.parseInt(myString);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // overloading
    public int myTryParseInt(String myString) {
        return myTryParseInt(myString,-1);
    }



}




