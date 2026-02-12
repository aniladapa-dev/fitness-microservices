package com.fitness.userservice.dto;

import com.fitness.userservice.model.ActivityType;
import lombok.Data;

@Data
public class AddTargetRequest {
    private ActivityType activityType;
    private String label;
}
