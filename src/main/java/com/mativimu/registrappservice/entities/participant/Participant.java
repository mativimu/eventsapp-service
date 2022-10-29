package com.mativimu.registrappservice.entity.participant;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
public class Participant {
    
    private Long user_id;
    private Long event_id;
    private String status;
    private boolean attendance;

    public Participant(Long user_id, Long event_id, String status, boolean attendance) {
        this.user_id = user_id;
        this.event_id = event_id;
        this.status = status;
        this.attendance = attendance;
    }

    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    public Long getEvent_id() {
        return event_id;
    }
    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public boolean isAttendance() {
        return attendance;
    }
    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

    
}
