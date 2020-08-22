package com.main.schoolux.utilitaries;


        import javax.servlet.http.HttpServletRequest;

public class MyBooleanUtil {

    public static boolean booleanFromString(HttpServletRequest myRequest, String attributeToTest) {


        if (myRequest.getSession().getAttribute(attributeToTest) != null) {
            try {
                return (Boolean) myRequest.getSession().getAttribute(attributeToTest);
            }catch (Exception e) {
                e.getMessage();
            }

        }
        return false;
    }
}

