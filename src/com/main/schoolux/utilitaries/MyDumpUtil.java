package com.main.schoolux.utilitaries;


import org.apache.log4j.Logger;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


public class MyDumpUtil {

    private final static Logger LOG = Logger.getLogger(MyDumpUtil.class);

    public static void getFullRequestParametersMapDumped(HttpServletRequest req) {

        Map m = req.getParameterMap();
        Set s = m.entrySet();
        Iterator it = s.iterator();

        while (it.hasNext()) {

            Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();

            String key = entry.getKey();
            String[] value = entry.getValue();

            LOG.debug("Key = " + key);
            if (value.length > 1) {
                for (int i = 0; i < value.length; i++) {
                    LOG.debug("Value " + i + " : " + value[i].toString());
                }
            } else
                LOG.debug("Value =" + value[0].toString() + "\n");
        }
    }

    public static void getFullSessionAttributesEnumDumped(HttpServletRequest req) {
        Enumeration keys = req.getSession().getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            LOG.debug(key + ": " + req.getSession().getValue(key)+ "<br>");
        }
    }
}

// Voir jspUtil.jsp pour les dumps dans les .jsp via jstl


// source : https://www.java4s.com/java-servlet-tutorials/example-on-getparametermap-method-of-servlet-request-object/

