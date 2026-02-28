package com.gaadix.inventory.controller;

import com.gaadix.common.dto.ApiResponse;
import com.gaadix.inventory.dto.InspectionRequest;
import com.gaadix.inventory.entity.VehicleInspection;
import com.gaadix.inventory.service.VehicleInspectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles/{carId}/inspections")
@RequiredArgsConstructor
public class VehicleInspectionController {
    
    private final VehicleInspectionService inspectionService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<VehicleInspection>> createInspection(
            @PathVariable Long carId,
            @Valid @RequestBody InspectionRequest request) {
        request.setCarId(carId);
        VehicleInspection inspection = inspectionService.createInspection(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Inspection created successfully", inspection));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<VehicleInspection>>> getInspections(@PathVariable Long carId) {
        List<VehicleInspection> inspections = inspectionService.getInspectionsByCarId(carId);
        return ResponseEntity.ok(ApiResponse.success("Inspections retrieved successfully", inspections));
    }
    
    @GetMapping("/latest")
    public ResponseEntity<ApiResponse<VehicleInspection>> getLatestInspection(@PathVariable Long carId) {
        VehicleInspection inspection = inspectionService.getLatestInspection(carId);
        return ResponseEntity.ok(ApiResponse.success("Latest inspection retrieved successfully", inspection));
    }
}
