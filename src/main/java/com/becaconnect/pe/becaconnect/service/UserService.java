package com.becaconnect.pe.becaconnect.service;

import com.becaconnect.pe.becaconnect.model.User;
import com.becaconnect.pe.becaconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @PostConstruct
    public void initDefaultUsers() {
        // Crear usuario admin
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin1"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
        }

        // Crear usuario guest
        if (userRepository.findByUsername("cris").isEmpty()) {
            User cris = new User();
            cris.setUsername("cris");
            cris.setPassword(passwordEncoder.encode("cris1"));
            cris.setRole("ROLE_USER");
            userRepository.save(cris);
        }
    }


    // Verifica si un usuario ya existe por el nombre de usuario
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Guarda un nuevo usuario
    public void save(User user) {
        userRepository.save(user);
    }

    // Registra un nuevo usuario con la contraseña encriptada
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");  // Asigna el rol por defecto como 'ROLE_USER'
        return userRepository.save(user);
    }

    // Encuentra un usuario por su nombre de usuario
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Valida la contraseña del usuario
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
