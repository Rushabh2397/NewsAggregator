package com.rushabh.newsaggregator.controller;

import com.rushabh.newsaggregator.dto.Request.LoginUser;
import com.rushabh.newsaggregator.dto.Request.RegisterUser;
import com.rushabh.newsaggregator.dto.Response.ApiResponse;
import com.rushabh.newsaggregator.dto.Response.LoginUserResponse;
import com.rushabh.newsaggregator.dto.Response.RegisterUserResponse;
import com.rushabh.newsaggregator.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterUserResponse>> registerUser(@Valid @RequestBody RegisterUser user) {
        RegisterUserResponse userDetails = authService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Success", "User Registered!", userDetails));
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<String>> verifyUser(@RequestParam String token) {
       String tokenVerificationMsg = authService.verifyUser(token);
       return ResponseEntity.status(200).body(new ApiResponse<>("Success",tokenVerificationMsg,null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginUserResponse>> login(@Valid @RequestBody LoginUser user){
        LoginUserResponse res = authService.login(user);
        return  ResponseEntity.status(200).body(new ApiResponse<>("Success","User logged in!",res));
    }
}
