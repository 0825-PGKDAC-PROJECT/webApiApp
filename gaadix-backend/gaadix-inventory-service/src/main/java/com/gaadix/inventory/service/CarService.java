package com.gaadix.inventory.service;

import com.gaadix.common.dto.CarDTO;
import com.gaadix.common.entity.Car;
import com.gaadix.common.enums.CarStatus;
import com.gaadix.common.exception.ResourceNotFoundException;
import com.gaadix.inventory.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    
    public CarDTO getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        return modelMapper.map(car, CarDTO.class);
    }
    
    public Page<CarDTO> getAllCars(Pageable pageable) {
        return carRepository.findAll(pageable)
                .map(car -> modelMapper.map(car, CarDTO.class));
    }
    
    public Page<CarDTO> getCarsByStatus(CarStatus status, Pageable pageable) {
        return carRepository.findByStatus(status, pageable)
                .map(car -> modelMapper.map(car, CarDTO.class));
    }
    
    public Page<CarDTO> getCarsBySeller(Long sellerId, Pageable pageable) {
        return carRepository.findBySellerId(sellerId, pageable)
                .map(car -> modelMapper.map(car, CarDTO.class));
    }
    
    public List<CarDTO> getFeaturedCars() {
        return carRepository.findByIsFeaturedTrue().stream()
                .map(car -> modelMapper.map(car, CarDTO.class))
                .collect(Collectors.toList());
    }
    
    public List<CarDTO> getCarsNearby(Double latitude, Double longitude, Double radiusKm) {
        return carRepository.findCarsWithinRadius(latitude, longitude, radiusKm).stream()
                .map(car -> modelMapper.map(car, CarDTO.class))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public CarDTO createCar(CarDTO carDTO) {
        Car car = modelMapper.map(carDTO, Car.class);
        car.setStatus(CarStatus.DRAFT);
        car.setViewCount(0);
        car = carRepository.save(car);
        return modelMapper.map(car, CarDTO.class);
    }
    
    @Transactional
    public CarDTO updateCar(Long id, CarDTO carDTO) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        
        modelMapper.map(carDTO, car);
        car.setId(id);
        car = carRepository.save(car);
        return modelMapper.map(car, CarDTO.class);
    }
    
    @Transactional
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException("Car not found with id: " + id);
        }
        carRepository.deleteById(id);
    }
    
    @Transactional
    public void incrementViewCount(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        car.setViewCount(car.getViewCount() + 1);
        carRepository.save(car);
    }
}
