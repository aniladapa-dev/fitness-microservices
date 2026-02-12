package com.fitness.aiservice.model;

import lombok.Data;


import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Activity {

    private String id;
    private String userId;
    private Integer duration;
    private Integer caloriesBurned;
    private String type;
    private LocalDateTime start;
    private Map<String, Object> additionalMetrics;
    private LocalDateTime createdAt;
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