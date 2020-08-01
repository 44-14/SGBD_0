package com.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "medias_users", schema = "schoolux", catalog = "")
public class MediaUserEntity {
    private int id;
    private MediaEntity mediasByIdMedia;

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
        MediaUserEntity that = (MediaUserEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "id_media", referencedColumnName = "id", nullable = false)
    public MediaEntity getMediasByIdMedia() {
        return mediasByIdMedia;
    }

    public void setMediasByIdMedia(MediaEntity mediasByIdMedia) {
        this.mediasByIdMedia = mediasByIdMedia;
    }
}
