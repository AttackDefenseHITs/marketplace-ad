package com.example.springauth.jwt;

import com.example.springauth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    public String generateToken(User user){
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());
        UUID tokenId = UUID.randomUUID();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId().toString())
                .claim("role", user.getRole().toString())
                .setId(tokenId.toString())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .compact();
    }

    public UUID getUserIdFromToken(String token){
        String userId = getAllClaimsFromToken(token).get("userId", String.class);
        return UUID.fromString(userId);
    }

    public long getRemainingTimeInMillis(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        long currentTimeInMillis = System.currentTimeMillis();
        return expirationDate.getTime() - currentTimeInMillis;
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public String getIdFromToken(String token) {
        return getAllClaimsFromToken(token).getId();
    }

    public UUID getUserIdFromAuthentication(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            if (userDetails instanceof User) {
                return ((User) userDetails).getId();
            }
        }
        throw new IllegalStateException("Cannot extract user ID from Authentication object");
    }

    Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }
}