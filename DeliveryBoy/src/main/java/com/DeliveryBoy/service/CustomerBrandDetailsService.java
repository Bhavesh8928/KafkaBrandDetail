package com.DeliveryBoy.service;

import com.DeliveryBoy.dto.CustomerBrandDetailsDTO;
import com.DeliveryBoy.entity.CustomerBrandDetails;
import com.DeliveryBoy.repository.CustomerBrandDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerBrandDetailsService {

    private final CustomerBrandDetailsRepository repository;
//    private final ModelMapper modelMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @Lazy //This tells Spring to initialize the bean only when it is first used, rather than at application startup. It helps in breaking circular dependencies between customerBrandService and kafkaService
    private KafkaService kafkaService;

    @Async
    public void insertDummyData() {
        List<CustomerBrandDetails> batch = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            CustomerBrandDetails entity = new CustomerBrandDetails();
            entity.setName("Brand " + i);
            entity.setDescription("Description for Brand " + i);
            entity.setEmailId("brand" + i + "@example.com");
            entity.setMobileNo("123456" + String.format("%04d", i));
            entity.setStatus(true);
            entity.setCreatedBy(1L);
            entity.setCreatedAt(LocalDateTime.now());
            batch.add(entity);
            // Insert data in batches of 1000 records
            if (i % 1000 == 0 || i == 99999) {
                repository.saveAll(batch);
                batch.clear();  // Clear the batch after saving
                System.out.println("Dummy Data Inserted " + (i + 1) + " records");
            }
        }
    }

    public CustomerBrandDetailsDTO createCustomerBrandDetails(CustomerBrandDetailsDTO dto) {
        CustomerBrandDetails entity = modelMapper.map(dto, CustomerBrandDetails.class);
        entity.setCreatedAt(LocalDateTime.now());
        CustomerBrandDetails savedEntity = repository.save(entity);
        return modelMapper.map(savedEntity, CustomerBrandDetailsDTO.class);
    }

    public CustomerBrandDetailsDTO updateCustomerBrandDetails(Long id, CustomerBrandDetailsDTO dto) {
        Optional<CustomerBrandDetails> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            CustomerBrandDetails entity = optionalEntity.get();
            modelMapper.map(dto, entity);
            entity.setUpdatedAt(LocalDateTime.now());
            CustomerBrandDetails updatedEntity = repository.save(entity);
            return modelMapper.map(updatedEntity, CustomerBrandDetailsDTO.class);
        } else {
            throw new RuntimeException("CustomerBrandDetails not found with id: " + id);
        }
    }

    public void deleteCustomerBrandDetails(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("CustomerBrandDetails not found with id: " + id);
        }
    }

    public CustomerBrandDetailsDTO getCustomerBrandDetailsById(Long id) {
        CustomerBrandDetails entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CustomerBrandDetails not found with id: " + id));
        return modelMapper.map(entity, CustomerBrandDetailsDTO.class);
    }

    public List<CustomerBrandDetailsDTO> getAllCustomerBrandDetails() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, CustomerBrandDetailsDTO.class))
                .collect(Collectors.toList());
    }

    @Async
    public void generateCustomDesignAsync(Long brandId) {
        // Fetch brand details from the database
        CustomerBrandDetailsDTO brandDetails = getCustomerBrandDetailsById(brandId);
        if (brandDetails != null) {
            // Publish a message to Kafka
            String message = "Design generated for BrandID: " + brandId + " - " + brandDetails.getName();
            kafkaService.publishMessage(message);
        } else {
            throw new RuntimeException("Brand not found with ID: " + brandId);
        }
    }
}
