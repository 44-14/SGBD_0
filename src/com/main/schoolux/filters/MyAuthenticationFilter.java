package com.main.schoolux.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "MyAuthenticationFilter")
public class MyAuthenticationFilter implements Filter {


    // LOGGER + PATH CONSTANTS
    private final static Logger LOG = Logger.getLogger(MyAuthenticationFilter.class);


    public void destroy() {
    }


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest myHttpRequest = (HttpServletRequest) req;
        HttpServletResponse myHttpResponse = (HttpServletResponse) resp;

        final String SIGNIN_URI = myHttpRequest.getContextPath()+"/signin";

        LOG.debug("================== AUTHENTICATION FILTER ======================= " +
                  "\nFiltered request infos :  Method = "+ myHttpRequest.getMethod() + "      -----------------    Request URI = " + myHttpRequest.getRequestURI());




        // Ressources disponibles sans aucunes contraintes ou conditions d'accès à l'url /resources
        String particularRequest = myHttpRequest.getRequestURI().substring(myHttpRequest.getContextPath().length());

        if (particularRequest.startsWith("/resources") || particularRequest.startsWith("/public")) {
            chain.doFilter(myHttpRequest, myHttpResponse);
            return;
        } else {
            // l'argument false fait que si il n'existe pas de session, il n'en crée pas et donc ça reste à null
            // Si on le met à true, une session est automatiquement crée s'il n'en existe pas
            HttpSession mySession = myHttpRequest.getSession(false);

            boolean isSignedIn = mySession != null && mySession.getAttribute("signedUser") != null;
            boolean isLoginRequest = particularRequest.equals("/signin");

            LOG.info("User signed in : " + isSignedIn);

            if (isLoginRequest || isSignedIn) {
                chain.doFilter(myHttpRequest, myHttpResponse);

            } else {

                myHttpResponse.sendRedirect(SIGNIN_URI);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}







