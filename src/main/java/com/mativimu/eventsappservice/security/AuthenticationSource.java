package com.mativimu.eventsappservice.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.hash.Hashing;
import com.mativimu.eventsappservice.domain.user.User;
import com.mativimu.eventsappservice.domain.user.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthenticationSource {

    private final UserService userService;

    @Autowired
    public AuthenticationSource(UserService userService) {
        this.userService = userService;
    }
        
    @PostMapping
    public ResponseEntity<UserDetails> getUserCredentials(@RequestBody Credentials credentials) throws Exception, IllegalStateException {
        User user = userService.getUserByEmail(credentials.getEmail());
        String credsHashedPassword = Hashing.sha256()
            .hashString(credentials.getPassword(), StandardCharsets.UTF_8).toString();
        log.info("Password comparison:");
        log.info("Password in database " + user.getUserPassword());
        log.info("Password in request " + credsHashedPassword);
        if(!user.getUserPassword().equals(credsHashedPassword)) {
            throw new InvalidParameterException("Invalid Password");
        }
        KeyPair keyPair = TokenUtils.keysGenerator();
        TokenUtils.recordPublicKey(credentials.getEmail(), (RSAPublicKey) keyPair.getPublic());
        TokenUtils.recordPrivateKey(credentials.getEmail(),(RSAPrivateKey) keyPair.getPrivate());
        Map<String, String> payload = TokenUtils.generatePayload(user);
        String token = TokenUtils.generateJwt(payload, credentials.getEmail(), keyPair);
        return
            ResponseEntity.ok().body(new UserDetails(user, token));
    }
}
