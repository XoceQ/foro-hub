package com.alura.foro_hub.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {


    // Utilizamos una clave segura generada con Keys.hmacShaKeyFor
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 24 horas

    // Método para generar el token JWT
    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY) // Usamos la clave segura
                .compact();
    }

    // Método para validar el token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token); // Reemplazamos 'secretKey' por 'SECRET_KEY'
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Método para obtener el nombre de usuario del token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Reemplazamos 'secretKey' por 'SECRET_KEY'
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}