package com.example.Ecommerce.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    public static String generateToken(String usernameOrEmail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("usernameOrEmail", usernameOrEmail);
        return Jwts.builder()
                .claims(claims)
                .subject(usernameOrEmail)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 10))
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
