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
/**
 * REST Controller for managing daily targets for a user.
 * - Allows fetching a user's targets for the current day.
 * - Provides functionality to add new daily targets.
 * - Enables toggling the completion status of a specific target.
 * - Supports resetting/deleting all targets for today.
 * - Allows deleting a specific target by its ID.
 */
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
