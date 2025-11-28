package com.ecommerce.demo.service;

import com.ecommerce.demo.model.User;
import com.ecommerce.demo.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Value("${app.verification.token.expiry-minutes:30}")
    private int tokenExpiryMinutes;

    @Value("${app.verification.email.from:no-reply@yourdomain.com}")
    private String emailFrom;

    @Value("${app.verification.email.subject:Verify your email}")
    private String emailSubject;

    @Value("${app.frontend.verify-url:http://localhost:8080/api/user/verify?token=}")
    private String verificationUrl;

    public void sendVerificationEmail(User user) {
        // For testing purposes, we'll bypass email sending and auto-verify the user.
        // In a real application, you would generate a token and send an email.
        user.setIsVerified(true);
        user.setVerificationToken(null);
        user.setVerificationTokenExpiry(null);
        userRepository.save(user);
    }

    /*
    private void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(emailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }
    */

    public boolean verifyUser(String token) {
        // Bypassed for testing.
        return true;
    }
}
