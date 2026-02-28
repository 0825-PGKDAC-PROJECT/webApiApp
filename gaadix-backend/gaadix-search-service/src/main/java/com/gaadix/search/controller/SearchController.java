package com.gaadix.search.controller;

import com.gaadix.common.dto.ApiResponse;
import com.gaadix.common.entity.Car;
import com.gaadix.search.dto.SearchRequest;
import com.gaadix.search.dto.SearchResponse;
import com.gaadix.search.service.VehicleSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {
    
    private final VehicleSearchService searchService;
    
    @PostMapping("/vehicles")
    public ResponseEntity<ApiResponse<SearchResponse<Car>>> searchVehicles(
            @RequestBody SearchRequest request) {
        SearchResponse<Car> response = searchService.searchVehicles(request);
        return ResponseEntity.ok(ApiResponse.success("Search completed successfully", response));
    }
    
    @GetMapping("/vehicles")
    public ResponseEntity<ApiResponse<SearchResponse<Car>>> searchVehiclesGet(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        SearchRequest request = SearchRequest.builder()
                .keyword(keyword)
                .brand(brand)
                .city(city)
                .page(page)
                .size(size)
                .build();
        
        SearchResponse<Car> response = searchService.searchVehicles(request);
        return ResponseEntity.ok(ApiResponse.success("Search completed successfully", response));
    }
}
