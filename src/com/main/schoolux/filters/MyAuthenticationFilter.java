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


// A LIRE POUR LES HISTOIRES AVEC L URL ET LES URL PATTERN
// https://docs.roguewave.com/en/hydraexpress/4.6.0/html/rwsfservletug/4-4.html


/*  Le pattern  /signin       intercepte uniquement    /signin


    Le pattern  /signin/*     intercepte                tout ce qui commence par /signin/   dont /signin/abc
                              ET INTERCEPTE EN PLUS UNIQUEMENT  /signin  sans le / à la fin
                              MAIS n'intercepte pas le reste n'ayant pas le second /  comme    /signinabc  ou  /signino ou  /signinabc/oo


    DANS LES 2 CAS , LE SERVLET PATH UNE FOIS DANS LE doGet() SERA /signin
    IL FAUDRA SCINDER L URI EN RECUPERANT TOUT CE QUI SUIT LE DERNIER / POUR AVOIR LA PARTIE EXPLOITABLE DANS LE SWITCH
 */


/*
Le chemin du serveur :
 recupere URLl  =>  http://localhost:8080/SGBD_0_war_exploded/kekchose/autrechose
 recupere l'URI  => /SGBD_0_war_exploded/kekchose/autrechose
 retire le contexte de l'URI  => le context = /SGBD_0_war_exploded donc ici on aurait /kekchose/autrechose
 si le restant de l'URI est vide => ajoute un slash + le welcome file
 si le restant de l'URI est juste /  => ajoute le welcome file    c une sorte de filter par defaut

 mais il semble que ça soit que pour le context qu'il y a ce mecanisme donc il y a un truc qui rajoute l'url
 Any url-pattern will be applied after that context  ( le context sans le / )

 donc tout ce qui suit /SGBD_0_war_exploded sera analysé pour l url patterr
 si il y a bien  un restant => ici /kekchose/autrechose => chaque slash est analysé dans l'ordre
 et dès qu'il y a un mapping avec un url pattern traitée par une servlet
 cet url pattern  sera mis en request.servletPath

 Remarque :   l url-pattern  /abc aura /abc en servletPath  et n'accepte que l'url /abc
 Remarque :   l url-pattern  /abc/*  aura aussi  /abc en servletPath   et accepte l'url /abc ainsi que tout ce qui commence par /abc/
    donc le servletPath ne peut pas servir à dispatcher la requete dans un case vu /user et /user/edit auront le meme servletPath
 */


/*
Are you using the correct context as the first part of your path in URL?
        Whenever you deploy an application, you specify a context that identify all your URLs.
        Any url-pattern will be applied after that context.
        Let's say you have context called "MyShop", then using your provided web.xml, you should call http://yourdomain/MyShop/MyServlet .
 */



@WebFilter(filterName = "MyAuthenticationFilter", urlPatterns ="/*")
public class MyAuthenticationFilter implements Filter {


    // LOGGER + PATH CONSTANTS +  REQUEST COUNTER
    private final static Logger LOG = Logger.getLogger(MyAuthenticationFilter.class);
    public static int requestCounter = 0; // initialisé à 0 par défaut


    public void init(FilterConfig config) throws ServletException {
        // Uniquement ce qui est commun pour toutes les requêtes entrantes de toutes les sessions simultanées
        // car la méthode n'est appelée qu'une seule fois lors de l'initialisation de l'objet filtre au démarrage de l'appli (pas en lien avec la reception d une requete)

        LOG.debug("Filter class : " + this.getClass()+
                "\n Class name : "  + this.getClass().getName()
                +"\nFilter instance toString : "+this.toString());
    }

    public void destroy() {
    }


    public void doFilter(ServletRequest receivedReq, ServletResponse receivedResp, FilterChain chain) throws ServletException, IOException {

        /* CAST DES PARAMETRES car la méthode doFilter() de l'interface implémentée Filter
           requiert ServletRequest et ServletResponse en paramètres */
        HttpServletRequest request = (HttpServletRequest) receivedReq;
        HttpServletResponse response = (HttpServletResponse) receivedResp;

        MyLogUtil.enterFilter(request);

        // stocke /SGBD_0_war_exploded/signin dans la hashmap
        if (requestCounter==0) {
            AppConfig.myFreeAccessWIthoutContextURIList.put("CONTEXT_PATH",request.getContextPath());
            AppConfig.myFreeAccessWIthoutContextURIList.put("SIGNIN_URI",request.getContextPath()+"/signin");
        }

        LOG.info("Number of processed requests : "+ ++requestCounter);

        // Récupérer tout ce qui suit le contexte /SGBD_0_war_exploded/ dans l'URI
        String scindedURI = MyURLUtil.URI_WithoutContext(request);
        LOG.debug("URI scindée sans contexte : "+scindedURI);


        // Vérification que la partie scindée de l'URI ne matche pas avec le début des URI publiquement accessibles stockées dans la Hashmap myFreeAccessWIthoutContextURIList
        // à savoir /signin - /signup  -  /resources - /public
        /* for ( ItemType myItem  : myCollection) {}   */
        for ( Map.Entry <String,String> myItem   :   AppConfig.myFreeAccessWIthoutContextURIList.entrySet()) {
            //LOG.debug("Inside FOREACH loop => Key : "+myItem.getKey() +"   Value :"+myItem.getValue());
            // String key = myItem.getKey();
            // String value = myItem.getValue();
            //if (scindedURI.contentEquals(myItem.getValue()) || scindedURI.startsWith(myItem.getValue()+"/")){
            if (scindedURI.startsWith(myItem.getValue())){
                //LOG.debug("Inside IF structure in FOREACH loop");
                LOG.debug("Filter transmitting the request targeting a FREE ACCESS WITHOUT CONTEXT URI");
                chain.doFilter(request, response);
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
        HttpSession mySession = request.getSession(false);

        boolean isSignedIn = mySession != null && mySession.getAttribute("signedUser") != null;
        LOG.info("User signed in : " + isSignedIn);

        if (isSignedIn) {
            // Affichage des infos de l'user connecté via un override de la méthode toString() dans UserEntity.class
            UserEntity signedUser = (UserEntity) mySession.getAttribute("signedUser");
            LOG.debug("Signed user informations: " + signedUser.toString());
            LOG.debug("Filter transmitting the request to "+request);
            chain.doFilter(request, response);
        } else {
            LOG.debug("Filter redirecting client-side to /signin");

            // Les 2 fonctionnent donc on peut mettre des chemins vers des fichiers ou des mapping servlet d'url pattern
            //request.getRequestDispatcher("WEB-INF/views/signIn/signInForm.jsp").forward(request,myHttpResponse);
            //request.getRequestDispatcher("/signin").forward(request,myHttpResponse);

            // Voir diff entre getRequestDispatcher et sendRedirect dans Notes.txt
            //request.getRequestDispatcher(SIGNIN_URI_WITHOUT_CONTEXT).forward(request,myHttpResponse);
            response.sendRedirect(AppConfig.myFreeAccessWIthoutContextURIList.get("SIGNIN_URI"));

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












