package com.fitness.userservice.dto;

import com.fitness.userservice.model.ActivityType;
import lombok.Data;

@Data
public class DailyTargetResponse {
    private Long id;
    private ActivityType activityType;
    private String label;
    private boolean completed;
}
