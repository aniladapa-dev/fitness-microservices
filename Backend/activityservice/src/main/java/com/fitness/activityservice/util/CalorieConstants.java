package com.fitness.activityservice.util;

import com.fitness.activityservice.model.ActivityType;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Utility class containing MET (Metabolic Equivalent of Task) values for various activities.
 * Used for automatic calorie calculation when user input is missing.
 */
public class CalorieConstants {

    public static final Map<ActivityType, Double> MET_VALUES;

    static {
        Map<ActivityType, Double> map = new EnumMap<>(ActivityType.class);
        map.put(ActivityType.RUNNING, 8.0);
        map.put(ActivityType.WALKING, 3.5);
        map.put(ActivityType.CYCLING, 7.5);
        map.put(ActivityType.SWIMMING, 8.0);
        map.put(ActivityType.WEIGHT_TRAINING, 5.0);
        map.put(ActivityType.YOGA, 2.5);
        map.put(ActivityType.CARDIO, 7.0);
        map.put(ActivityType.STRETCHING, 2.3);
        map.put(ActivityType.MEDITATION, 1.0);
        map.put(ActivityType.OTHER, 4.0);
        MET_VALUES = Collections.unmodifiableMap(map);
    }

    /**
     * Calculates estimated calories burned.
     * Formula: Calories = MET * weight (kg) * (duration (mins) / 60)
     */
    public static int calculateCalories(ActivityType type, int durationMinutes, Double weight) {
        if (weight == null || weight <= 0) {
            weight = 70.0; // Default weight if not provided
        }
        Double met = MET_VALUES.getOrDefault(type, 4.0);
        double durationHours = durationMinutes / 60.0;
        return (int) Math.round(met * weight * durationHours);
    }
}
