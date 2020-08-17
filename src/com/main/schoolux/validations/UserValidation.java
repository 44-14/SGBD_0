package com.main.schoolux.validations;

import com.persistence.entities.UserEntity;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;




public class UserValidation {

    private final static Logger LOG = Logger.getLogger(UserValidation.class);

    public UserEntity createUserValidation (HttpServletRequest request)
    {

        LOG.info("=== Begin - createUserValidation() in UserValidation ===");


        Map<String, String> myResult = new HashMap<String, String>();
        Map<String, String> myErrors = new HashMap<String, String>();

        UserEntity myUser = new UserEntity();

        /*pour le service
        myUser.setActive(true);
        myUser.setInscriptionDate();
        */



        // Recupération des paramètres de la request

        String firstNameRequest = request.getParameter("firstNameFromForm");
        String lastNameRequest = request.getParameter("lastNameFromForm");
        String usernameRequest = request.getParameter("usernameFromForm");
        String passwordRequest = request.getParameter("passwordFromForm");
        String phoneNumberRequest = request.getParameter("phoneNumberFromForm");
        String birthdateRequest = request.getParameter("birthdateFromForm");
        String genderRequest = request.getParameter("genderNameFromForm");
        String emailAddressRequest = request.getParameter("emailAddressFromForm");

        String titleRequest = request.getParameter("titleFromForm");
        String photoRequest = request.getParameter("photoFromForm");
        String roleRequest = request.getParameter("roleFromForm");
        String parentRequest = request.getParameter("parentFromForm");




        String usernameSession = request.getParameter("usernameFromForm");
        String passwordSession = request.getParameter("passwordFromForm");

        return myUser;







    }

}
