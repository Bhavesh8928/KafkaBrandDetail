package com.DeliveryBoy.service;

import com.DeliveryBoy.config.ModelMapperConfig;
import com.DeliveryBoy.dto.CustomerBrandDetailsDTO;
import com.DeliveryBoy.entity.CustomerBrandDetails;
import com.DeliveryBoy.repository.CustomerBrandDetailsRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
}
