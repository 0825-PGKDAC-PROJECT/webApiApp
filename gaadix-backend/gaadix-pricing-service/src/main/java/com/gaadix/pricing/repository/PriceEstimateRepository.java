package com.gaadix.pricing.repository;

import com.gaadix.pricing.entity.PriceEstimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceEstimateRepository extends JpaRepository<PriceEstimate, Long> {
    
    Optional<PriceEstimate> findByCarId(Long carId);
    
    List<PriceEstimate> findByBrandAndModel(String brand, String model);
    
    List<PriceEstimate> findByYear(Integer year);
}
