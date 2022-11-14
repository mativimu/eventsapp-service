package com.mativimu.eventsappservice.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.mativimu.eventsappservice.domain.user.User;


public class TokenUtils {

    private static Map<String, RSAPublicKey> publicKeyRecord = new HashMap<>();
    private static Map<String, RSAPrivateKey> privateKeyRecord = new HashMap<>();
    
    public static KeyPair keysGenerator() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static void recordPublicKey(String email, RSAPublicKey publicKey) {
        boolean exists = publicKeyRecord.containsKey(email);
        if(!exists){
            publicKeyRecord.put(email, publicKey);
        }
        publicKeyRecord.replace(email, publicKey);
    }

    public static void recordPrivateKey(String email, RSAPrivateKey privateKey) {
        boolean exists = publicKeyRecord.containsKey(email);
        if(!exists){
            privateKeyRecord.put(email, privateKey);
        }
        privateKeyRecord.replace(email, privateKey);
    }

    public static Map<String, String> generatePayload(User user) {
        Map<String, String> payload = new HashMap<>();
        payload.put("username", user.getUsername());
        payload.put("email", user.getUserEmail());
        payload.put("name", user.getFullName());
        payload.put("password", user.getUserPassword());
        return payload;
    }

    public static String generateJwt(Map<String,String> payload, String issuer, KeyPair keyPair) throws Exception {
        Builder tokenBuilder = JWT.create()
            .withIssuer(issuer)
            .withClaim("jti", UUID.randomUUID().toString())
            .withExpiresAt(Date.from(Instant.now().plusSeconds(300000)))
            .withIssuedAt(Date.from(Instant.now()));
        payload
            .entrySet()
            .forEach( pair -> 
                tokenBuilder.withClaim(pair.getKey(), pair.getValue())
                );
        return
            tokenBuilder
                .sign(Algorithm.RSA256((RSAPublicKey)keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate()));
    }

    public static boolean verifyToken(String token) throws JWTVerificationException {
        try{
            DecodedJWT jwt = JWT.decode(token);
            String issuer = jwt.getIssuer();
            Algorithm algorithm = Algorithm
                .RSA256(
                    publicKeyRecord.get(issuer), 
                    privateKeyRecord.get(issuer));
            JWTVerifier verifier = JWT
                .require(algorithm)
                .withIssuer(issuer)
                .build();
            verifier.verify(jwt);
            return true;
        }
        catch(JWTVerificationException e) {
            return false;
        }
    }
}
