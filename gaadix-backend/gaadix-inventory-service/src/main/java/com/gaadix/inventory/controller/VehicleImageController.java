package com.gaadix.inventory.controller;

import com.gaadix.common.dto.ApiResponse;
import com.gaadix.inventory.entity.VehicleImage;
import com.gaadix.inventory.service.VehicleImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles/{carId}/images")
@RequiredArgsConstructor
public class VehicleImageController {
    
    private final VehicleImageService imageService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<VehicleImage>> addImage(
            @PathVariable Long carId,
            @RequestParam String imageUrl,
            @RequestParam(required = false) String imageType,
            @RequestParam(defaultValue = "false") boolean isPrimary) {
        VehicleImage image = imageService.addImage(carId, imageUrl, imageType, isPrimary);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Image added successfully", image));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<VehicleImage>>> getImages(@PathVariable Long carId) {
        List<VehicleImage> images = imageService.getImagesByCarId(carId);
        return ResponseEntity.ok(ApiResponse.success("Images retrieved successfully", images));
    }
    
    @DeleteMapping("/{imageId}")
    public ResponseEntity<ApiResponse<Void>> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok(ApiResponse.success("Image deleted successfully", null));
    }
}
