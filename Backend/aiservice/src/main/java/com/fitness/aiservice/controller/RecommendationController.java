package com.fitness.aiservice.controller;

import com.fitness.aiservice.model.Recommendations;
import com.fitness.aiservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
/**
 * REST Controller for serving AI-generated recommendations.
 * - Retrieves all fitness recommendations for a given user.
 * - Retrieves a specific recommendation by the activity ID.
 * - Deletes a recommendation internally when the related activity is deleted.
 */
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("user/{userId}")
    public ResponseEntity<List<Recommendations>> getUserRecommendation(@PathVariable String userId){
        return ResponseEntity.ok(recommendationService.getUserRecommendation(userId));
    }

    @GetMapping("activity/{activityId}")
    public ResponseEntity<Recommendations> getActivityRecommendation(@PathVariable String activityId){
        return ResponseEntity.ok(recommendationService.getActivityRecommendation(activityId));
    }

    @DeleteMapping("/activity/{activityId}")
    public ResponseEntity<Void> deleteByActivityId(
            @PathVariable String activityId) {

        recommendationService.deleteByActivityId(activityId);
        return ResponseEntity.noContent().build();
    }

}
