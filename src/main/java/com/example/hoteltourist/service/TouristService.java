package com.example.hoteltourist.service;

import com.example.hoteltourist.dto.TouristUpdateRequest;
import com.example.hoteltourist.entity.Tourist;
import com.example.hoteltourist.exception.TouristNotFoundException;
import com.example.hoteltourist.repository.TouristRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TouristService {

    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    @Transactional
    public void updateTourist(Long id, TouristUpdateRequest request) {
        Tourist tourist = touristRepository.findById(id)
                .orElseThrow(() -> new TouristNotFoundException(id));

        tourist.setName(request.name());
        tourist.setDestination(request.destination());
        tourist.setContactNumber(request.contactNumber());
        tourist.setEmail(request.email());
        touristRepository.save(tourist);
    }
}
