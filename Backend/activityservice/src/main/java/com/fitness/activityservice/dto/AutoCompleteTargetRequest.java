package com.fitness.activityservice.dto;

import com.fitness.activityservice.model.ActivityType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AutoCompleteTargetRequest {

    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activityType;
    private LocalDate date;
}
