package com.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "events_users", schema = "schoolux", catalog = "")
public class EventUserEntity {
    private int id;
    private EventEntity eventsByIdEvent;

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
        EventUserEntity that = (EventUserEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id", nullable = false)
    public EventEntity getEventsByIdEvent() {
        return eventsByIdEvent;
    }

    public void setEventsByIdEvent(EventEntity eventsByIdEvent) {
        this.eventsByIdEvent = eventsByIdEvent;
    }
}
