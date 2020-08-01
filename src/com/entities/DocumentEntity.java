package com.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * mapping de la db, je met les champs de la db dedans et je met aussi columne partout pour bien specifier ce que c'est.
 *
 */
// on precise bien le chemin d'ici (package complet) dans persistance
// en dessous la classe Document a pas de s parce qu'elle ne represente qu'une seule ligne en db


@NamedQueries(value = {
        @NamedQuery(name = "Document.findAll",
                query = "SELECT d from DocumentEntity d"),
        @NamedQuery(name = "Document.updateById",
                query = "update DocumentEntity d set d.label = 'aaaaaaaaa' where d.id = :id"),
        @NamedQuery(name = "Document.findAllActive",
                query = "SELECT d from DocumentEntity d where d.active = true")
})


@Entity
@Table(name = "documents")
public class DocumentEntity {
    private static final long serialVersionUID = 1L;
    // private Collection<DocumentsUsersEntity> documentsUsersById;

    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id private Integer id;

    @Column(name="label")
    private String label;

    @Transient
//    @Temporal(TemporalType.DATE)
    //@Column(name="creation_date")
    private Date creationDate;

    @Transient
    private String status;

    @Column(name = "type")
    private String type;

    @Transient
    //@Column(name = "valid")
    private String valide;

    @Column(name = "format")
    private String format;



    @Column(name = "url")
    private String url;

    @Column(name = "active")
    private boolean active;

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", type='" + type + '\'' +
                ", valide='" + valide + '\'' +
                ", format='" + format + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    /*
    --------------------------
         trucs qui permet de veriier si par exemple ce qu'on insere en db n'existe pas deja
     ----------------------------
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentEntity document = (DocumentEntity) o;
        return Objects.equals(getLabel(), document.getLabel()) &&
                Objects.equals(getCreationDate(), document.getCreationDate()); //&&
        // Objects.equals(getActive(), document.getActive());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabel(), getCreationDate());
    }

    //@OneToMany(mappedBy = "documentsByIdDocument")
    //public Collection<DocumentsUsersEntity> getDocumentsUsersById() {
    //  return documentsUsersById;
    //}

    //public void setDocumentsUsersById(Collection<DocumentsUsersEntity> documentsUsersById) {
    //  this.documentsUsersById = documentsUsersById;
    //}
/*
    ---------------------
       getter et setter
    ---------------------
 */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setType(String type){ this.type = type;}

    public String getType(){return type;}

    public String getValide() {
        return valide;
    }

    public void setValide(String valide) {
        this.valide = valide;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
