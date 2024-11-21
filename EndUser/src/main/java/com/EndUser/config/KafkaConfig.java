package com.EndUser.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);
    private int receivedMessageCount = 0;

    @KafkaListener(topics = AppConstants.LOCATION__UPDATE_TOPIC, groupId = AppConstants.LOCATION__UPDATE_GROUP_ID)
    public void consumeBrandDesign(String message) {
        if (message.startsWith("Design published successfully for BrandID:")) {
            logger.info("Design sent successfully: {}", message);
        } else if (message.startsWith("TOTAL_MESSAGES_SENT: ")) {
            logger.info("Received summary message: {}", message);
        } else {
            logger.info("Received unrelated message: {}", message);
        }
    }

    @KafkaListener(topics = AppConstants.LOCATION__UPDATE_TOPIC, groupId = AppConstants.LOCATION__UPDATE_GROUP_ID)
    public void updatedLocation(String location) {

//        System.out.println(location);
        if (location.startsWith("TOTAL_MESSAGES_SENT: ")) {
            int sentCount = Integer.parseInt(location.split(": ")[1]);
            logger.info("Total messages sent by producer: " + sentCount);
            logger.info("Total messages received by consumer:  " + receivedMessageCount);

            if (sentCount == receivedMessageCount) {
                logger.info("All messages sent successfully");
            } else {
                logger.info("Messages lost detected! Sent: {} and Received: {}", sentCount, receivedMessageCount);
            }
            receivedMessageCount = 0;
        } else {
            receivedMessageCount++;
            logger.info("Received location update:{}", location);
        }
    }
}
