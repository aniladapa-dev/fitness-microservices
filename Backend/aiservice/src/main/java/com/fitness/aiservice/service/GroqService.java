package com.fitness.aiservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
/**
 * WebClient integration service for the Groq AI API.
 * - Configures authorization tokens and API URLs.
 * - Sends prompt details to the 'llama-3.1-8b-instant' model.
 * - Blocks and returns the AI's string response.
 */
public class GroqService {

    private final WebClient webClient;

    public GroqService(
            WebClient.Builder webClientBuilder,
            @Value("${groq.api.key}") String apiKey
    ) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.groq.com/openai/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String getAnswer(String prompt) {

        Map<String, Object> body = Map.of(
                "model", "llama-3.1-8b-instant",
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );

        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
