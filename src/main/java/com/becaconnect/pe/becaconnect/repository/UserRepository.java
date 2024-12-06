package com.becaconnect.pe.becaconnect.repository;

import com.becaconnect.pe.becaconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
