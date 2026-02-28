package com.gaadix.inventory.repository;

import com.gaadix.inventory.entity.VehicleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleImageRepository extends JpaRepository<VehicleImage, Long> {
    
    List<VehicleImage> findByCarIdOrderByDisplayOrderAsc(Long carId);
    
    Optional<VehicleImage> findByCarIdAndIsPrimaryTrue(Long carId);
    
    void deleteByCarId(Long carId);
}
