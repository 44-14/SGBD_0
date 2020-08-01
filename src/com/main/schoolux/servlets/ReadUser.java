package com.main.schoolux.servlets;


import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ReadUser", urlPatterns = "/utilisateur")
public class ReadUser extends HttpServlet {
    private static Logger log = Logger.getLogger(ReadUser.class);
    //private Utilisateur utilisateur;

    private String nom;
    private String prenom;

    public ReadUser(){ super(); };

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //recup du nom et prenom
       /* nom = request.getParameter("nom");
        prenom = request.getParameter("prenom");
       // request.getSession().setAttribute("nom",nom);

        log.info("[readD$$oc] Executing doGet() of showDocument.");
        Utilisateur utilisateur = getReadUserById();

        log.info(utilisateur == null ? "null" : utilisateur.toString());
        log.info("[readUser] Set attribute in session.");
        request.getSession().setAttribute("monUtilisateur", utilisateur);
        log.info("[readUser] get RequestDispatcher.");
        RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/utilisateur.jsp");
        createUser(nom,"swag");
        log.info("[readUser] forward.");
        rd.forward(request, response);
*/

    }

  /*  public Utilisateur createUser(String nom, String prenom){
log.debug("debut du createUser");

        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setPrenom(prenom);
log.debug("on vient de parametrer nom et prenom");
        //on fait l'appel avec le dao
        EntityFinderImpl efi = new EntityFinderImpl();
        log.debug("call in entityFinderImpl");
        //ici, on recuperer notre create ENTIER avec l'ID et on le restocke dans le u
       // u = (Utilisateur) efi.create(u);
log.debug("end create user, id=" + u!=null ?u.getId() : "user is null");
         return u;
    }

    public Utilisateur getReadUserById() {
        EntityFinder finder = new EntityFinderImpl<Utilisateur>();
        Utilisateur utilisateur = new Utilisateur();
        utilisateur = (Utilisateur) finder.findOne(utilisateur, 1);

        return utilisateur;
    }*/
    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        ReadUser.log = log;
    }
/*
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
*/}
