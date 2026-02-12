package com.fitness.userservice.controller;

import com.fitness.userservice.dto.AutoCompleteTargetRequest;
import com.fitness.userservice.service.DailyTargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/daily-targets")
@RequiredArgsConstructor
public class DailyTargetInternalController {

    private final DailyTargetService dailyTargetService;

    @PostMapping("/auto-complete")
    public void autoCompleteTarget(@RequestBody AutoCompleteTargetRequest request) {
        dailyTargetService.autoCompleteTarget(
                request.getUserId(),
                request.getActivityType(),
                request.getDate()
        );
    }
}
