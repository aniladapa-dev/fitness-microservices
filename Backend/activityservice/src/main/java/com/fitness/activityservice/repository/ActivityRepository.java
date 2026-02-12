package com.fitness.activityservice.repository;

import com.fitness.activityservice.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
    List<Activity> findByUserId(String userId);

    @Query("""
    {
      "userId": ?0,
      "createdAt": {
        "$gte": ?1,
        "$lt": ?2
      }
    }
    """)
    List<Activity> findUserActivitiesForToday(
            String userId,
            LocalDateTime startOfDay,
            LocalDateTime startOfNextDay
    );
}
