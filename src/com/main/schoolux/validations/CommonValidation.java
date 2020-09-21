package com.main.schoolux.validations;

import com.main.schoolux.utilitaries.MyStringUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class CommonValidation {
    private static final Logger LOG = Logger.getLogger(CommonValidation.class);

    private CommonValidation() {
    }



    public static void checkEmpty_Input(String input, String inputLabel, Map<String,String> errors, Map <String,String> valids)  {

        if (!MyStringUtil.hasContent(input)) {
            errors.put(inputLabel+"Error", "Ce champ est requis");
        } else {
            valids.put(inputLabel+"Valid", input);
        }
        return;
    }


    public static void checkEmptyAndLength_Input(String input, String inputLabel,int minLength, int maxLength, Map<String,String> errors, Map <String,String> valids )  {

        if (!MyStringUtil.hasContent(input)) {
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


    /**
     * Returns -1 if the string can't be parsed into an int.
     */
    // pour les input, faire une methode checkIdInput qui call celle-ci et si recupere -1,  met un msg dans la hashmap
    public static int checkValid_Id(String input) {

        int id = -1;

        try {
            id = Integer.parseInt(input);
            }
        catch (NumberFormatException e) {
            LOG.debug("id : "+e.getMessage());
            //e.getMessage();
            //errors.add(e.getMessage());
            }
        return id;
    }



/*
    public static void checkIdAttribute(String input, String inputLabel,int minLength, int maxLength, Map<String,String> errors, Map <String,String> valids ) {
        int id = -1;
        try {
            id = Integer.parseInt(request.getParameter("idPermissionFromForm"));
        } catch (NumberFormatException e) {
            //e.getMessage();
            PermissionManagerServletMessages.add(e.getMessage());
        }
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
