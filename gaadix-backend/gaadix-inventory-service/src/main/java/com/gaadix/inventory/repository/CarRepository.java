package com.gaadix.inventory.repository;

import com.gaadix.common.entity.Car;
import com.gaadix.common.enums.CarStatus;
import com.gaadix.common.enums.FuelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
    Page<Car> findByStatus(CarStatus status, Pageable pageable);
    Page<Car> findBySellerId(Long sellerId, Pageable pageable);
    Page<Car> findByFuelType(FuelType fuelType, Pageable pageable);
    List<Car> findByIsFeaturedTrue();
    
    @Query(value = 
        "SELECT * FROM cars c WHERE c.status = 'AVAILABLE' " +
        "AND (6371 * acos(cos(radians(:lat)) * cos(radians(c.latitude)) * " +
        "cos(radians(c.longitude) - radians(:lon)) + sin(radians(:lat)) * " +
        "sin(radians(c.latitude)))) <= :radius",
        nativeQuery = true)
    List<Car> findCarsWithinRadius(@Param("lat") Double latitude,
                                   @Param("lon") Double longitude,
                                   @Param("radius") Double radiusKm);
}
