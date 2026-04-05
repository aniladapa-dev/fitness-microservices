package com.fitness.gateway.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
/**
 * Internal UserService client within the API Gateway.
 * - Makes WebClient calls to the USER-SERVICE to check if a user is already recorded.
 * - Triggers backend user registration if the incoming JWT reveals a new Keycloak User.
 */
public class UserService {

    private final WebClient userServiceWebClient;

    // Check if user exists by keycloakId
    public Mono<Boolean> validateUser(String keycloakId) {
        log.info("Validating user existence for keycloakId={}", keycloakId);

        return userServiceWebClient.get()
                .uri("/api/users/{userId}/validate", keycloakId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorReturn(false); // if error, treat as not exists
    }

    // Sync user from Keycloak into DB
    public Mono<UserResponse> syncUser(KeycloakSyncRequest request) {
        log.info("Syncing user from Keycloak: {}", request.getEmail());

        return userServiceWebClient.post()
                .uri("/api/users/register") // route unchanged
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("User sync failed: status={}, body={}",
                            e.getStatusCode(), e.getResponseBodyAsString());
                    return Mono.error(e);
                });
    }
}
