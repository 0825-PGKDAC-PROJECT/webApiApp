package com.gaadix.inventory.service;

import com.gaadix.common.entity.Car;
import com.gaadix.common.enums.CarStatus;
import com.gaadix.common.enums.FuelType;
import com.gaadix.common.enums.Transmission;
import com.gaadix.inventory.dto.VehicleRequest;
import com.gaadix.inventory.dto.VehicleResponse;
import com.gaadix.inventory.entity.VehicleImage;
import com.gaadix.inventory.entity.VehicleInspection;
import com.gaadix.inventory.repository.VehicleImageRepository;
import com.gaadix.inventory.repository.VehicleInspectionRepository;
import com.gaadix.inventory.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleImageRepository imageRepository;

    @Mock
    private VehicleInspectionRepository inspectionRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private Car car;
    private VehicleRequest vehicleRequest;
    private VehicleImage vehicleImage;
    private VehicleInspection vehicleInspection;

    @BeforeEach
    void setUp() {
        car = Car.builder()
                .brand("Maruti Suzuki")
                .model("Swift VXI")
                .year(2020)
                .basePrice(BigDecimal.valueOf(550000.0))
                .fuelType(FuelType.PETROL)
                .transmission(Transmission.MANUAL)
                .odometer(25000)
                .color("Red")
                .registrationNumber("MH12AB1234")
                .status(CarStatus.AVAILABLE)
                .sellerId(1L)
                .build();
        car.setId(1L);

        vehicleRequest = new VehicleRequest();
        vehicleRequest.setBrand("Maruti Suzuki");
        vehicleRequest.setModel("Swift VXI");
        vehicleRequest.setYear(2020);
        vehicleRequest.setBasePrice(BigDecimal.valueOf(550000.0));
        vehicleRequest.setFuelType(FuelType.PETROL);
        vehicleRequest.setTransmission(Transmission.MANUAL);

        vehicleImage = new VehicleImage();
        vehicleImage.setId(1L);
        vehicleImage.setCarId(1L);
        vehicleImage.setImageUrl("https://example.com/image.jpg");
        vehicleImage.setPrimary(true);

        vehicleInspection = new VehicleInspection();
        vehicleInspection.setId(1L);
        vehicleInspection.setCarId(1L);
        vehicleInspection.setOverallScore(85);
    }

    @Test
    void createVehicle_Success() {
        when(vehicleRepository.save(any(Car.class))).thenReturn(car);
        when(imageRepository.findByCarIdOrderByDisplayOrderAsc(anyLong())).thenReturn(new ArrayList<>());
        when(inspectionRepository.findFirstByCarIdOrderByInspectionDateDesc(anyLong())).thenReturn(Optional.empty());

        VehicleResponse result = vehicleService.createVehicle(vehicleRequest, 1L);

        assertNotNull(result);
        assertEquals("Maruti Suzuki", result.getBrand());
        assertEquals("Swift VXI", result.getModel());
        assertEquals(CarStatus.AVAILABLE, result.getStatus());
        verify(vehicleRepository).save(any(Car.class));
    }

    @Test
    void createVehicle_WithAllFields() {
        vehicleRequest.setDescription("Excellent condition");
        vehicleRequest.setFeatures("ABS, Airbags");
        vehicleRequest.setCity("Mumbai");
        vehicleRequest.setState("Maharashtra");

        when(vehicleRepository.save(any(Car.class))).thenReturn(car);
        when(imageRepository.findByCarIdOrderByDisplayOrderAsc(anyLong())).thenReturn(new ArrayList<>());
        when(inspectionRepository.findFirstByCarIdOrderByInspectionDateDesc(anyLong())).thenReturn(Optional.empty());

        VehicleResponse result = vehicleService.createVehicle(vehicleRequest, 1L);

        assertNotNull(result);
        verify(vehicleRepository).save(argThat(c -> 
            c.getSellerId().equals(1L) && c.getStatus() == CarStatus.DRAFT
        ));
    }

    @Test
    void getVehicleById_Success() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(imageRepository.findByCarIdOrderByDisplayOrderAsc(anyLong())).thenReturn(Arrays.asList(vehicleImage));
        when(inspectionRepository.findFirstByCarIdOrderByInspectionDateDesc(anyLong())).thenReturn(Optional.of(vehicleInspection));

        VehicleResponse result = vehicleService.getVehicleById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Maruti Suzuki", result.getBrand());
        assertEquals(85, result.getInspectionScore());
        assertNotNull(result.getPrimaryImageUrl());
        verify(vehicleRepository).findById(1L);
    }

    @Test
    void getVehicleById_NotFound() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> vehicleService.getVehicleById(999L));
        verify(vehicleRepository).findById(999L);
    }

    @Test
    void getVehicleById_WithoutImagesAndInspection() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(imageRepository.findByCarIdOrderByDisplayOrderAsc(anyLong())).thenReturn(new ArrayList<>());
        when(inspectionRepository.findFirstByCarIdOrderByInspectionDateDesc(anyLong())).thenReturn(Optional.empty());

        VehicleResponse result = vehicleService.getVehicleById(1L);

        assertNotNull(result);
        assertNull(result.getInspectionScore());
        assertNull(result.getPrimaryImageUrl());
        assertTrue(result.getImageUrls().isEmpty());
    }

    @Test
    void getAllVehicles_WithoutStatusFilter() {
        Page<Car> carPage = new PageImpl<>(Arrays.asList(car));
        when(vehicleRepository.findAll(any(Pageable.class))).thenReturn(carPage);
        when(imageRepository.findByCarIdOrderByDisplayOrderAsc(anyLong())).thenReturn(new ArrayList<>());
        when(inspectionRepository.findFirstByCarIdOrderByInspectionDateDesc(anyLong())).thenReturn(Optional.empty());

        Page<VehicleResponse> result = vehicleService.getAllVehicles(null, PageRequest.of(0, 20));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(vehicleRepository).findAll(any(Pageable.class));
    }

    @Test
    void getAllVehicles_WithStatusFilter() {
        Page<Car> carPage = new PageImpl<>(Arrays.asList(car));
        when(vehicleRepository.findByStatus(any(CarStatus.class), any(Pageable.class))).thenReturn(carPage);
        when(imageRepository.findByCarIdOrderByDisplayOrderAsc(anyLong())).thenReturn(new ArrayList<>());
        when(inspectionRepository.findFirstByCarIdOrderByInspectionDateDesc(anyLong())).thenReturn(Optional.empty());

        Page<VehicleResponse> result = vehicleService.getAllVehicles(CarStatus.AVAILABLE, PageRequest.of(0, 20));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(vehicleRepository).findByStatus(CarStatus.AVAILABLE, PageRequest.of(0, 20));
    }

    @Test
    void getAllVehicles_EmptyResult() {
        Page<Car> emptyPage = new PageImpl<>(new ArrayList<>());
        when(vehicleRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        Page<VehicleResponse> result = vehicleService.getAllVehicles(null, PageRequest.of(0, 20));

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void getVehiclesBySeller_Success() {
        Page<Car> carPage = new PageImpl<>(Arrays.asList(car));
        when(vehicleRepository.findBySellerId(anyLong(), any(Pageable.class))).thenReturn(carPage);
        when(imageRepository.findByCarIdOrderByDisplayOrderAsc(anyLong())).thenReturn(new ArrayList<>());
        when(inspectionRepository.findFirstByCarIdOrderByInspectionDateDesc(anyLong())).thenReturn(Optional.empty());

        Page<VehicleResponse> result = vehicleService.getVehiclesBySeller(1L, PageRequest.of(0, 20));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(vehicleRepository).findBySellerId(1L, PageRequest.of(0, 20));
    }

    @Test
    void updateVehicle_Success() {
        vehicleRequest.setBrand("Honda");
        vehicleRequest.setModel("City");
        vehicleRequest.setBasePrice(BigDecimal.valueOf(850000.0));

        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(vehicleRepository.save(any(Car.class))).thenReturn(car);
        when(imageRepository.findByCarIdOrderByDisplayOrderAsc(anyLong())).thenReturn(new ArrayList<>());
        when(inspectionRepository.findFirstByCarIdOrderByInspectionDateDesc(anyLong())).thenReturn(Optional.empty());

        VehicleResponse result = vehicleService.updateVehicle(1L, vehicleRequest);

        assertNotNull(result);
        verify(vehicleRepository).findById(1L);
        verify(vehicleRepository).save(any(Car.class));
    }

    @Test
    void updateVehicle_NotFound() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> vehicleService.updateVehicle(999L, vehicleRequest));
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void deleteVehicle_Success() {
        when(vehicleRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(vehicleRepository).deleteById(anyLong());

        vehicleService.deleteVehicle(1L);

        verify(vehicleRepository).existsById(1L);
        verify(vehicleRepository).deleteById(1L);
    }

    @Test
    void deleteVehicle_NotFound() {
        when(vehicleRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> vehicleService.deleteVehicle(999L));
        verify(vehicleRepository, never()).deleteById(anyLong());
    }

    @Test
    void publishVehicle_Success() {
        car.setStatus(CarStatus.DRAFT);
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(vehicleRepository.save(any(Car.class))).thenReturn(car);
        when(imageRepository.findByCarIdOrderByDisplayOrderAsc(anyLong())).thenReturn(new ArrayList<>());
        when(inspectionRepository.findFirstByCarIdOrderByInspectionDateDesc(anyLong())).thenReturn(Optional.empty());

        VehicleResponse result = vehicleService.publishVehicle(1L);

        assertNotNull(result);
        verify(vehicleRepository).save(argThat(c -> c.getStatus() == CarStatus.AVAILABLE));
    }

    @Test
    void publishVehicle_NotFound() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> vehicleService.publishVehicle(999L));
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void publishVehicle_AlreadyPublished() {
        car.setStatus(CarStatus.AVAILABLE);
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(vehicleRepository.save(any(Car.class))).thenReturn(car);
        when(imageRepository.findByCarIdOrderByDisplayOrderAsc(anyLong())).thenReturn(new ArrayList<>());
        when(inspectionRepository.findFirstByCarIdOrderByInspectionDateDesc(anyLong())).thenReturn(Optional.empty());

        VehicleResponse result = vehicleService.publishVehicle(1L);

        assertNotNull(result);
        verify(vehicleRepository).save(any(Car.class));
    }
}
