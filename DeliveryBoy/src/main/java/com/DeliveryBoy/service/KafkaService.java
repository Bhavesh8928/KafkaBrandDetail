package com.DeliveryBoy.service;

import com.DeliveryBoy.config.AppConstants;
import com.DeliveryBoy.dto.CustomerBrandDetailsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    @Lazy
    private CustomerBrandDetailsService brandDetailsService;

    public boolean updateLocation(String location) {
        this.kafkaTemplate.send(AppConstants.LOCATION_TOPIC_NAME, location);
//        this.logger.info("Location updated and message produced: " + location);
        return true;
    }

    public boolean publishMessage(String message) {
        kafkaTemplate.send(AppConstants.LOCATION_TOPIC_NAME, message);
        logger.info("Published message to Kafka: {}", message);  // Design generated for BrandID: 12 - Brand Name here
        return true;
    }

    public boolean publishBrandDesign(Long brandId) {
        try {
            CustomerBrandDetailsDTO customerBrandDetails = brandDetailsService.getCustomerBrandDetailsById(brandId);
            if (customerBrandDetails == null) {
                logger.error("Brand with ID {} not found", brandId);
                throw new RuntimeException("Brand not found with ID: " + brandId); // or return false if preferred
//                return false;
            }

            Thread.sleep(5000); // Simulate processing delay

            String message = "Design published successfully for BrandID: " + brandId;
            kafkaTemplate.send(AppConstants.LOCATION_TOPIC_NAME, message);
            logger.info(message);

            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted", e);
            return false;
        } catch (RuntimeException e) {
            logger.error("Error publishing design: {}", e.getMessage());
            return false;
        }
    }
}
