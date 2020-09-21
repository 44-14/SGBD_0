package com.main.schoolux.utilitaries;


import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class MyTrackingUtil {

    private final static Logger LOG = Logger.getLogger(MyTrackingUtil.class);





    public static void TrackMethodEntry(Object o) {

        LOG.debug(o.getClass());
        //LOG.debug(this.getClass().getEnclosingMethod().getName(); // -> null pointer
        //LOG.debug(this.getClass().getEnclosingMethod().toString()); //-> null pointer
        //LOG.debug( new Exception().getStackTrace()[0].getMethodName()); // fonctionne pas car retournera TrackMethodEntry
        // la surcharge avec Exception e en paramètre fonctionne
    }



    public static void TrackMethodEntry(Object o,Exception e) {

        LOG.debug(o.getClass());
        //LOG.debug(this.getClass().getEnclosingMethod().getName(); // -> null pointer
        //LOG.debug(this.getClass().getEnclosingMethod().toString()); //-> null pointer
        LOG.debug( e.getStackTrace()[0].getMethodName());

        // exemple d'appel : début du doGet() d'une servlet
        // myTrackingUtil.TrackMethodEntry(this, new Exception());
        // this = l'object duquel la methode doGet est invoquee donc la servlet
        // l exception permet de chopper la current running method

        // remarque :
        // quand on instancie un objet dans les parenthèses arguments d'un appel à methode
        // la methode en cours est celle dans laquelle on fait l'appel à cette méthode
        // et pas la methode appelée à laquelle on va passer le paramètre

        // donc dans la methode doGet
        // LOG.debug( new Exception().getStackTrace()[0].getMethodName());
        // retournera bien doGet et pas debug comme MethodName

        //https://www.geeksforgeeks.org/get-name-of-current-method-being-executed-in-java/
    }

    public static void TracMethodEntry_LogFormatted(Object o, Exception e) {

        LOG.debug(o.getClass());
        LOG.debug( e.getStackTrace()[0].getMethodName());
    }


}

// Voir jspUtil.jsp pour les dumps dans les .jsp via jstl


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