package com.main.schoolux.utilitaries;

import com.sun.deploy.security.SelectableSecurityManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
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

    public static boolean hasContent(Character myChar) {
        LOG.debug("Checking if the string has content : " + myChar);
        return (myChar != null);
    }



    public static String AfterLastSlash(String myString) {


        if (MyStringUtil.hasContent(myString))
        {
            return myString.substring(myString.lastIndexOf("/") + 1);
        }
        else
        {
            return null;
        }
    }

    public static String AfterLastOccurenceOf_OrNull(String myString, Character myChar) {

        if (MyStringUtil.hasContent(myString) && MyStringUtil.hasContent(myChar))
        {
            myString =  myString.substring(myString.lastIndexOf(myChar) + 1);
            LOG.debug("Ma String returned" + myString);
            return myString;
        }
        else
        {
            return null;
        }
    }







}
