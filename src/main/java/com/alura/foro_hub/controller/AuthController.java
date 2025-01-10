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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        try {
            // Verificación del usuario (logging para depuración)
            System.out.println("Verificando usuario: " + loginRequest.getUsername());
            User user = userService.findByUsername(loginRequest.getUsername());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid username. Please check your username.");
            }

            // Autenticación (logging para depuración)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Generación del token
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(token);

        } catch (AuthenticationException e) {
            // Logging de excepción
            System.out.println("Error en la autenticación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials. Please check your username and password.");
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
}

