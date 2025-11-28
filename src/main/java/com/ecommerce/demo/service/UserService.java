package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.AuthResponse;
import com.ecommerce.demo.dto.user.*;
import com.ecommerce.demo.exception.AuthenticationException;
import com.ecommerce.demo.exception.DuplicateResourceException;
import com.ecommerce.demo.exception.ResourceNotFoundException;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.repository.UserRepository;
import com.ecommerce.demo.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;

    @Transactional
    public UserResponse registerUser(UserRegisterRequest request) {
        // Check for duplicate username or email
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateResourceException("Username '" + request.getUsername() + "' is already taken.");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email '" + request.getEmail() + "' is already registered.");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .mobileNo(request.getMobileNo())
                .role("USER")
                .build();

        User savedUser = userRepository.save(user);

        // Send verification email
        emailVerificationService.sendVerificationEmail(savedUser);

        return mapToResponse(savedUser);
    }

    public boolean verifyUser(String token) {
        return emailVerificationService.verifyUser(token);
    }

    public AuthResponse loginUser(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthenticationException("Invalid username or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid username or password.");
        }

        if (user.getIsVerified() == null || !user.getIsVerified()) {
            throw new AuthenticationException("Email has not been verified.");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return new AuthResponse(token, user.getUserId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    public UserResponse getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        return mapToResponse(user);
    }

    public UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setFullName(user.getFullName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setMobileNo(user.getMobileNo());
        response.setIsVerified(user.getIsVerified());
        response.setRole(user.getRole());
        return response;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
