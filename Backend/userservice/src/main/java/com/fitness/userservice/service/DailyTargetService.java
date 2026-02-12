package com.fitness.userservice.service;

import com.fitness.userservice.dto.DailyTargetResponse;
import com.fitness.userservice.model.ActivityType;
import com.fitness.userservice.model.DailyTarget;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.DailyTargetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyTargetService {

    private final DailyTargetRepository repository;
    private final UserService userService;

    /* ---------- GET TODAY TARGETS ---------- */
    @Transactional
    public List<DailyTargetResponse> getTodayTargets(String keycloakId) {
        User user = userService.getByKeycloakId(keycloakId);

        LocalDate today = LocalDate.now();
        repository.deleteByUserAndTargetDateLessThan(user, today);

        return repository.findByUserAndTargetDate(user, today)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /* ---------- ADD TARGET ---------- */
    @Transactional
    public DailyTargetResponse addTarget(
            String keycloakId,
            ActivityType activityType,
            String label
    ) {
        User user = userService.getByKeycloakId(keycloakId);

        DailyTarget target = new DailyTarget();
        target.setUser(user);
        target.setTargetDate(LocalDate.now());
        target.setActivityType(activityType);
        target.setLabel(label);
        target.setCompleted(false);

        repository.save(target);
        return toResponse(target);
    }

    /* ---------- TOGGLE TARGET ---------- */
    @Transactional
    public DailyTargetResponse toggleTarget(Long id, String keycloakId) {
        DailyTarget target = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Target not found"));

        if (!target.getUser().getKeycloakId().equals(keycloakId)) {
            throw new RuntimeException("Not your target");
        }

        target.setCompleted(!target.isCompleted());
        repository.save(target);

        return toResponse(target);
    }

    /* ---------- RESET TODAY ---------- */
    @Transactional
    public void resetToday(String keycloakId) {
        User user = userService.getByKeycloakId(keycloakId);
        repository.deleteByUserAndTargetDate(user, LocalDate.now());
    }

    /* ---------- ENTITY → DTO ---------- */
    private DailyTargetResponse toResponse(DailyTarget target) {
        DailyTargetResponse dto = new DailyTargetResponse();
        dto.setId(target.getId());
        dto.setActivityType(target.getActivityType());
        dto.setLabel(target.getLabel());
        dto.setCompleted(target.isCompleted());
        return dto;
    }

    /* delete a target by it is id*/
    @Transactional
    public void deleteTarget(Long id, String keycloakId) {
        User user = userService.getByKeycloakId(keycloakId);

        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new RuntimeException("Target not found");
        }

        repository.deleteByIdAndUser(id, user);
    }

    @Transactional
    public void autoCompleteTarget(
            String userId,
            ActivityType activityType,
            LocalDate date
    ) {
        User user = userService.getByKeycloakId(userId);

        repository.findByUserAndTargetDateAndActivityTypeAndCompletedFalse(
                user,
                date,
                activityType
        ).ifPresent(target -> {
            target.setCompleted(true);
            repository.save(target);
        });
    }


}
