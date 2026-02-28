package com.gaadix.search.repository;

import com.gaadix.common.entity.Car;
import com.gaadix.common.enums.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface VehicleSearchRepository extends JpaRepository<Car, Long> {
    
    @Query("SELECT c FROM Car c WHERE " +
           "(:keyword IS NULL OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.model) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:brand IS NULL OR c.brand = :brand) " +
           "AND (:model IS NULL OR c.model = :model) " +
           "AND (:minYear IS NULL OR c.year >= :minYear) " +
           "AND (:maxYear IS NULL OR c.year <= :maxYear) " +
           "AND (:fuelType IS NULL OR c.fuelType = :fuelType) " +
           "AND (:transmission IS NULL OR c.transmission = :transmission) " +
           "AND (:minPrice IS NULL OR c.basePrice >= :minPrice) " +
           "AND (:maxPrice IS NULL OR c.basePrice <= :maxPrice) " +
           "AND (:maxOdometer IS NULL OR c.odometer <= :maxOdometer) " +
           "AND (:city IS NULL OR c.city = :city) " +
           "AND (:state IS NULL OR c.state = :state) " +
           "AND (:status IS NULL OR c.status = :status)")
    Page<Car> searchVehicles(
            @Param("keyword") String keyword,
            @Param("brand") String brand,
            @Param("model") String model,
            @Param("minYear") Integer minYear,
            @Param("maxYear") Integer maxYear,
            @Param("fuelType") FuelType fuelType,
            @Param("transmission") Transmission transmission,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("maxOdometer") Integer maxOdometer,
            @Param("city") String city,
            @Param("state") String state,
            @Param("status") CarStatus status,
            Pageable pageable
    );
    
    @Query(value = "SELECT * FROM cars c WHERE " +
           "c.latitude IS NOT NULL AND c.longitude IS NOT NULL " +
           "AND (6371 * acos(cos(radians(:lat)) * cos(radians(c.latitude)) * " +
           "cos(radians(c.longitude) - radians(:lon)) + sin(radians(:lat)) * " +
           "sin(radians(c.latitude)))) <= :radiusKm " +
           "AND (:status IS NULL OR c.status = :status)", 
           nativeQuery = true)
    Page<Car> searchByLocation(
            @Param("lat") Double latitude,
            @Param("lon") Double longitude,
            @Param("radiusKm") Integer radiusKm,
            @Param("status") String status,
            Pageable pageable
    );
}
