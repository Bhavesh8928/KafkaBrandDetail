package com.DeliveryBoy.controller;

import com.DeliveryBoy.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public class LocationController {
    @RestController
    @RequestMapping("/delivery")
    public static class KafkaController {

        @Autowired
        private KafkaService kafkaService;

        private Count countTracker = new Count();

        @PostMapping("/update")
        public ResponseEntity<?> updateLocation() {
            countTracker.resetCount();
            for (int i = 0; i < 100; i++) {
                String location = "[" + Math.round(Math.random() * 100) + "," + Math.round(Math.random() * 100) + "]";
                kafkaService.updateLocation(location);
                countTracker.incrementCount();
            }
            kafkaService.updateLocation("TOTAL_MESSAGES_SENT: " + countTracker.getFinalCount());
            return new ResponseEntity<>(Map.of("Message", "Updated Location"), HttpStatus.OK);
        }

        @PostMapping("/publish/{brandId}")
        public ResponseEntity<?> publishBrandDesign(@PathVariable Long brandId) {
            boolean isPublished = kafkaService.publishBrandDesign(brandId);
            return new ResponseEntity<>(Map.of("status", isPublished ? "success" : "failure"), HttpStatus.OK);
        }
    }
}
