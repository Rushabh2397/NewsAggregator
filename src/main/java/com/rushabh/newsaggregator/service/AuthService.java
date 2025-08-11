package com.rushabh.newsaggregator.service;

import com.rushabh.newsaggregator.dto.Request.LoginUser;
import com.rushabh.newsaggregator.dto.Request.RegisterUser;
import com.rushabh.newsaggregator.dto.Response.LoginUserResponse;
import com.rushabh.newsaggregator.dto.Response.RegisterUserResponse;
import com.rushabh.newsaggregator.entity.Role;
import com.rushabh.newsaggregator.entity.User;
import com.rushabh.newsaggregator.entity.VerificationToken;
import com.rushabh.newsaggregator.repository.RoleRepository;
import com.rushabh.newsaggregator.repository.UserRepository;
import com.rushabh.newsaggregator.repository.VerificationTokenRepository;
import com.rushabh.newsaggregator.util.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Value("${jwt.secret}")
    private String secret;

    @Transactional
    public RegisterUserResponse registerUser(RegisterUser user) {
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found while registering"));

        User newUser = new User(
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone()
        );

        newUser.getRoles().add(role);
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

    public LoginUserResponse login(LoginUser user) {
        Optional<User> userDetails = userRepository.findByEmail(user.getEmail());
        if (userDetails.isEmpty()) {
            throw new RuntimeException("Username or Password is incorrect");
        }

        if (!passwordEncoder.matches(user.getPassword(), userDetails.get().getPassword())) {
            throw new RuntimeException("Username or Password is incorrect");
        }

        User userEntity = userDetails.get();
        String token = JwtUtils.generateToken(userEntity, secret);

        return new LoginUserResponse(
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                token
        );

    }
}
