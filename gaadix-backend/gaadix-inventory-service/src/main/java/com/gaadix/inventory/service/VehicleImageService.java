package com.gaadix.inventory.service;

import com.gaadix.common.exception.ResourceNotFoundException;
import com.gaadix.inventory.entity.VehicleImage;
import com.gaadix.inventory.repository.VehicleImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleImageService {
    
    private final VehicleImageRepository imageRepository;
    
    @Transactional
    public VehicleImage addImage(Long carId, String imageUrl, String imageType, boolean isPrimary) {
        if (isPrimary) {
            imageRepository.findByCarIdAndIsPrimaryTrue(carId)
                    .ifPresent(img -> {
                        img.setPrimary(false);
                        imageRepository.save(img);
                    });
        }
        
        VehicleImage image = VehicleImage.builder()
                .carId(carId)
                .imageUrl(imageUrl)
                .imageType(imageType)
                .isPrimary(isPrimary)
                .displayOrder(imageRepository.findByCarIdOrderByDisplayOrderAsc(carId).size())
                .build();
        
        return imageRepository.save(image);
    }
    
    @Transactional(readOnly = true)
    public List<VehicleImage> getImagesByCarId(Long carId) {
        return imageRepository.findByCarIdOrderByDisplayOrderAsc(carId);
    }
    
    @Transactional
    public void deleteImage(Long imageId) {
        if (!imageRepository.existsById(imageId)) {
            throw new ResourceNotFoundException("Image not found with id: " + imageId);
        }
        imageRepository.deleteById(imageId);
    }
}
