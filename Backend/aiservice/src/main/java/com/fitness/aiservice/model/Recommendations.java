package com.fitness.aiservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "recommendations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recommendations {

    @Id
    private String id;

    private String activityId;
    private String userId;

    // Core analysis
    private Analysis analysis;

    // Lists
    private List<String> improvements;
    private List<String> suggestions;
    private List<DietRecommendation> dietRecommendations;
    private List<String> safety;

    private String difficultyLevel;
    private Double confidenceScore;



    @CreatedDate
    private LocalDateTime createdAt;
}
