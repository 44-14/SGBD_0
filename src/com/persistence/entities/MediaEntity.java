package com.persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "medias", schema = "schoolux", catalog = "")
public class MediaEntity {
    private int id;
    private String name;
    private byte active;
    private String type;
    private Timestamp dateUpload;
    private String url;
    private String format;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "active")
    public byte getActive() {
        return active;
    }

    public void setActive(byte active) {
        this.active = active;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "date_upload")
    public Timestamp getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Timestamp dateUpload) {
        this.dateUpload = dateUpload;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "format")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaEntity that = (MediaEntity) o;
        return id == that.id &&
                active == that.active &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(dateUpload, that.dateUpload) &&
                Objects.equals(url, that.url) &&
                Objects.equals(format, that.format);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, active, type, dateUpload, url, format);
    }
}
