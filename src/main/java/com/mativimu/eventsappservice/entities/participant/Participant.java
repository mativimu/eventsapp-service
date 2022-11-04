package com.mativimu.eventsappservice.entities.participant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.engine.spi.Mapping;

import com.mativimu.eventsappservice.entities.event.Event;
import com.mativimu.eventsappservice.entities.user.User;

@Entity
@Table(name = "participants")
public class Participant {
    
    @Id
    @SequenceGenerator(
        name = "participants_sequence",
        sequenceName = "participants_sequence",
        allocationSize = 10
        )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "participants_sequence")
    @Column(name = "participant_id")
    private Long participantId;

    @Column(name = "participant_status", nullable = false, unique = false, length = 8)
    private String participantStatus;

    @Column(name = "event_attendance", nullable = false, unique = false)
    private boolean eventAttendance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    private Event event;

    public Participant(String participantStatus, boolean eventAttendance, User user, Event event) {
        this.participantStatus = participantStatus;
        this.eventAttendance = eventAttendance;
        this.user = user;
        this.event = event;
    }

    public String getParticipantStatus() {
        return participantStatus;
    }

    public void setParticipantStatus(String participantStatus) {
        this.participantStatus = participantStatus;
    }

    public boolean isEventAttendance() {
        return eventAttendance;
    }

    public void setEventAttendance(boolean eventAttendance) {
        this.eventAttendance = eventAttendance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
}
