package com.fitness.userservice.dto;

import com.fitness.userservice.model.ActivityType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AutoCompleteTargetRequest {
    private String userId;
    private ActivityType activityType;
    private LocalDate date;
}