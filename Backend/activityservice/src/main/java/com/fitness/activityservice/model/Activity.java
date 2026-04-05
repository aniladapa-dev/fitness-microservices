package com.fitness.activityservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "activities")
@Data
@Builder
@AllArgsConstructor
/**
 * MongoDB Document representing a physical fitness activity.
 * - Stored in the "activities" collection.
 * - Captures metadata: user ID, activity type, duration, and calories.
 * - Supports dynamic/unstructured "additional metrics" via a Map.
 * - Utilizes Spring Data audit fields for created and updated timestamps.
 */
public class Activity {

    @Id
    private String id;
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    
    // New fields for Hybrid Calorie Calculation
    private Double weight;
    private Integer userProvidedCalories;
    private Integer calculatedCalories;

    private LocalDateTime start;

    @Field("metrics")
    private Map<String, Object> additionalMetrics;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}

/*
    {
    "userId" : 1,
    "type" : "RUNNING",
    "duration" : 30,
    "caloriesBurned" : 300,
    "startTime" : "2026-01-25T11:11:11",
    "additionalMetrics" : {
        "distance" : 4.4,
        "averageSpeed" : 2.2,
        "maxHeartRate" : 100
    }
}
 */