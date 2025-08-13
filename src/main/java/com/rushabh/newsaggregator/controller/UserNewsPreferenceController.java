package com.rushabh.newsaggregator.controller;

import com.rushabh.newsaggregator.dto.Request.AddUserNewsPreference;

import com.rushabh.newsaggregator.dto.Response.UserNewsPreferenceResponse;
import com.rushabh.newsaggregator.dto.Response.ApiResponse;
import com.rushabh.newsaggregator.service.UserNewsPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/preferences")
public class UserNewsPreferenceController {

    @Autowired
    UserNewsPreferenceService userNewsPreferenceService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserNewsPreferenceResponse>> addUserNewPreference(@RequestBody AddUserNewsPreference req) {
        UserNewsPreferenceResponse preference = userNewsPreferenceService.addUserNewsPreference(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Success", "User news preference added!", preference));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserNewsPreferenceResponse>> fetchUserNewsPreference() {
        UserNewsPreferenceResponse preference = userNewsPreferenceService.fetchUserNewsPreference();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Success", "User news preference added!", preference));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserNewsPreferenceResponse>> updateUserNewsPreference(@RequestBody AddUserNewsPreference req) {
        UserNewsPreferenceResponse preference = userNewsPreferenceService.updateUserNewsPreference(req);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Success", "User news preference updated!", preference));
    }


}
