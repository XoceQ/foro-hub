package com.alura.foro_hub.controller;

import com.alura.foro_hub.domain.user.User;
import com.alura.foro_hub.domain.user.UserRepository;
import com.alura.foro_hub.domain.user.dtos.DtoAunthetication;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody @Valid DtoAunthetication dtoAunthetication) {
        // Verifica si el nombre de usuario ya está registrado
        if (userRepository.findByUsername(dtoAunthetication.username()) != null) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }

        // Crea un nuevo usuario y guarda los datos
        User newUser = new User();
        newUser.setUsername(dtoAunthetication.username());
        newUser.setPassword(new BCryptPasswordEncoder().encode(dtoAunthetication.password())); // Encriptamos la contraseña

        // Guarda el usuario en la base de datos
        userRepository.save(newUser);

        // Devuelve un mensaje de éxito
        return ResponseEntity.ok("User registered successfully.");
    }
}