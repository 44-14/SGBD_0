package com.main.schoolux.utilitaries;

import org.apache.log4j.Logger;

import java.util.List;

public class MyStringUtil {

    private static final Logger LOG = Logger.getLogger(MyStringUtil.class);



    /**
     * WARNING: This method is extremely common, and should be in a utility class.
     * (It really should be in the JDK, as a static method of the String class.)
     */
    public static boolean hasContent(String string) {
        LOG.debug("Checking if the string has content : " + string);
        return (string != null && string.trim().length() > 0);
    }






}
