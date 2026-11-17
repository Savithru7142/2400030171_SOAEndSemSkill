package com.example.hoteltourist.repository;

import com.example.hoteltourist.entity.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouristRepository extends JpaRepository<Tourist, Long> {
}
