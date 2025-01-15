package com.alura.foro_hub.controller;

import com.alura.foro_hub.domain.profile.Profile;
import com.alura.foro_hub.domain.profile.ProfileRepository;
import com.alura.foro_hub.domain.profile.dtos.DtoListProfile;
import com.alura.foro_hub.domain.profile.dtos.DtoRegisterProfile;
import com.alura.foro_hub.domain.profile.dtos.DtoUpdateProfile;
import com.alura.foro_hub.domain.user.User;
import com.alura.foro_hub.domain.user.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "User", description = "CRUD operations on the user-profile entity")
public class ProfileController {


    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @Operation(summary = "Gets all users")
    public ResponseEntity<Page<DtoListProfile>> listProfiles(@PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(profileRepository.findByActiveTrue(pageable).map(this::conversorToDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets the registration of a user by ID")
    public ResponseEntity<DtoListProfile> findProfileById(@PathVariable Long id) {
        Profile profile = profileRepository.getReferenceById(id);
        return ResponseEntity.ok(conversorToDTO(profile));
    }

    @PostMapping
    @Operation(summary = "Register a user in the database")
    public ResponseEntity<DtoListProfile> createProfile(@RequestBody @Valid DtoRegisterProfile dtoRegisterProfile, UriComponentsBuilder uriComponentsBuilder) {
        // Verificar si el correo ya está registrado
        if (profileRepository.existsByEmail(dtoRegisterProfile.email())) {
            return ResponseEntity.badRequest().body(new DtoListProfile(null, null, "Email already exists", null, null, Collections.emptyList()));
        }

        // Obtener el usuario con el id_user proporcionado
        User user = userRepository.findById(dtoRegisterProfile.id_user())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Crear el perfil y asignar el usuario al campo id_user
        Profile profile = new Profile(null, dtoRegisterProfile.name(), dtoRegisterProfile.email(), true, user, null);
        profileRepository.save(profile);

        // Convertir el perfil a DTO
        DtoListProfile dtoListProfile = conversorToDTO(profile);

        // Construir la URI de respuesta
        URI url = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(profile.getId()).toUri();
        return ResponseEntity.created(url).body(dtoListProfile);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Update a user's data")
    public ResponseEntity<DtoListProfile> updateProfile(@RequestBody @Valid DtoUpdateProfile dtoUpdateProfile) {
        Profile profile = profileRepository.getReferenceById(dtoUpdateProfile.id());
        profile.updateData(dtoUpdateProfile);
        DtoListProfile dtoListProfile = conversorToDTO(profile);
        return ResponseEntity.ok(dtoListProfile);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Mark the user as inactive")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        Profile profile = profileRepository.getReferenceById(id);
        profile.deactivateProfile();
        return ResponseEntity.noContent().build();
    }

    private DtoListProfile conversorToDTO(Profile profile) {
        // Verifica si el usuario es null
        Long userId = (profile.getUser() != null) ? profile.getUser().getId() : null;

        // Solo se incluyen los datos necesarios en el DTO
        return new DtoListProfile(profile.getId(), profile.getName(), profile.getEmail(),
                userId, profile.getActive(), Collections.emptyList());
    }

}