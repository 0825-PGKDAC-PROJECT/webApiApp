package com.gaadix.inventory.service;

import com.gaadix.common.entity.Car;
import com.gaadix.common.enums.CarStatus;
import com.gaadix.common.exception.ResourceNotFoundException;
import com.gaadix.inventory.dto.VehicleRequest;
import com.gaadix.inventory.dto.VehicleResponse;
import com.gaadix.inventory.entity.VehicleImage;
import com.gaadix.inventory.entity.VehicleInspection;
import com.gaadix.inventory.repository.VehicleImageRepository;
import com.gaadix.inventory.repository.VehicleInspectionRepository;
import com.gaadix.inventory.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {
    
    private final VehicleRepository vehicleRepository;
    private final VehicleImageRepository imageRepository;
    private final VehicleInspectionRepository inspectionRepository;
    
    @Transactional
    public VehicleResponse createVehicle(VehicleRequest request, Long sellerId) {
        Car car = Car.builder()
                .brand(request.getBrand())
                .model(request.getModel())
                .year(request.getYear())
                .fuelType(request.getFuelType())
                .batteryCapacity(request.getBatteryCapacity())
                .electricRange(request.getElectricRange())
                .basePrice(request.getBasePrice())
                .odometer(request.getOdometer())
                .color(request.getColor())
                .registrationNumber(request.getRegistrationNumber())
                .registrationState(request.getRegistrationState())
                .rtoCode(request.getRtoCode())
                .ownershipType(request.getOwnershipType())
                .transmission(request.getTransmission())
                .description(request.getDescription())
                .features(request.getFeatures())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .sellerId(sellerId)
                .insuranceExpiryDate(request.getInsuranceExpiryDate())
                .pucExpiryDate(request.getPucExpiryDate())
                .status(CarStatus.DRAFT)
                .build();
        
        Car saved = vehicleRepository.save(car);
        return mapToResponse(saved);
    }
    
    @Transactional(readOnly = true)
    public VehicleResponse getVehicleById(Long id) {
        Car car = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        return mapToResponse(car);
    }
    
    @Transactional(readOnly = true)
    public Page<VehicleResponse> getAllVehicles(CarStatus status, Pageable pageable) {
        Page<Car> cars = status != null 
                ? vehicleRepository.findByStatus(status, pageable)
                : vehicleRepository.findAll(pageable);
        return cars.map(this::mapToResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<VehicleResponse> getVehiclesBySeller(Long sellerId, Pageable pageable) {
        return vehicleRepository.findBySellerId(sellerId, pageable)
                .map(this::mapToResponse);
    }
    
    @Transactional
    public VehicleResponse updateVehicle(Long id, VehicleRequest request) {
        Car car = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        
        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setFuelType(request.getFuelType());
        car.setBasePrice(request.getBasePrice());
        car.setOdometer(request.getOdometer());
        car.setColor(request.getColor());
        car.setTransmission(request.getTransmission());
        car.setDescription(request.getDescription());
        car.setFeatures(request.getFeatures());
        car.setCity(request.getCity());
        car.setState(request.getState());
        
        Car updated = vehicleRepository.save(car);
        return mapToResponse(updated);
    }
    
    @Transactional
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);
        }
        vehicleRepository.deleteById(id);
    }
    
    @Transactional
    public VehicleResponse publishVehicle(Long id) {
        Car car = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        car.setStatus(CarStatus.AVAILABLE);
        Car updated = vehicleRepository.save(car);
        return mapToResponse(updated);
    }
    
    private VehicleResponse mapToResponse(Car car) {
        List<VehicleImage> images = imageRepository.findByCarIdOrderByDisplayOrderAsc(car.getId());
        VehicleInspection latestInspection = inspectionRepository
                .findFirstByCarIdOrderByInspectionDateDesc(car.getId())
                .orElse(null);
        
        return VehicleResponse.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .fuelType(car.getFuelType())
                .basePrice(car.getBasePrice())
                .odometer(car.getOdometer())
                .color(car.getColor())
                .registrationNumber(car.getRegistrationNumber())
                .ownershipType(car.getOwnershipType())
                .transmission(car.getTransmission())
                .status(car.getStatus())
                .description(car.getDescription())
                .features(car.getFeatures())
                .city(car.getCity())
                .state(car.getState())
                .sellerId(car.getSellerId())
                .isFeatured(car.isFeatured())
                .viewCount(car.getViewCount())
                .insuranceExpiryDate(car.getInsuranceExpiryDate())
                .imageUrls(images.stream().map(VehicleImage::getImageUrl).collect(Collectors.toList()))
                .primaryImageUrl(images.stream().filter(VehicleImage::isPrimary)
                        .findFirst().map(VehicleImage::getImageUrl).orElse(null))
                .inspectionScore(latestInspection != null ? latestInspection.getOverallScore() : null)
                .build();
    }
}
