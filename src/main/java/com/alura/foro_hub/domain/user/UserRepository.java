package com.alura.foro_hub.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);
    Optional<User> findById(Long id);  // Método para encontrar un User por ID

}