package com.alura.foro_hub.controller;


import com.alura.foro_hub.dto.LoginRequestDTO;
import com.alura.foro_hub.dto.RegisterRequestDTO;
import com.alura.foro_hub.model.User;
import com.alura.foro_hub.service.TokenService;
import com.alura.foro_hub.service.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        try {
            // Autenticación
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Generación del token
            String token = tokenService.generateToken(authentication);

            return ResponseEntity.ok(new ResponseMessage("Authentication successful", token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseMessage("Invalid credentials. Please check your username and password.", null));
        }
    }
    // Endpoint para registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterRequestDTO registerRequest) {
        try {
            User newUser = userService.registerUser(
                    registerRequest.getUsername(),
                    registerRequest.getPassword(),
                    registerRequest.getEmail()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser); // Retorna el nuevo usuario
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Error de registro
        }
    }

    // Clase auxiliar para respuestas consistentes
    // Clase para la respuesta consistente
    private static class ResponseMessage {
        private String message;
        private String token;

        public ResponseMessage(String message, String token) {
            this.message = message;
            this.token = token;
        }

        public String getMessage() {
            return message;
        }

        public String getToken() {
            return token;
        }
    }



}

