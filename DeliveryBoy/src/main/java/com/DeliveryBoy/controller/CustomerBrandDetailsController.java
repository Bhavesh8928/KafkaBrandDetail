package com.DeliveryBoy.controller;

import com.DeliveryBoy.dto.CustomerBrandDetailsDTO;
import com.DeliveryBoy.service.CustomerBrandDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class CustomerBrandDetailsController {

    private final CustomerBrandDetailsService service;

    @PostMapping
    public ResponseEntity<CustomerBrandDetailsDTO> createCustomerBrandDetails(@RequestBody CustomerBrandDetailsDTO dto) {
        CustomerBrandDetailsDTO createdDto = service.createCustomerBrandDetails(dto);
        return ResponseEntity.ok(createdDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerBrandDetailsDTO> updateCustomerBrandDetails(
            @PathVariable Long id,
            @RequestBody CustomerBrandDetailsDTO dto) {
        CustomerBrandDetailsDTO updatedDto = service.updateCustomerBrandDetails(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerBrandDetails(@PathVariable Long id) {
        service.deleteCustomerBrandDetails(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerBrandDetailsDTO> getCustomerBrandDetailsById(@PathVariable Long id) {
        CustomerBrandDetailsDTO dto = service.getCustomerBrandDetailsById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CustomerBrandDetailsDTO>> getAllCustomerBrandDetails() {
        List<CustomerBrandDetailsDTO> dtoList = service.getAllCustomerBrandDetails();
        return ResponseEntity.ok(dtoList);
    }
}
