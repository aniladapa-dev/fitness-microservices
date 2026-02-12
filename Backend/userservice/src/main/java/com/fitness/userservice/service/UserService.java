package com.fitness.userservice.service;


import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.KeycloakSyncRequest;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse syncFromKeycloak(KeycloakSyncRequest request) {

        return userRepository.findByKeycloakId(request.getKeycloakId())
                .map(this::toResponse)
                .orElseGet(() -> {
                    log.info("Creating new user from Keycloak: {}", request.getEmail());

                    User user = new User();
                    user.setKeycloakId(request.getKeycloakId());
                    user.setEmail(request.getEmail());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());

                    return toResponse(userRepository.save(user));
                });
    }

    public UserResponse getUserProfile(String keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return toResponse(user);
    }

    private UserResponse toResponse(User user) {
        UserResponse res = new UserResponse();
        res.setId(user.getId());
        res.setKeycloakId(user.getKeycloakId());
        res.setEmail(user.getEmail());
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        res.setRole(user.getRole());
        res.setCreatedAt(user.getCreatedAt());
        res.setUpdatedAt(user.getUpdatedAt());
        return res;
    }

    public Boolean existsByKeycloakId(String userId) {
        return userRepository.existsByKeycloakId(userId);
    }

    public User getByKeycloakId(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
