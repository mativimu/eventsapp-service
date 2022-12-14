package com.mativimu.eventsappservice.domain.event;

import java.nio.charset.StandardCharsets;
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

import com.google.common.hash.Hashing;
import com.mativimu.eventsappservice.domain.participant.Participant;


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

    @Column(name = "event_code", nullable = false, length = 25)
    private String eventCode;

    @Column(name = "event_name", length = 100)
    private String eventName;

    @Column(name = "event_type", length = 20)
    private String eventType;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "event_date", nullable = true)
    private Date eventDate;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createdAt = new Date()     ;

    @OneToMany(
        mappedBy = "event",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    private List<Participant> participants = new ArrayList<>();

    @Column(name = "event_fingerprint", nullable = false, unique = true, length = 64)
    private String fingerprint;

    public Event() {}

    public Event(String eventCode, String eventName, String eventType, Date eventDate) {
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDate = eventDate;
        setFingerprint();
    }

    public Long getEventId() {
        return this.eventId;
    }

    public String getEventCode() {
        return this.eventCode;
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

    public String getFingerprint() {
        return this.fingerprint;
    }

    private void setFingerprint() {
        String hashingSource = this.eventCode + this.eventName + this.eventType + this.eventDate;
        this.fingerprint = Hashing.sha256()
            .hashString(hashingSource , StandardCharsets.UTF_8).toString();
    }
}