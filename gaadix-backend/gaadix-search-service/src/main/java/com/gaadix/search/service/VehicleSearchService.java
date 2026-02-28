package com.gaadix.search.service;

import com.gaadix.common.entity.Car;
import com.gaadix.common.enums.CarStatus;
import com.gaadix.search.dto.SearchRequest;
import com.gaadix.search.dto.SearchResponse;
import com.gaadix.search.repository.VehicleSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleSearchService {
    
    private final VehicleSearchRepository searchRepository;
    
    @Transactional(readOnly = true)
    public SearchResponse<Car> searchVehicles(SearchRequest request) {
        Sort sort = Sort.by(
                "DESC".equalsIgnoreCase(request.getSortDirection()) 
                        ? Sort.Direction.DESC 
                        : Sort.Direction.ASC,
                request.getSortBy()
        );
        
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
        
        Page<Car> page;
        
        if (request.getLatitude() != null && request.getLongitude() != null && request.getRadiusKm() != null) {
            String statusStr = request.getStatus() != null ? request.getStatus().name() : null;
            page = searchRepository.searchByLocation(
                    request.getLatitude(),
                    request.getLongitude(),
                    request.getRadiusKm(),
                    statusStr,
                    pageable
            );
        } else {
            page = searchRepository.searchVehicles(
                    request.getKeyword(),
                    request.getBrand(),
                    request.getModel(),
                    request.getMinYear(),
                    request.getMaxYear(),
                    request.getFuelType(),
                    request.getTransmission(),
                    request.getMinPrice(),
                    request.getMaxPrice(),
                    request.getMaxOdometer(),
                    request.getCity(),
                    request.getState(),
                    request.getStatus() != null ? request.getStatus() : CarStatus.AVAILABLE,
                    pageable
            );
        }
        
        return SearchResponse.<Car>builder()
                .results(page.getContent())
                .totalResults(page.getTotalElements())
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .pageSize(page.getSize())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}
