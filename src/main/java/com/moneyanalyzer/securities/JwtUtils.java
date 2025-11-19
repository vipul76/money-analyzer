package com.moneyanalyzer.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    private final Key key;
    private final long jwtExpirationMs;


    public JwtUtils(@Value("${app.jwt.secret}") String secret,
                    @Value("${app.jwt.expiration-ms}") long jwtExpirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpirationMs = jwtExpirationMs;
    }

    // -------------------- TOKEN GENERATION --------------------

    // Creates a new JWT token for the given email (stored in the "sub" claim).

    public String generateToken(String email){
        Date now = new Date();
        Date exp = new Date(now.getTime()+jwtExpirationMs);

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    // -------------------- TOKEN VALIDATION --------------------

    // Validates the token by parsing and verifying its signature & expiration.
    // If parsing succeeds → token is valid. If any exception occurs → invalid.

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        }
        catch(JwtException | IllegalArgumentException ex){
            return false;
        }
    }

    // -------------------- CLAIM EXTRACTION --------------------

    // Extracts the "sub" (subject / email) from the token.

    public String getEmailFromToken(String token){
        return getClaimFromToken(token,Claims::getSubject);
    }

    // Generic method to extract any claim using a resolver function.
    // Example: getClaimFromToken(token, Claims::getExpiration)

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        Claims claims = parseClaims(token);
        return claimsResolver.apply(claims);
    }

    // Parses the token, verifies it, and returns all its claims (payload).

    private Claims parseClaims(String token) {
        return Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    }
}
