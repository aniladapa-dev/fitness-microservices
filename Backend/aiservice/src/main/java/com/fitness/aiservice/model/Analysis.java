package com.fitness.aiservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Data model representing the core analysis component of a recommendation.
 * - Breakdowns insights across overall performance, pace, heart rate, and calories.
 * - Used as an embedded object within the Recommendations document in Mongo.
 */
public class Analysis {
    private String overall;
    private String pace;
    private String heartRate;
    private String caloriesBurned;
}

