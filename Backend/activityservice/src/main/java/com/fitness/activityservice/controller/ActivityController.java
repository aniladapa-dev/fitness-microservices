package com.fitness.activityservice.controller;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(
            @RequestHeader("X-User-ID") String keycloakId,
            @RequestBody ActivityRequest request) {

        return ResponseEntity.ok(
                activityService.trackActivity(keycloakId, request)
        );
    }


    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivity(@RequestHeader("X-User-ID") String userId){
        return ResponseEntity.ok(activityService.getUserActivity(userId));
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable String activityId){
        return ResponseEntity.ok(activityService.getActivityById(activityId));
    }

    @GetMapping("/today")
    public ResponseEntity<List<ActivityResponse>> getUserTodayActivity(@RequestHeader("X-User-ID") String userId){
        LocalDate today = LocalDate.now();
        return ResponseEntity.ok(activityService.getUserTodayActivity(userId, today));
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<Void> deleteActivity(
            @PathVariable String activityId) {

        activityService.deleteActivity(activityId);
        return ResponseEntity.noContent().build();
    }



}
