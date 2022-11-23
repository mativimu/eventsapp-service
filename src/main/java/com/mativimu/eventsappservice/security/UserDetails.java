package com.mativimu.eventsappservice.security;

import com.mativimu.eventsappservice.domain.user.User;

import lombok.Data;

@Data
public class UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String fullname;
    private String occupation;
    private String token;

    public UserDetails(User user, String token) {
        this.id = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getUserEmail();
        this.password = user.getUserPassword();
        this.fullname = user.getUserFullName();
        this.occupation = user.getUserOccupation();
        this.token = token;
    }
}
