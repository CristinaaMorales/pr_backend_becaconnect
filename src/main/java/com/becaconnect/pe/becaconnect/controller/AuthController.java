package com.becaconnect.pe.becaconnect.controller;

import com.becaconnect.pe.becaconnect.model.User;
import com.becaconnect.pe.becaconnect.service.AuditLogService;
import com.becaconnect.pe.becaconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User loginRequest) {
        Optional<User> user = userService.findByUsername(loginRequest.getUsername());

        if (user.isPresent() && userService.validatePassword(loginRequest.getPassword(), user.get().getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("username", user.get().getUsername());
            response.put("role", user.get().getRole());
            response.put("userId", String.valueOf(user.get().getId()));

            // Registrar el evento de login (incluyendo el rol)
            auditLogService.logEvent(user.get().getUsername(), user.get().getRole(), "Login");

            // Imprimir en consola el evento de login
            System.out.println("[LOGIN EVENT] User: " + user.get().getUsername() + " Role: " + user.get().getRole());

            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid credentials");
            System.out.println("[LOGIN EVENT FAILED] Invalid credentials for username: " + loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestBody User logoutRequest) {
        System.out.println("[LOGOUT REQUEST RECEIVED] Username: " + logoutRequest.getUsername());
        Optional<User> user = userService.findByUsername(logoutRequest.getUsername());

        if (user.isPresent()) {
            String username = user.get().getUsername();
            String role = user.get().getRole();

            // Registrar el evento de logout
            auditLogService.logEvent(username, role, "Logout");

            // Imprimir en consola el evento de logout
            System.out.println("[LOGOUT EVENT] User: " + username + " Role: " + role);

            return ResponseEntity.ok(Map.of("message", "Logout successful"));
        } else {
            System.out.println("[LOGOUT EVENT FAILED] User not found: " + logoutRequest.getUsername());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }
    }

    // Método para registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Verificar si el nombre de usuario ya existe
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario ya está en uso.");
        }

        // Encriptar la contraseña antes de guardarla
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Asignar el rol por defecto como "USER"
        user.setRole("ROLE_USER");

        // Guardar el usuario en la base de datos
        userService.save(user);

        // Respuesta tras un registro exitoso
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuario registrado exitosamente");
        response.put("username", user.getUsername());
        response.put("role", user.getRole());
        return ResponseEntity.ok(response);
    }

}
