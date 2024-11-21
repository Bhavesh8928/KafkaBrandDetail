package com.DeliveryBoy.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBrandDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_brand_details_seq")
    private Long id;

    private String name;

    private String description;

    private String emailId;

    private String mobileNo;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status;

    private Long createdBy;

    private LocalDateTime createdAt;

    private Long updatedBy;

    private LocalDateTime updatedAt;

}
