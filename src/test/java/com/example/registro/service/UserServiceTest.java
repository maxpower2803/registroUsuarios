package com.example.registro.service;

import com.example.registro.dto.PhoneRequest;
import com.example.registro.dto.UserRequest;
import com.example.registro.dto.UserResponse;
import com.example.registro.entity.User;
import com.example.registro.exception.EmailAlreadyExistsException;
import com.example.registro.repository.UserRepository;
import com.example.registro.security.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Simulamos el valor de la propiedad inyectada
        userService = new UserService(userRepository, jwtUtil);
        // Forzamos el valor del regex directamente
        ReflectionTestUtils.setField(userService, "passwordRegex", "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d)[A-Za-z\\d]{8,}$");
    }

    private UserRequest createValidRequest() {
        PhoneRequest phone = new PhoneRequest();
        phone.setNumber("123456789");
        phone.setCitycode("2");
        phone.setCountrycode("56");

        UserRequest request = new UserRequest();
        request.setName("Max");
        request.setEmail("max@example.com");
        request.setPassword("Passw0rd1");
        request.setPhones(Collections.singletonList(phone));

        return request;
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        UserRequest request = createValidRequest();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(jwtUtil.generateToken(anyString())).thenReturn("mock-token");

        User.UserBuilder builder = User.builder();
        builder.id(UUID.randomUUID());
        builder.created(LocalDateTime.now());
        builder.modified(LocalDateTime.now());
        builder.lastLogin(LocalDateTime.now());
        builder.token("mock-token");
        builder.isActive(true);
        User mockUser = builder
                .build();

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        UserResponse response = userService.registerUser(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("mock-token", response.getToken());
        assertTrue(response.isActive());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        UserRequest request = createValidRequest();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.registerUser(request));
    }

    @Test
    void shouldThrowExceptionWhenPasswordInvalid() {
        UserRequest request = createValidRequest();
        request.setPassword("weak"); // No cumple con el regex

        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(request));
        assertTrue(ex.getMessage().contains("formato requerido"));
    }
}
