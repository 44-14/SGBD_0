package com.main.schoolux.form;


import com.entities.DocumentEntity;

import com.entityFinder.EntityFinderImpl;
import com.main.schoolux.servlets.ReadUser;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DocumentForm {
    private static Logger log = Logger.getLogger(ReadUser.class);

    //methode du formukaire pour crer un doc:
    //il faut: recuperer les variables de la JSP, les vérifier (appel d"autres methodes de verif) et tout retourner.
    //Puis: cette methode est appelée depuis la servlet. on va stocker le resultat du doc dans une variable doc
    // ensuite, ry catch + transaction avec dedans l'appel au service

    /**
     *  CRUD
     */
    public DocumentEntity createDoc(HttpServletRequest request) {

    DocumentEntity doc = new DocumentEntity();
    HttpSession session = request.getSession();
    String error = null;
    session.setAttribute("error", error);

    //request
    String label = request.getParameter("labelDoc");
    String type = request.getParameter("typeDoc");
    String format = request.getParameter("formatDoc");
    String url = request.getParameter("urlDoc");
    String otherFormat = request.getParameter("otherFormatDoc");


            //ici, il faut recuperer les variables du formaulaire car la elles sont en dur
    doc.setLabel(label);
    log.debug("label before valideLabel: "+label);
    doc.setType(type);
    doc.setActive(true);

    if(!(label.equals(null))){
        boolean valide;
        valide = validLabel(label);
        log.debug("validation label response : "+ valide);
        if(valide == true){
            log.debug("valide : true");
            doc.setLabel(label);
        }
        else if(valide == false){
            log.debug("valide = false");
            error = "Votre nom de document doit faire 3 caractères minimum";
            session.setAttribute("error", error);
        }
    }

    log.info("format" + label);

    if(!(url == null)){
        doc.setUrl(url);
    }
    if(otherFormat != null){
        doc.setFormat(otherFormat);
    }else{
        doc.setFormat(format);
    }

    return doc;
}
/*
public Date dateJour(){
    Date date = new Date();
    log.info(" NOUVELLE DATE " + date);
//ca ca fonctionne mais c'est en string
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    String dateJour = fmt.format(date);
    log.info(" NOUVELLE DATE STRING " + dateJour);
}*/


//formulaire qui va recuperer les données de la page de modification. Il y aura:
    //les verifs , les recup de variables

    //CA FONCTIONNR
    public DocumentEntity updateDoc(HttpServletRequest request){
        log.info("updateDoc - begin");
        DocumentEntity doc = new DocumentEntity();
        EntityFinderImpl efi = new EntityFinderImpl();
        HttpSession session = request.getSession();
        String error = null;
        session.setAttribute("error", error);

    log.debug("doc? "+doc);
        //recuperate of the id for update a document
        int id = Integer.parseInt(request.getParameter("idDocUpdate"));
        log.debug("l'id recupere depuis notre page ShowDocument dans l'update est : "+ id);
        doc = (DocumentEntity) efi.findOne(doc, id);
        //request
        String label = request.getParameter("updateLabelDoc");
        String format = request.getParameter("updateFormatDoc");
        String type = request.getParameter("updateTypeDoc");
        String url = request.getParameter("updateUrlDoc");
log.info("begin int id updateDoc");


        log.debug("label : " + label);
        if(!(label == null)){
            boolean valide;
            valide = validLabel(label);
            if(valide == true){
                doc.setLabel(label);
            }
            else{
                error = "Votre nom de document doit faire 3 caractères minimum";
                session.setAttribute("error", error);
            }

        }
        if(!(format == null)){
            doc.setFormat(format);
        }
        if (!(type == null)){
            doc.setType(type);
        }
        if(!(url == null)){
            doc.setUrl(url);
        }

        log.info("nom: "+doc.getLabel());

        log.info("updateDoc - end");
        return doc;
    }

    public DocumentEntity deleteLogic(HttpServletRequest request){
        log.debug("deleteLogicForm begin");

        DocumentEntity doc = new DocumentEntity();
        EntityFinderImpl efi = new EntityFinderImpl();
        HttpSession session = request.getSession();
        String error = null;
        session.setAttribute("error", error);

        //recuperate of the id for delete a document
        int id = Integer.parseInt(request.getParameter("idDocDelete"));
        log.debug("l'id recupere depuis notre page ShowDocument dans le delete est : "+ id);
        doc = (DocumentEntity) efi.findOne(doc, id);
        //request

        doc.setActive(false);
        log.info("nom: "+doc.getLabel());

        log.info("deleteDoc - end");
        return doc;
    }
/*
    public Document changeActif(HttpServletRequest request){
        Document doc = new Document();
        //doc.setValide();
    }
*/

    /**
        Methodes for documents
     */

    public String updateOrDeleteDocument(HttpServletRequest request){
        HttpSession session = request.getSession();
        int idDoc = 0;

        String type;
        String format;
        //request
        String update = request.getParameter("updateDoc");
        String delete = request.getParameter("deleteDoc");

        //condition
        if(update != null){
            log.info("choix update: "+ update);
            //recuperate idDocUptade from ShowDocument.jsp
            idDoc = Integer.parseInt(request.getParameter("idDocUpdate"));
            //put idDocUpdate in a session for use later in updateDocuments
            session.setAttribute("idDocUpdate", idDoc);

            //recuperate the id is OK, now, go recuperate the other(label...)
            String labelDoc = request.getParameter("labelDocUpdate");
            log.debug("labelDocUpdate dans le form "+labelDoc );
            session.setAttribute("labelDocUpdate", labelDoc);
            type = request.getParameter("typeDocUpdate");
            session.setAttribute("typeDocUpdate", type);
        }else if (delete != null){
            log.info("choix delete: "+ delete);
            idDoc = Integer.parseInt(request.getParameter("idDocDelete"));
            session.setAttribute("idDocDelete", idDoc);
        }


        String choice = null;
       // log.info("choix update: "+ update);
       // log.info("choix delete: "+ delete);
        log.info("id du document : "+ idDoc);


        if (delete != null){
            choice = delete;

            //on doit envoyer le delete a la servlet
        }else if (update != null){
            choice = update;
            //on doit envoyer a la servlet update
        }

        return choice;
    }

    public String yesOrNo(HttpServletRequest request){
        String choice = null;
        String radio = request.getParameter("choiceDelete");
       // String no = request.getParameter("no");
        log.info("string radio: "+radio);
        //log.info("string no: "+no);

        if(radio.equals("yes") && radio != null){
            choice = "yes";
        } else if ( radio.equals("no") && radio != null){
            choice = "no";
        }

        return choice;

    }



    /**
        Validations
     */
    public boolean validLabel(String labelDoc) {
        log.info("begin = valideLabel + labelDoc recept: " + labelDoc);
        boolean valide = false;
        if ( labelDoc != null && labelDoc.trim().length() >= 3 ) {
            valide = true;
        }
        log.info("end valideLabel + return" + valide);
        return valide;
    }


    public void validFormat(){

    }



}
