package com.DeliveryBoy.repository;

import com.DeliveryBoy.entity.CustomerBrandDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerBrandDetailsRepository extends JpaRepository<CustomerBrandDetails, Long> {

}
