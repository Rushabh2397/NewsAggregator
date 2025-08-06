package com.rushabh.newsaggregator.service;

import com.rushabh.newsaggregator.dto.Request.RegisterUser;
import com.rushabh.newsaggregator.dto.Response.RegisterUserResponse;
import com.rushabh.newsaggregator.entity.User;
import com.rushabh.newsaggregator.entity.VerificationToken;
import com.rushabh.newsaggregator.repository.UserRepository;
import com.rushabh.newsaggregator.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterUserResponse registerUser(RegisterUser user) {
        User newUser = new User(
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone()
        );
        User savedUser = userRepository.save(newUser);
        String verificationToken = generateVerificationToken(savedUser);
        System.out.println("Verification Token: " + verificationToken);

        return new RegisterUserResponse(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getPhone(),
                savedUser.getEmail()

        );

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(
                token,
                user,
                LocalDateTime.now().plusHours(24)
        );
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Transactional
    public String verifyUser(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken.isEmpty()) {
            return "Token has expired!";
        }
        VerificationToken tokenEntity = verificationToken.get();
        if (tokenEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "Token has expired!";
        }

        User user = tokenEntity.getUser();
        user.setActive(true);
        user.setIsEmailVerified(true);

        userRepository.save(user);
        verificationTokenRepository.deleteById(tokenEntity.getId());

        return "User verified successfully";
    }
}
