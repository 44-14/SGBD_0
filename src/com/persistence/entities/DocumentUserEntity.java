package com.persistence.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "documents_users", schema = "schoolux", catalog = "")
public class DocumentUserEntity {
    private int id;
    private DocumentEntity documentsByIdDocument;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentUserEntity that = (DocumentUserEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "id_document", referencedColumnName = "id", nullable = false)
    public DocumentEntity getDocumentsByIdDocument() {
        return documentsByIdDocument;
    }

    public void setDocumentsByIdDocument(DocumentEntity documentsByIdDocument) {
        this.documentsByIdDocument = documentsByIdDocument;
    }
}
