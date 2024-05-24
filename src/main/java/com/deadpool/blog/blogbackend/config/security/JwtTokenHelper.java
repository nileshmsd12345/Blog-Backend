package com.deadpool.blog.blogbackend.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    public static final Long JWT_TOKEN_VALIDITY = (long) (5 * 60 * 60);


    @Value("${jwt.secret}")
    private String jwtSecretBase64;

    //provide a strong key
    private Key jwtSecretKey;

    @PostConstruct
    public void init() {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(jwtSecretBase64);
            this.jwtSecretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
            System.out.println("JWT Secret Key successfully initialized.");
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid Base64-encoded key for JWT Secret.", e);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize JWT Secret Key.", e);
        }
    }



    //get Claims from token
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token).getBody();
    }

    //get username from token
    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    //get expiration date from token
    public Long getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration().getTime();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Boolean isTokenExpired(String token) {
        final Long expiration = getExpirationDateFromToken(token);
        return expiration < System.currentTimeMillis();
    }

    // generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        Key key = new SecretKeySpec(jwtSecretKey.getEncoded(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder().setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(key).compact();

    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
