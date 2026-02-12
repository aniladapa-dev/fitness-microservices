package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendations;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAiService activityAiService;

    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity) {

        log.info("Received activity for processing: {}", activity.getId());

        try {
            activityAiService.generateRecommendation(activity);
            log.info("Recommendation processing completed for activity {}", activity.getId());

        } catch (Exception e) {

            log.error("AI processing failed for activity {}. Saving fallback.",
                    activity.getId(), e);

            activityAiService.saveFallbackRecommendation(activity);
        }
    }
}



    /*
    @RabbitListener(
            queues = "activity.queue",
            ackMode = "MANUAL"
    )
    public void processActivity(Activity activity, Channel channel, Message message) throws IOException {
        try {
            log.info("Received activity for processing: {}", activity.getId());

            Recommendations recommendation =
                    activityAiService.generateRecommendation(activity);

            if (recommendation == null) {
                throw new RuntimeException("Recommendation generation failed");
            }


            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            log.info("ACK sent for activity {}", activity.getId());

        } catch (Exception e) {

            log.error("Processing failed for activity {}", activity.getId(), e);

            channel.basicNack(
                    message.getMessageProperties().getDeliveryTag(),
                    false,
                    true // requeue
            );
        }
    }

     */

