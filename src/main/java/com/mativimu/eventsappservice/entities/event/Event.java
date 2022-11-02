package com.mativimu.eventsappservice.entities.event;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;


@Entity(name = "Event")
@Table(name = "events")
public class Event {

    @Id
    @SequenceGenerator(
        name = "event_sequence",
        sequenceName = "event_sequence",
        allocationSize = 10
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "event_sequence")
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "code")
    private String code;

    @Column(name = "name_name")
    private String eventName;

    @Column(name = "type")
    private String type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private Date date = new Date();

    @Column(name = "expired")
    private boolean expired;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;


    public Event(String code, String eventName, String type, Date date, boolean expired) {
        this.code = code;
        this.eventName = eventName;
        this.type = type;
        this.date = date;
        this.expired = expired;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    
    
}