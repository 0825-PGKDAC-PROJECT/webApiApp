package com.gaadix.user.service;

import com.gaadix.common.dto.UserDTO;
import com.gaadix.common.entity.User;
import com.gaadix.common.enums.UserRole;
import com.gaadix.user.dto.AuthResponse;
import com.gaadix.user.dto.LoginRequest;
import com.gaadix.user.dto.RegisterRequest;
import com.gaadix.user.repository.UserRepository;
import com.gaadix.user.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john@gaadix.com");
        registerRequest.setPassword("password123");
        registerRequest.setPhone("9876543210");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("john@gaadix.com");
        loginRequest.setPassword("password123");

        user = User.builder()
                .email("john@gaadix.com")
                .password("encodedPassword")
                .firstName("John")
                .lastName("Doe")
                .phone("9876543210")
                .roles(Set.of(UserRole.BUYER))
                .isActive(true)
                .accountLocked(false)
                .failedLoginAttempts(0)
                .build();
        user.setId(1L);

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("john@gaadix.com");
    }

    @Test
    void register_Success() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByPhone(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtUtil.generateToken(anyString())).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(anyString())).thenReturn("refreshToken");
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertNotNull(response.getUser());
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("password123");
    }

    @Test
    void register_EmailAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_PhoneAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByPhone(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_WithCustomRoles() {
        registerRequest.setRoles(Set.of(UserRole.SELLER));
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByPhone(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtUtil.generateToken(anyString())).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(anyString())).thenReturn("refreshToken");
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(jwtUtil.generateToken(anyString())).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(anyString())).thenReturn("refreshToken");
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);
        when(userRepository.save(any(User.class))).thenReturn(user);

        AuthResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        verify(userRepository).findByEmail("john@gaadix.com");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void login_UserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    void login_AccountLocked() {
        user.setAccountLocked(true);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    void login_AccountInactive() {
        user.setActive(false);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    void login_InvalidCredentials_IncrementFailedAttempts() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_FifthFailedAttempt_LocksAccount() {
        user.setFailedLoginAttempts(4);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        verify(userRepository).save(argThat(u -> u.isAccountLocked()));
    }

    @Test
    void refreshToken_Success() {
        when(jwtUtil.validateToken(anyString())).thenReturn(true);
        when(jwtUtil.extractEmail(anyString())).thenReturn("john@gaadix.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(anyString())).thenReturn("newAccessToken");
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        AuthResponse response = authService.refreshToken("oldRefreshToken");

        assertNotNull(response);
        assertEquals("newAccessToken", response.getAccessToken());
        assertEquals("oldRefreshToken", response.getRefreshToken());
        verify(jwtUtil).validateToken("oldRefreshToken");
    }

    @Test
    void refreshToken_InvalidToken() {
        when(jwtUtil.validateToken(anyString())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.refreshToken("invalidToken"));
        verify(jwtUtil, never()).extractEmail(anyString());
    }

    @Test
    void refreshToken_UserNotFound() {
        when(jwtUtil.validateToken(anyString())).thenReturn(true);
        when(jwtUtil.extractEmail(anyString())).thenReturn("john@gaadix.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.refreshToken("validToken"));
    }
}
