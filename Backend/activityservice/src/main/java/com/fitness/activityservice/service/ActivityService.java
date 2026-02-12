package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.dto.AutoCompleteTargetRequest;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;
    private final DailyTargetClient dailyTargetClient;

    //@value is used to access values from application.yml - rabbitmq values
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public ActivityResponse trackActivity(String keycloakId,
                                          ActivityRequest request) {

        boolean isValidUser = userValidationService.validateUser(keycloakId);
        if (!isValidUser) {
            throw new RuntimeException("Invalid User : " + keycloakId);
        }

        Activity activity = Activity.builder()
                .userId(keycloakId)
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .start(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);

        AutoCompleteTargetRequest autoCompleteRequest = new AutoCompleteTargetRequest();
        autoCompleteRequest.setUserId(savedActivity.getUserId());
        autoCompleteRequest.setActivityType(savedActivity.getType());

        if (savedActivity.getCreatedAt() != null) {
            autoCompleteRequest.setDate(savedActivity.getCreatedAt().toLocalDate());
        }


        try {
            dailyTargetClient.autoCompleteTarget(autoCompleteRequest);
        } catch (Exception e) {
            log.error("Failed to auto-complete target for user {}", autoCompleteRequest.getUserId(), e);
        }


        return mapToActivityResponse(savedActivity);
    }




    private ActivityResponse mapToActivityResponse(Activity activity) {
        ActivityResponse activityResponse = new ActivityResponse();

        activityResponse.setId(activity.getId());
        activityResponse.setUserId(activity.getUserId());
        activityResponse.setType(activity.getType());
        activityResponse.setDuration(activity.getDuration());
        activityResponse.setCaloriesBurned(activity.getCaloriesBurned());
        activityResponse.setStart(activity.getStart());
        activityResponse.setAdditionalMetrics(activity.getAdditionalMetrics());
        activityResponse.setCreatedAt(activity.getCreatedAt());
        activityResponse.setUpdatedAt(activity.getUpdatedAt());

        return activityResponse;
    }


    public List<ActivityResponse> getUserActivity(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);

        return activities.stream()
                .map(this::mapToActivityResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found with Id :" + activityId));

        return mapToActivityResponse(activity);
    }

    public List<ActivityResponse> getUserTodayActivity(String userId, LocalDate today) {

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime startOfNextDay = today.plusDays(1).atStartOfDay();

        return activityRepository
                .findUserActivitiesForToday(userId, startOfDay, startOfNextDay)
                .stream()
                .map(this::mapToActivityResponse)
                .toList();
    }

    public void deleteActivity(String activityId) {

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() ->
                        new RuntimeException("Activity not found"));

        activityRepository.delete(activity);
    }


}
