package com.gaadix.inventory.repository;

import com.gaadix.inventory.entity.VehicleInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleInspectionRepository extends JpaRepository<VehicleInspection, Long> {
    
    List<VehicleInspection> findByCarIdOrderByInspectionDateDesc(Long carId);
    
    Optional<VehicleInspection> findFirstByCarIdOrderByInspectionDateDesc(Long carId);
}
