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
        // Generate token and expiry
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setVerificationTokenExpiry(LocalDateTime.now().plusMinutes(tokenExpiryMinutes));
        userRepository.save(user);

        String to = user.getEmail();
        String link = verificationUrl + token;

        String body = "Dear " + user.getFullName() + ",\n\n"
                + "Please verify your email by clicking the link below:\n"
                + link + "\n\n"
                + "This link will expire in " + tokenExpiryMinutes + " minutes.\n\n"
                + "Thank you,\nYour Ecommerce Team";

        sendEmail(to, emailSubject, body);
    }

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
        Optional<User> optionalUser = userRepository.findByVerificationToken(token);

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();

        if (user.getIsVerified()) {
            return false;
        }

        if (user.getVerificationTokenExpiry() == null ||
                user.getVerificationTokenExpiry().isBefore(LocalDateTime.now())) {
            return false;
        }

        user.setIsVerified(true);
        user.setVerificationToken(null);
        user.setVerificationTokenExpiry(null);
        userRepository.save(user);

        return true;
    }
}
