package com.gaadix.inventory.controller;

import com.gaadix.common.dto.ApiResponse;
import com.gaadix.common.enums.CarStatus;
import com.gaadix.inventory.dto.VehicleRequest;
import com.gaadix.inventory.dto.VehicleResponse;
import com.gaadix.inventory.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    
    private final VehicleService vehicleService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<VehicleResponse>> createVehicle(
            @Valid @RequestBody VehicleRequest request,
            @RequestHeader("X-User-Id") Long sellerId) {
        VehicleResponse response = vehicleService.createVehicle(request, sellerId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Vehicle created successfully"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleResponse>> getVehicle(@PathVariable Long id) {
        VehicleResponse response = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Vehicle retrieved successfully"));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<VehicleResponse>>> getAllVehicles(
            @RequestParam(required = false) CarStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<VehicleResponse> response = vehicleService.getAllVehicles(status, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.success(response, "Vehicles retrieved successfully"));
    }
    
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<ApiResponse<Page<VehicleResponse>>> getVehiclesBySeller(
            @PathVariable Long sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<VehicleResponse> response = vehicleService.getVehiclesBySeller(sellerId, PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.success(response, "Seller vehicles retrieved successfully"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleResponse>> updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody VehicleRequest request) {
        VehicleResponse response = vehicleService.updateVehicle(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Vehicle updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Vehicle deleted successfully"));
    }
    
    @PostMapping("/{id}/publish")
    public ResponseEntity<ApiResponse<VehicleResponse>> publishVehicle(@PathVariable Long id) {
        VehicleResponse response = vehicleService.publishVehicle(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Vehicle published successfully"));
    }
}
