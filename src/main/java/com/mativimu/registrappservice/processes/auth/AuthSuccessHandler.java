package com.mativimu.registrappservice.processes.auth;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mativimu.registrappservice.entities.user.User;


@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    private final int expTime;
    private final String secret;

    public AuthSuccessHandler(@Value("${jwt.expiration}") int expiration, 
                              @Value("${jwt.secret}") String secretKey ) {
        this.expTime = expiration;
        this.secret = secretKey;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String access_token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + expTime))
                .withIssuer(request.getRequestURI().toString())
                .sign(algorithm);

        String refresh_token = JWT.create()
        .withSubject(user.getEmail())
        .withExpiresAt(new Date(System.currentTimeMillis() + expTime))
        .withIssuer(request.getRequestURI().toString())
        .sign(algorithm);

        response.setHeader("access_token" ,access_token);
        response.setHeader("refresh_token" ,refresh_token);
        
    }
}
