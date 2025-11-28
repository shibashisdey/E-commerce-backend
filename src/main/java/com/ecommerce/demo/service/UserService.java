package com.ecommerce.demo.service;



import com.ecommerce.demo.dto.user.UserRegisterRequest;
import com.ecommerce.demo.dto.user.UserResponse;
import com.ecommerce.demo.dto.user.*;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;

    @Transactional
    public UserResponse registerUser(UserRegisterRequest request) {
        User user = User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .mobileNo(request.getMobileNo())
                .role("USER")
                .isVerified(false)
                .build();

        userRepository.save(user);

        // Send verification email
        emailVerificationService.sendVerificationEmail(user);

        return mapToResponse(user);
    }

    public boolean verifyUser(String token) {
        return emailVerificationService.verifyUser(token);
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
    public UserResponse loginUser(UserLoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid username or password");
        }

        User user = userOpt.get();

        if (!user.getIsVerified()) {
            throw new RuntimeException("Email is not verified");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // Convert entity to DTO before returning
        return mapToResponse(user);
    }

    public UserResponse getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return mapToResponse(user);
    }

}
