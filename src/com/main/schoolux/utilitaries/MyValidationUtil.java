package com.main.schoolux.utilitaries;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class MyValidationUtil {
    private static final Logger LOG = Logger.getLogger(MyValidationUtil.class);

    private MyValidationUtil() {
    }



    public static void CheckEmpty(String input, String inputLabel, Map<String,String> errors, Map <String,String> valids)  {

        if (!hasContent(input)) {
            errors.put(inputLabel+"Error", "Ce champ est requis");
        } else {
            valids.put(inputLabel+"Valid", input);
        }
        return;
    }


    public static void CheckEmptyAndLength(String input, String inputLabel,int minLength, int maxLength, Map<String,String> errors, Map <String,String> valids )  {

        if (!hasContent(input)) {
            errors.put(inputLabel+"Error", "Ce champ est requis");
        } else {
            if (input.length() < minLength || input.length() > maxLength) {
                errors.put(inputLabel+"Error", "Ce champ requiert entre"+minLength + " et " + maxLength + " caractères.");
            }
            else
            {
                valids.put(inputLabel+"Valid", input);
            }
        }
        return ;
    }










// XL  //

    public static List<String> getErrorMessagesOrEmptyForTextualInput(String input, String inputLabel, int minLength, int maxLength) {
        List<String> errorMessages = new ArrayList<String>();
        if (!hasContent(input)) {
            errorMessages.add(inputLabel + " n'a pas de contenu");
        } else {
            if (input.length() < minLength
                    || input.length() > maxLength) {
                String message = inputLabel + " doit être entre " + minLength + " et " + maxLength + " charactères.";
                errorMessages.add(message);
            }
        }
        return errorMessages;
    }

    /**
     * WARNING: This method is extremely common, and should be in a utility class.
     * (It really should be in the JDK, as a static method of the String class.)
     */
    public static boolean hasContent(String string) {
        LOG.debug("Contenu de la chaîne de caractères fournie : " + string);
        return (string != null && string.trim().length() > 0);
    }

    /**
     * Returns true only if the field passes the test, and is NOT null.
     */
    private boolean ensureNotNull(Object field, String errorMsg, List<String> errors) {
        boolean result = true;
        if (field == null) {
            errors.add(errorMsg);
            result = false;
        }
        return result;
    }
}
