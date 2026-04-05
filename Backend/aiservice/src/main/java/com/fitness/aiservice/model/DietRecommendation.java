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
 * Data model for specialized nutrition advice tied to a workout.
 * - Used as an embedded list item in the Recommendations document.
 * - Specifies dietary actions and their physiological purpose.
 */
public class DietRecommendation {
    private String basedOnActivity;
    private String purpose;
}

