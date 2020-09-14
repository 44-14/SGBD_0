package com.main.schoolux.utilitaries;


import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;



public class DumpUtil {

    private final static Logger LOG = Logger.getLogger(DumpUtil.class);

    public static void getFullRequestMapDumped(HttpServletRequest req) {

        Map m = req.getParameterMap();
        Set s = m.entrySet();
        Iterator it = s.iterator();

        while (it.hasNext()) {

            Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();

            String key = entry.getKey();
            String[] value = entry.getValue();

            LOG.debug("Key = " + key );
            if (value.length > 1) {
                for (int i = 0; i < value.length; i++) {
                    LOG.debug("Value " + i + " : " + value[i].toString() );
                }
            } else
                LOG.debug("Value =" + value[0].toString() + "\n");
        }
    }
}











// source : https://www.java4s.com/java-servlet-tutorials/example-on-getparametermap-method-of-servlet-request-object/
/*
public class getFullRequestMap extends HttpServlet
{
    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
    {
        PrintWriter pw=res.getWriter();
        res.setContentType("text/html");

        Map m=req.getParameterMap();
        Set s = m.entrySet();
        Iterator it = s.iterator();

        while(it.hasNext()){

            Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();

            String key             = entry.getKey();
            String[] value         = entry.getValue();

            pw.println("Key is "+key+"<br>");

            if(value.length>1){
                for (int i = 0; i < value.length; i++) {
                    pw.println("<li>" + value[i].toString() + "</li><br>");
                }
            }else
                pw.println("Value is "+value[0].toString()+"<br>");

            pw.println("-------------------<br>");
        }

        pw.close();
    }
}

*/