package com.fitness.userservice.controller;

import com.fitness.userservice.dto.AddTargetRequest;
import com.fitness.userservice.dto.DailyTargetResponse;
import com.fitness.userservice.service.DailyTargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/daily-targets")
@RequiredArgsConstructor
public class DailyTargetController {

    private final DailyTargetService service;

    @GetMapping("/today")
    public List<DailyTargetResponse> getTodayTargets(
            @PathVariable String userId
    ) {
        return service.getTodayTargets(userId);
    }

    @PostMapping("/today")
    public DailyTargetResponse addTarget(
            @PathVariable String userId,
            @RequestBody AddTargetRequest request
    ) {
        return service.addTarget(
                userId,
                request.getActivityType(),
                request.getLabel()
        );
    }

    @PatchMapping("/{id}/toggle")
    public DailyTargetResponse toggleTarget(
            @PathVariable String userId,
            @PathVariable Long id
    ) {
        return service.toggleTarget(id, userId);
    }

    @DeleteMapping("/today")
    public void resetToday(
            @PathVariable String userId
    ) {
        service.resetToday(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteTarget(
            @PathVariable String userId,
            @PathVariable Long id
    ) {
        service.deleteTarget(id, userId);
    }

}
