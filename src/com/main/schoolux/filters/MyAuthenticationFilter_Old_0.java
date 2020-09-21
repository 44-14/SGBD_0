package com.main.schoolux.filters;

import com.AppConfig;
import com.main.schoolux.utilitaries.MyUrlUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "MyAuthenticationFilter")
public class MyAuthenticationFilter_Old_0 implements Filter {



    // LOGGER + PATH CONSTANTS
    private final static Logger LOG = Logger.getLogger(MyAuthenticationFilter_Old_0.class);


    public void init(FilterConfig config) throws ServletException {

        AppConfig.myFreeAccessURIList.put("SIGNIN_URI_WITHOUT_CONTEXT", "/signin");
        AppConfig.myFreeAccessURIList.put("SIGNUP_URI_WITHOUT_CONTEXT", "/signup");
        AppConfig.myFreeAccessURIList.put("RESOURCES_URI_WITHOUT_CONTEXT", "/resources");
        AppConfig.myFreeAccessURIList.put("PUBLIC_URI_WITHOUT_CONTEXT", "/public");
    }

    public void destroy() {
    }


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest myHttpRequest = (HttpServletRequest) req;
        HttpServletResponse myHttpResponse = (HttpServletResponse) resp;

        final String SIGNIN_URI = myHttpRequest.getContextPath() + "/signin"; // retourne /SGBD_0_war_exploded/signin
        //final String SIGNIN_URI_WITHOUT_CONTEXT = "/signin";
        //final String SIGNUP_URI_WITHOUT_CONTEXT = "/signup";



/*
        final List<String> FreeAccessURIList;


        public final static String SIGNIN_URI = myHttpRequest.getContextPath()+"/signin";
        public final static String SIGNIN_URI_WITHOUT_CONTEXT = "/signin";

        List<Gender> genderList = Arrays.asList

        public final static ArrayList FreeAccessURIList = new ArrayList(Arrays.asList(/resources,/public,/signup));
        public final static List FreeAccessURIList = ["resources","/public","/signup"];
*/
/*
        Map<String, String> myFreeAccessURIList = new HashMap<String, String>();
        myFreeAccessURIList.put("SIGNIN_URI_WITHOUT_CONTEXT", "/signin");
        myFreeAccessURIList.put("SIGNUP_URI_WITHOUT_CONTEXT", "/signup");
        myFreeAccessURIList.put("RESOURCES_URI_WITHOUT_CONTEXT", "/resources");
        myFreeAccessURIList.put("PUBLIC_URI_WITHOUT_CONTEXT", "/public");
*/

        LOG.debug("================== AUTHENTICATION FILTER ======================= " +
                "\nFiltered Request Method : " + myHttpRequest.getMethod() + "\nFiltered Request URI : " + myHttpRequest.getRequestURI());


        // Ressources disponibles sans aucunes contraintes ou conditions d'accès à l'url /resources
        //String particularRequest = myHttpRequest.getRequestURI().substring(myHttpRequest.getContextPath().length());
        String particularRequest = MyUrlUtil.URL_FromFirstExploitableSlash(myHttpRequest);

        /* for ( ItemType myItem  : myCollection) {}   */


        for (Map.Entry<String, String> myItem : AppConfig.myFreeAccessURIList.entrySet()) {
            //String key = myItem.getKey();
            //String value = myItem.getValue();
            LOG.debug("Inside  FOREACH loop");
            if (particularRequest.startsWith(myItem.getValue())) {
                LOG.debug("Inside IF structure in FOREACH loop");
                chain.doFilter(myHttpRequest, myHttpResponse);
            }

        }









/*

        if (particularRequest.startsWith("/resources")
         || particularRequest.startsWith("/public")
         // à rajouter si on fait la gestion du reste de l'URL dans la servlet signIn avec notamment un default case qui redirige vers un truc par defaut => à faire pour chaque servlet de toute manière sinon ca buggera une fois connecté si on met /login/345 par ex
         //|| particularRequest.startsWith("/signin")
         // dans la SignUpServlet, vérifier si deja connecté, ne pas renvoyer le formulaire d'inscription mais un msg disant déjà connecté pour ne pas qu une personne connectée puisse faire une nouvelle inscription
         || particularRequest.startsWith("/signup")
         || particularRequest.startsWith("/signin")) // bien equals ici comme ça un /signin/oooo reseterra l uri à /signin via le redirect tout en bas, c est plus clean
        {
            LOG.debug("ici");
            chain.doFilter(myHttpRequest, myHttpResponse);
        } else {

 */
        // l'argument false fait que si il n'existe pas de session, il n'en crée pas et donc ça reste à null
        // Si on le met à true, une session est automatiquement crée s'il n'en existe pas
        HttpSession mySession = myHttpRequest.getSession(false);

        boolean isSignedIn = mySession != null && mySession.getAttribute("signedUser") != null;

        // obligé de vérifier si c'est la signIn Request sinon ça ira en boucle infinie faire le sendRedirect(SIGNIN_URI)
        // qui ne fait que relancer une requête http://localhost:8080/SGBD_0_war_exploded/signin via le client sans jamais atteindre la servlet mappé sur /signin
        // vérification inutile si on utilise le getRequestDispatcher vers "/signin", mais c'est plus clean de voir le reset de l'url sur le client
        //boolean isLoginRequest = particularRequest.equals(SIGNIN_URI_WITHOUT_CONTEXT);

        LOG.info("User signed in : " + isSignedIn);

        //if (isLoginRequest || isSignedIn) {
        if (isSignedIn) {
            chain.doFilter(myHttpRequest, myHttpResponse);

        } else {
            LOG.debug("Filter redirecting client-side to /signin");

            // Les 2 fonctionnent donc on peut mettre des chemins vers des fichiers ou des mapping d'url pattern vers une servlet
            //myHttpRequest.getRequestDispatcher("WEB-INF/views/signIn/signInForm.jsp").forward(myHttpRequest,myHttpResponse);
            //myHttpRequest.getRequestDispatcher("/signin").forward(myHttpRequest,myHttpResponse);

            // Voir diff entre getRequestDispatcher et sendRedirect dans Notes.txt
            //myHttpRequest.getRequestDispatcher(SIGNIN_URI_WITHOUT_CONTEXT).forward(myHttpRequest,myHttpResponse);
            myHttpResponse.sendRedirect(SIGNIN_URI);
        }
    }
}











