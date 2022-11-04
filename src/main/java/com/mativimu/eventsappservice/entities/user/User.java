package com.mativimu.eventsappservice.entities.user;


import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.google.common.hash.Hashing;
import com.mativimu.eventsappservice.entities.participant.Participant;

import java.nio.charset.StandardCharsets;
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

    @Column(name = "username", nullable = false, unique = false, length = 25)
    private String username;
    
    @Column(name = "user_email", nullable = false, unique = true, length = 30)
    private String userEmail;

    @Column(name = "user_password", nullable = false, unique = true)
    private String userPassword;

    @Column(name = "full_name", nullable = false, unique = false, length = 30)
    private String fullName;

    @Column(name = "user_occupation", nullable = true, unique = false, length = 30)
    private String userOccupation;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    private List<Participant> participant = new ArrayList<>();


    public User(String username, String userEmail, String userPassword, String fullName, String userOccupation) {
        this.username = username;
        this.userEmail = userEmail;
        setUserPassword(userPassword);
        this.fullName = fullName;
        this.userOccupation = userOccupation;
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
        String hashedPassword = Hashing.sha256()
            .hashString(userPassword, StandardCharsets.UTF_8)
            .toString();
        this.userPassword = hashedPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

}
