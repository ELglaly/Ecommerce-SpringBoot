package com.example.ecommerce.security.jwt;

import com.example.ecommerce.entity.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class JwtService {



    private static String secretKey="";
    public JwtService() {
        {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
                SecretKey sk= keyGenerator.generateKey();
                secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
            }
             catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRoles());
        claims.put("id", user.getId());
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(getKey())
                .compact();

    }

    public static SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }
    public Long extractUserId(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().get("id", Long.class);
    }
    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().get("email", String.class);
    }
    public Collection extractRole(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().get("role", Collection.class);
    }

    public boolean validateToken(String token, String usernameOrEmail) {
        return usernameOrEmail.equals(extractUsername(token)) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }
}
