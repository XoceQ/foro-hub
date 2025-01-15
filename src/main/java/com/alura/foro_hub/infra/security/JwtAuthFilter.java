package com.alura.foro_hub.infra.security;

import com.alura.foro_hub.domain.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;  // Repositorio de usuarios, ajusta según la estructura de tu dominio

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtener el token del header
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);  // Extraer el token sin "Bearer "

            try {
                String username = tokenService.getSubject(token);  // Extraer el nombre de usuario del token

                if (username != null) {
                    // Buscar el usuario en la base de datos
                    var user = userRepository.findByUsername(username);  // Ajusta según tu repositorio y método de búsqueda

                    if (user != null) {
                        // Crear un objeto de autenticación con el usuario encontrado
                        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);  // Establecer la autenticación en el contexto de seguridad
                    }
                }

            } catch (RuntimeException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Si el token es inválido, devolver 401
                return;
            }
        }

        filterChain.doFilter(request, response);  // Continuar con la cadena de filtros
    }
}