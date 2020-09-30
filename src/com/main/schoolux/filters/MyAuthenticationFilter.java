package com.main.schoolux.filters;

import com.AppConfig;
import com.main.schoolux.utilitaries.MyLogUtil;
import com.main.schoolux.utilitaries.MyURLUtil;
import com.persistence.entities.UserEntity;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


@WebFilter(filterName = "MyAuthenticationFilter", urlPatterns = "/*")
public class MyAuthenticationFilter implements Filter {


    // LOGGER + PATH CONSTANTS +  REQUEST COUNTER
    private final static Logger LOG = Logger.getLogger(MyAuthenticationFilter.class);
    public static int requestCounter = 0; // initialisé à 0 par défaut


    public void init(FilterConfig config) throws ServletException {

        LOG.debug("Filter class : " + this.getClass() +
                "\n Class name : " + this.getClass().getName()
                + "\nFilter instance toString : " + this.toString());
    }

    public void destroy() {
    }


    public void doFilter(ServletRequest receivedReq, ServletResponse receivedResp, FilterChain chain) throws ServletException, IOException {

        /* CAST DES PARAMETRES car la méthode doFilter() de l'interface implémentée Filter
        requiert ServletRequest et ServletResponse en paramètres */
        HttpServletRequest request = (HttpServletRequest) receivedReq;
        HttpServletResponse response = (HttpServletResponse) receivedResp;

        MyLogUtil.enterFilter(request);

        try {


            // stocke /SGBD_0_war_exploded/signin dans la hashmap
            if (requestCounter == 0) {
                AppConfig.myFreeAccessWIthoutContextURIList.put("CONTEXT_PATH", request.getContextPath());
                AppConfig.myFreeAccessWIthoutContextURIList.put("SIGNIN_URI", request.getContextPath() + "/signin");
            }

            LOG.info("Number of processed requests : " + ++requestCounter);

            // Récupérer tout ce qui suit le contexte /SGBD_0_war_exploded/ dans l'URI
            String scindedURI = MyURLUtil.URI_WithoutContext(request);
            LOG.debug("URI scindée sans contexte : " + scindedURI);


            // Vérification que la partie scindée de l'URI ne matche pas avec le début des URI publiquement accessibles stockées dans la Hashmap myFreeAccessWIthoutContextURIList
            // à savoir /signin - /signup  -  /resources - /public
            for (Map.Entry<String, String> myItem : AppConfig.myFreeAccessWIthoutContextURIList.entrySet()) {
                if (scindedURI.startsWith(myItem.getValue())) {
                    //LOG.debug("Inside IF structure in FOREACH loop");
                    LOG.debug("Filter transmitting the request targeting a FREE ACCESS WITHOUT CONTEXT URI");
                    MyLogUtil.exitFilter();
                    chain.doFilter(request, response);

                    return;
                }
            }


            HttpSession mySession = request.getSession(false);
            boolean isSignedIn = mySession != null && mySession.getAttribute("signedUser") != null;
            LOG.info("User signed in : " + isSignedIn);

            if (isSignedIn) {

                LOG.debug("User signed in ==> Filter transmitting the request to servlet path  " + request.getServletPath());
                MyLogUtil.exitFilter();
                chain.doFilter(request, response);
            } else {
                LOG.debug("No signed in user ==> Filter redirecting client-side to /signin");
                MyLogUtil.exitFilter();
                response.sendRedirect(AppConfig.myFreeAccessWIthoutContextURIList.get("SIGNIN_URI"));

                /*
                 Remarque : si on utilise ici sendRedirect(la signin uri), alors il faut impérativement avoir une partie précédente du filtre qui vérifie si cette uri est reçue
                 sinon on aura une boucle infinie avec le filtre qui sendRedirect en permanence vers la signin uri
                 */
            }

        } catch (Exception e) {

            LOG.debug("Dans le catch de mon filtre : Executing action failed." + e.getStackTrace().toString());
            request.getSession().setAttribute("redirectErrorMessage", "Filtre global : Error message => " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/home");


        }


    }
}












