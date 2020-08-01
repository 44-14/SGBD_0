package com.main.schoolux.services;

import com.entities.DocumentEntity;
import com.entityFinder.EntityFinder;
import com.entityFinder.EntityFinderImpl;
import com.main.schoolux.servlets.ReadUser;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

public class DocumentService {
    private static Logger log = Logger.getLogger(ReadUser.class);



    public void createDocument(EntityManager em, DocumentEntity doc){
      em.persist(doc);
      em.merge(doc);

    }

    public void updateDocument(EntityManager em, DocumentEntity doc){
        log.info("begin - updateDocumentService");

       em.createNamedQuery("Document.updateById");
        // em.persist(doc);
         em.merge(doc);
        log.info("end - updateDocService");
    }

    public void deleteDocument(EntityManager em, DocumentEntity doc){
        log.info("deleteDocumentsService - begin");
        em.createNamedQuery("Document.updateById");
        em.merge(doc);
        log.info("deleteDocumentService - end");
    }




    public DocumentEntity getReadDocById() {
        EntityFinder finder = new EntityFinderImpl<DocumentEntity>();
        DocumentEntity doc = new DocumentEntity();
        doc = (DocumentEntity) finder.findOne(doc, 5);

        return doc;
    }

    public List<DocumentEntity> readAllDocuments(){
        EntityFinder finder = new EntityFinderImpl<DocumentEntity>();
        DocumentEntity doc = new DocumentEntity();
        List<DocumentEntity> listDoc = finder.findByNamedQuery("Document.findAll", doc, null);

        return listDoc;
    }
}
