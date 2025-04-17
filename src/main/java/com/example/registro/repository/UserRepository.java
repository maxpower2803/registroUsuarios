package com.example.registro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.registro.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByEmail(String email);
}
