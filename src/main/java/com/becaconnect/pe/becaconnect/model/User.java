package com.becaconnect.pe.becaconnect.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@ToString

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    // Método personalizado para obtener el rol en el formato Spring Security
    public String getSpringRole() {
        return this.role.replace("ROLE_", ""); // Devuelve el rol sin el prefijo "ROLE_"
    }

}
