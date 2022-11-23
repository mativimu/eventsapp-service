package com.mativimu.eventsappservice.domain.user;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mativimu.eventsappservice.domain.participant.Participant;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;


@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
        name = "users_sequence",
        sequenceName = "users_sequence",
        allocationSize = 10
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "users_sequence")
    @Column(name = "user_id"
    )
    private Long userId;

    @Column(name = "username", nullable = false, length = 25)
    private String username;
    
    @Column(name = "user_email", nullable = false, unique = true, length = 30)
    private String userEmail;

    @Column(name = "user_password", nullable = false, length = 64)
    private String userPassword;

    @Column(name = "full_name", nullable = false, length = 30)
    private String userFullName;

    @Column(name = "user_occupation", nullable = true, length = 30)
    private String userOccupation;

    @OneToMany( 
        mappedBy = "user",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    private List<Participant> participant = new ArrayList<>();

    public User(){}

    public User(String username, String userEmail,
                String userPassword, String userFullName, String userOccupation) {
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userFullName = userFullName;
        this.userOccupation = userOccupation;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

}
