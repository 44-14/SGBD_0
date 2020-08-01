package com.persistence.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "events", schema = "schoolux", catalog = "")
public class EventEntity {
    private int id;
    private String name;
    private Date beginDate;
    private Date endDate;
    private Time beginHour;
    private Time endHour;
    private String description;
    private String attachmentUrl;

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
    @Column(name = "begin_date")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Basic
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "begin_hour")
    public Time getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(Time beginHour) {
        this.beginHour = beginHour;
    }

    @Basic
    @Column(name = "end_hour")
    public Time getEndHour() {
        return endHour;
    }

    public void setEndHour(Time endHour) {
        this.endHour = endHour;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "attachment_url")
    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEntity that = (EventEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(beginDate, that.beginDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(beginHour, that.beginHour) &&
                Objects.equals(endHour, that.endHour) &&
                Objects.equals(description, that.description) &&
                Objects.equals(attachmentUrl, that.attachmentUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, beginDate, endDate, beginHour, endHour, description, attachmentUrl);
    }
}
