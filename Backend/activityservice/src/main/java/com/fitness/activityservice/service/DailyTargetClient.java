package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.AutoCompleteTargetRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
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
