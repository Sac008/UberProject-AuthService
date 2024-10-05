package com.springboot.uberprojectauthservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    /*
        This method creates a brand-new JWT token for us based on provided payload.
    */
    private String createToken(Map<String , Object> payload , String email) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + expiry*1000L);
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationTime).subject(email)
                .signWith(key)
                .compact();
    }

    private Claims extractAllPayload(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Date extractExpiration(String token) {
        Claims claims = extractAllPayload(token);
        return claims.getExpiration();
    }

//    private String extractEmail(String token) {
//        Claims claims = extractAllPayload(token);
//        return claims.getSubject();
//    }

    private Object extractParticularPayload(String token , String payloadProperty) {
        Claims claims = extractAllPayload(token);
        return claims.get(payloadProperty, Object.class);
    }


    /*
        This method checks if the token expiry was before the current time stamp or not
        returns true if token is expired or false.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private Boolean validateToken(String token , String email) {
        final String userEmailFetchedFromToken = extractParticularPayload(token , email).toString();
        return userEmailFetchedFromToken.equals(email) && !isTokenExpired(token);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String , Object> mp =new HashMap<>();
        mp.put("email" , "a@b.com");
        mp.put("phoneNumber" , "890808098080");
        String result = createToken(mp , "sachin@google.com");
        System.out.println(extractParticularPayload(result , "email").toString() + " ===================================");
    }
}
