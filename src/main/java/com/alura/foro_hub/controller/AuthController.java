package com.alura.foro_hub.controller;


import com.alura.foro_hub.domain.user.User;
import com.alura.foro_hub.domain.user.dtos.DtoAunthetication;
import com.alura.foro_hub.infra.security.DtoJwtToken;
import com.alura.foro_hub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;


import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
@Tag(name = "Autentication", description = "obtains the token for the assigned user that gives access to the other endpoints")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity authenticateUser(@RequestBody @Valid DtoAunthetication dtoAunthetication) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(dtoAunthetication.username(), dtoAunthetication.password());
        var userAuth = authenticationManager.authenticate(authToken);
        var jwtToken = tokenService.generateToken((User) userAuth.getPrincipal());
        return ResponseEntity.ok(new DtoJwtToken(jwtToken));
    }


}