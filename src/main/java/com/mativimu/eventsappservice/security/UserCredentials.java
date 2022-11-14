package com.mativimu.eventsappservice.security;

import com.mativimu.eventsappservice.domain.user.User;

import lombok.Data;

@Data
public class UserCredentials {

    private Long userId;
    private String username;
    private String userEmail;
    private String userPassword;
    private String fullName;
    private String token;

    public UserCredentials(User user, String token) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.userEmail = user.getUserEmail();
        this.userPassword = user.getUserPassword();
        this.fullName = user.getFullName();
        this.token = token;
    }
}
