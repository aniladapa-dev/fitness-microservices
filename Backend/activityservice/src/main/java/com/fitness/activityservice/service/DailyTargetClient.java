package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.AutoCompleteTargetRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
/**
 * WebClient service for inter-service communication with User Service.
 * - Centralizes HTTP calls related to Daily Targets.
 * - Exposes the auto-completion endpoint used when logging an activity.
 * - Operates synchronously to ensure operations finalize predictably.
 */
public class DailyTargetClient {

    private final WebClient userServiceWebClient;

    public void autoCompleteTarget(AutoCompleteTargetRequest request) {
        log.info("Auto-completing daily target for user {}", request.getUserId());

        userServiceWebClient.post()
                .uri("/internal/daily-targets/auto-complete")
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block(); // ✅ intentional blocking
    }
}
