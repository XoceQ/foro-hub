package com.alura.foro_hub.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private String secretKey = "secret"; // Reemplaza con tu clave secreta real

    // Método para validar el token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Método para obtener la autenticación a partir del token
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String username = claims.getSubject(); // El nombre de usuario (o el principal)

        // Usando org.springframework.security.core.userdetails.User en lugar de tu clase User
        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User(username, "", new ArrayList<>()); // Crear un usuario básico
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    // Método para generar un token (opcional, si lo necesitas)
    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hora de validez

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}