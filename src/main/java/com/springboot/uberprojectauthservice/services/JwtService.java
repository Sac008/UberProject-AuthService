package com.springboot.uberprojectauthservice.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.flywaydb.core.FlywayExecutor;
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
    private String createToken(Map<String , Object> payload , String username) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + expiry*1000L);
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationTime).subject(username)
                .signWith(key)
                .compact();
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String , Object> mp =new HashMap<>();
        mp.put("email" , "a@b.com");
        mp.put("phoneNumber" , "890808098080");
        String result = createToken(mp , "sachin");
        System.out.println(result);
    }
}
