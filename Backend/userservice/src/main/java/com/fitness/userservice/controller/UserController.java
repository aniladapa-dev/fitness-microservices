package com.fitness.userservice.controller;

import com.fitness.userservice.dto.*;
import com.fitness.userservice.model.KeycloakSyncRequest;
import com.fitness.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // INTERNAL: Gateway-only
    // Old name: register
    // New meaning: sync from Keycloak
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody KeycloakSyncRequest request) {
        return ResponseEntity.ok(userService.syncFromKeycloak(request));
    }

    // PUBLIC (secured)
    // userId == keycloakId
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(
            @PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    // INTERNAL / PUBLIC (used by gateway)
    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUser(
            @PathVariable String userId) {
        return ResponseEntity.ok(userService.existsByKeycloakId(userId));
    }
}

