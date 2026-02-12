package com.fitness.userservice.repository;

import com.fitness.userservice.model.ActivityType;
import com.fitness.userservice.model.DailyTarget;
import com.fitness.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyTargetRepository extends JpaRepository<DailyTarget, Long> {

    List<DailyTarget> findByUserAndTargetDate(User user, LocalDate date);

    void deleteByUserAndTargetDate(User user, LocalDate date);

    void deleteByUserAndTargetDateLessThan(User user, LocalDate date);

    void deleteByIdAndUser(Long id, User user);

    Optional<DailyTarget> findByUserAndTargetDateAndActivityTypeAndCompletedFalse(
            User user,
            LocalDate targetDate,
            ActivityType activityType
    );

}
