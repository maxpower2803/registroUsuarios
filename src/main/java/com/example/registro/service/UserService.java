package com.example.registro.service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.registro.dto.UserRequest;
import com.example.registro.dto.UserResponse;
import com.example.registro.entity.Phone;
import com.example.registro.entity.User;
import com.example.registro.exception.EmailAlreadyExistsException;
import com.example.registro.repository.UserRepository;
import com.example.registro.security.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    
    @Value("${password.regex}")
    private String passwordRegex;
    
    @Transactional
    public UserResponse registerUser(UserRequest request) {
    	if(!request.getPassword().matches(passwordRegex)) {
    		throw new IllegalArgumentException("Contraseña no cumple con el formato requerido.");
    	}
    	
    	userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
    		throw new EmailAlreadyExistsException("El correo ya se encuentra registrado");
    	});
    	
    	String token = jwtUtil.generateToken(request.getEmail());
    	LocalDateTime now = LocalDateTime.now();
    	
    	User user = User.builder()
    			.name(request.getName())
    			.email(request.getEmail())
    			.password(request.getPassword())
    			.created(now)
    			.modified(now)
    			.lastLogin(now)
    			.token(token)
    			.isActive(true)
    			.phones(request.getPhones().stream().map(p -> Phone.builder()
    					.number(p.getNumber())
    					.citycode(p.getCitycode())
    					.countrycode(p.getCountrycode())
    					.build()).collect(Collectors.toList()))
    			.build();
    	
    	User saved = userRepository.save(user);
    	
		return UserResponse.builder()
                .id(saved.getId())
                .created(saved.getCreated())
                .modified(saved.getModified())
                .lastLogin(saved.getLastLogin())
                .token(saved.getToken())
                .isActive(saved.isActive())
                .build();
    	
    }
}
