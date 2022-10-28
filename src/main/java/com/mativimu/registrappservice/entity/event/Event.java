package com.mativimu.registrappservice.entity.event;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;


@Table
@Entity
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
    private Long id;

    private String code;
    private String name;
    private String type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    
    private boolean expired;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created_at;


    @Autowired
    public Event(String code, String name, String type, Date date, Date created_at,boolean expired) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.date = date;
        this.expired = expired;
        this.created_at = created_at;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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