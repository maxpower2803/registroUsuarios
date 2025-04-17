package com.example.registro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.registro.dto.UserRequest;
import com.example.registro.dto.UserResponse;
import com.example.registro.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/registro")
	public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
		return ResponseEntity.ok(userService.registerUser(request));
	}

}
