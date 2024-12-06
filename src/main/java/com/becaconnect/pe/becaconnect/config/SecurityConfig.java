package com.becaconnect.pe.becaconnect.config;

import com.becaconnect.pe.becaconnect.model.User;
import com.becaconnect.pe.becaconnect.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found: " + username));

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword()) // Contraseña ya cifrada con BCrypt
                    .roles(user.getSpringRole())
                    .build();
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configurar CORS
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Permitir la creación de sesiones
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/logout","/auth/register","/api/posts/{id}/like").permitAll() // Permitir acceso público
                        .requestMatchers(HttpMethod.GET, "/api/posts", "/api/posts/{id}", "/api/posts/{id}/comments").permitAll() // Ver posts y comentarios
                        .requestMatchers(HttpMethod.POST, "/api/posts/{id}/comments").permitAll() // Permitir comentarios para todos
                        .requestMatchers(HttpMethod.POST, "/api/posts/create").hasRole("ADMIN") // Solo ADMIN puede crear
                        .requestMatchers(HttpMethod.PUT, "/api/posts/**").hasRole("ADMIN") // Solo ADMIN puede actualizar
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/**").hasRole("ADMIN") // Solo ADMIN puede eliminar
                        .anyRequest().authenticated()) // Cualquier otra solicitud requiere autenticación
                .httpBasic(withDefaults()); // Usar autenticación básica HTTP

        return http.build();
    }




    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
