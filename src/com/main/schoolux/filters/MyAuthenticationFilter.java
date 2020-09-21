package com.main.schoolux.filters;

import com.AppConfig;
import com.main.schoolux.utilitaries.MyUrlUtil;
import com.persistence.entities.UserEntity;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebFilter(filterName = "MyAuthenticationFilter")
public class MyAuthenticationFilter implements Filter {



    // LOGGER + PATH CONSTANTS +  REQUEST COUNTER
    private final static Logger LOG = Logger.getLogger(MyAuthenticationFilter.class);
    //public static int counter;






    public void init(FilterConfig config) throws ServletException {
        // Uniquement ce qui est commun pour toutes les requêtes entrantes de toutes les sessions simultanées car la méthode n'est appelée qu'une seule fois par l'application sur son temps de vie
    }

    public void destroy() {
    }


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest myHttpRequest = (HttpServletRequest) req;
        HttpServletResponse myHttpResponse = (HttpServletResponse) resp;


        LOG.debug("================== AUTHENTICATION FILTER ======================= " +
                "\nFiltered request method : " + myHttpRequest.getMethod() + "\nFiltered request URI : " + myHttpRequest.getRequestURI() + "\nFiltered request servlet path : " + myHttpRequest.getServletPath());


        // stocke /SGBD_0_war_exploded/signin dans la hashmap
        if (AppConfig.requestCounter==0) {
            AppConfig.myFreeAccessURIList.put("CONTEXT_PATH",myHttpRequest.getContextPath());
            AppConfig.myFreeAccessURIList.put("SIGNIN_URI",myHttpRequest.getContextPath()+"/signin");
        }


        LOG.info("Number of processed requests : "+ ++AppConfig.requestCounter);


        // Récupérer tout ce qui suit le contexte /SGBD_0_war_exploded/ dans l'URI
        String scindedURI = MyUrlUtil.URL_FromFirstExploitableSlash(myHttpRequest);


        // Vérification que la partie d'URI récupérée ne matche pas avec une des URI publiquement accessibles stockées dans la Hashmap myFreeAccessURIList
        /* for ( ItemType myItem  : myCollection) {}   */
        for (Map.Entry<String, String> myItem : AppConfig.myFreeAccessURIList.entrySet()) {
            //LOG.debug("Inside FOREACH loop => Key : "+myItem.getKey() +"   Value :"+myItem.getValue());
            // String key = myItem.getKey();
            // String value = myItem.getValue();

            if (scindedURI.contentEquals(myItem.getValue()) || scindedURI.startsWith(myItem.getValue()+"/")){
                //LOG.debug("Inside IF structure in FOREACH loop");
                LOG.debug("Filter transmitting the request targeting a free access URI");
                chain.doFilter(myHttpRequest, myHttpResponse);
                return;
            }
        }

        /*
        Chaque servlet va elle-même faire la gestion du restant de l'URI qui suit son mapping d'URL-Pattern une fois que la requete lui est transmise
        avec notamment un switch qui comprend un default case qui redirige vers une uri traitée dans un case précédent, ou le default qui modifie l action à traiter et boucle sur le switch
        Par exemple permet d'éviter que /signin/abcd/123 fasse une erreur, ce qui n'est pas traité par le switch de la signInServlet (ici la partie /abc/123)
        passera par son default case qui se chargera d eviter l'erreur
        */


        // l'argument false de getSession() implique que s'il n'existe pas déjà une session, il n'en crée pas une et le retour est null. Dans ce cas, il faut sign out l'user et le rediriger vers la signin page
        // Si on le met à true, une session est automatiquement crée s'il n'en existe pas.
        HttpSession mySession = myHttpRequest.getSession(false);

        boolean isSignedIn = mySession != null && mySession.getAttribute("signedUser") != null;
        LOG.info("User signed in : " + isSignedIn);


        if (isSignedIn) {
            // Affichage des infos de l'user connecté via un override de la méthode toString() dans UserEntity.class
            UserEntity signedUser = (UserEntity) mySession.getAttribute("signedUser");
            LOG.debug("Signed user informations: " + signedUser.toString());
            LOG.debug("Filter transmitting the request");
            chain.doFilter(myHttpRequest, myHttpResponse);
        } else {
            LOG.debug("Filter redirecting client-side to /signin");

            // Les 2 fonctionnent donc on peut mettre des chemins vers des fichiers ou des mapping servlet d'url pattern
            //myHttpRequest.getRequestDispatcher("WEB-INF/views/signIn/signInForm.jsp").forward(myHttpRequest,myHttpResponse);
            //myHttpRequest.getRequestDispatcher("/signin").forward(myHttpRequest,myHttpResponse);

            // Voir diff entre getRequestDispatcher et sendRedirect dans Notes.txt
            //myHttpRequest.getRequestDispatcher(SIGNIN_URI_WITHOUT_CONTEXT).forward(myHttpRequest,myHttpResponse);
            myHttpResponse.sendRedirect(AppConfig.myFreeAccessURIList.get("SIGNIN_URI"));

            /*
             Remarque : si on utilise ici sendRedirect(la signin uri), alors il faut impérativement avoir une partie précédente du filtre qui vérifie si cette uri est reçue
             sinon on aura une boucle infinie avec le filtre qui sendRedirect en permanence vers la signin uri mais celle-ci n'étant pas traitée, on reviendrait tout le temps à la dernière instruction
             qui relance une requête http://localhost:8080/SGBD_0_war_exploded/signin via le client sans qu'on atteigne jamais la servlet mappée sur /signin
             Cette vérification n'est pas nécessaire si on utilise le getRequestDispatcher vers "/signin" vu qu'il va se charger lui-même de contacter la servlet mappée
             mais c'est plus clean de voir le reset de l'url sur le client grâce au sendRedirect
             */

        }
    }
}












