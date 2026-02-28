package com.gaadix.inventory.repository;

import com.gaadix.common.entity.Car;
import com.gaadix.common.enums.CarStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Car, Long> {
    
    Page<Car> findByStatus(CarStatus status, Pageable pageable);
    
    Page<Car> findBySellerId(Long sellerId, Pageable pageable);
    
    List<Car> findByStatusAndIsFeaturedTrue(CarStatus status);
    
    @Query("SELECT c FROM Car c WHERE c.status = :status AND c.basePrice BETWEEN :minPrice AND :maxPrice")
    Page<Car> findByStatusAndPriceRange(CarStatus status, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    @Query("SELECT c FROM Car c WHERE c.status = :status AND c.brand = :brand")
    Page<Car> findByStatusAndBrand(CarStatus status, String brand, Pageable pageable);
}
