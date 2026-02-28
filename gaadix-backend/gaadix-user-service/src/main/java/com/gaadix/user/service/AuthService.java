package com.gaadix.user.service;

import com.gaadix.common.dto.UserDTO;
import com.gaadix.common.entity.User;
import com.gaadix.common.enums.UserRole;
import com.gaadix.common.exception.BadRequestException;
import com.gaadix.common.exception.UnauthorizedException;
import com.gaadix.user.dto.AuthResponse;
import com.gaadix.user.dto.LoginRequest;
import com.gaadix.user.dto.RegisterRequest;
import com.gaadix.user.repository.UserRepository;
import com.gaadix.user.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }
        
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("Phone already registered");
        }
        
        Set<UserRole> roles = request.getRoles() != null && !request.getRoles().isEmpty() 
            ? request.getRoles() 
            : Set.of(UserRole.BUYER);
        
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .dob(request.getDob())
                .roles(roles)
                .isActive(true)
                .accountLocked(false)
                .failedLoginAttempts(0)
                .build();
        
        user = userRepository.save(user);
        
        String accessToken = jwtUtil.generateToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(modelMapper.map(user, UserDTO.class))
                .build();
    }
    
    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        
        if (user.isAccountLocked()) {
            throw new UnauthorizedException("Account is locked");
        }
        
        if (!user.isActive()) {
            throw new UnauthorizedException("Account is inactive");
        }
        
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            
            user.setFailedLoginAttempts(0);
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);
            
            String accessToken = jwtUtil.generateToken(user.getEmail());
            String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
            
            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .user(modelMapper.map(user, UserDTO.class))
                    .build();
                    
        } catch (Exception e) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            if (user.getFailedLoginAttempts() >= 5) {
                user.setAccountLocked(true);
            }
            userRepository.save(user);
            throw new UnauthorizedException("Invalid credentials");
        }
    }
    
    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        
        String email = jwtUtil.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        String newAccessToken = jwtUtil.generateToken(email);
        
        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .user(modelMapper.map(user, UserDTO.class))
                .build();
    }
}
