package com.mativimu.eventsappservice.entities.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.mativimu.eventsappservice.entities.participant.Participant;


@Entity
@Table(name = "events")
public class Event {

    @Id
    @SequenceGenerator(
        name = "events_sequence",
        sequenceName = "events_sequence",
        allocationSize = 10
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "events_sequence")
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_code", nullable = true, length = 10)
    private String eventCode;

    @Column(name = "event_name", nullable = true, length = 25)
    private String eventName;

    @Column(name = "event_type", nullable = true, length = 20)
    private String eventType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "event_date", nullable = true)
    private Date eventDate;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt = new Date();

    @OneToMany(
        mappedBy = "event",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    private List<Participant> participants = new ArrayList<>();

    public Event(String eventCode, String eventName, String eventType, Date eventDate) {
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDate = eventDate;
    }
    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String code) {
        this.eventCode = code;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String name) {
        this.eventName = name;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String type) {
        this.eventType = type;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date date) {
        this.eventDate = date;
    }

}