package com.gaadix.inventory.service;

import com.gaadix.common.exception.ResourceNotFoundException;
import com.gaadix.inventory.dto.InspectionRequest;
import com.gaadix.inventory.entity.VehicleInspection;
import com.gaadix.inventory.repository.VehicleInspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleInspectionService {
    
    private final VehicleInspectionRepository inspectionRepository;
    
    @Transactional
    public VehicleInspection createInspection(InspectionRequest request) {
        VehicleInspection inspection = VehicleInspection.builder()
                .carId(request.getCarId())
                .inspectionDate(request.getInspectionDate())
                .inspectorName(request.getInspectorName())
                .overallScore(request.getOverallScore())
                .engineScore(request.getEngineScore())
                .transmissionScore(request.getTransmissionScore())
                .suspensionScore(request.getSuspensionScore())
                .brakesScore(request.getBrakesScore())
                .electricalScore(request.getElectricalScore())
                .bodyScore(request.getBodyScore())
                .interiorScore(request.getInteriorScore())
                .notes(request.getNotes())
                .recommendations(request.getRecommendations())
                .build();
        
        return inspectionRepository.save(inspection);
    }
    
    @Transactional(readOnly = true)
    public List<VehicleInspection> getInspectionsByCarId(Long carId) {
        return inspectionRepository.findByCarIdOrderByInspectionDateDesc(carId);
    }
    
    @Transactional(readOnly = true)
    public VehicleInspection getLatestInspection(Long carId) {
        return inspectionRepository.findFirstByCarIdOrderByInspectionDateDesc(carId)
                .orElseThrow(() -> new ResourceNotFoundException("No inspection found for car id: " + carId));
    }
}
