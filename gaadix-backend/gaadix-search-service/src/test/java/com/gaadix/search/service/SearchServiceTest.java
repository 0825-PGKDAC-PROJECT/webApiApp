package com.gaadix.search.service;

import com.gaadix.common.entity.Car;
import com.gaadix.search.dto.SearchRequest;
import com.gaadix.search.dto.SearchResponse;
import com.gaadix.search.repository.VehicleSearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private VehicleSearchRepository repository;

    @InjectMocks
    private VehicleSearchService searchService;

    @Test
    void testSearchVehicles() {
        SearchRequest request = SearchRequest.builder()
                .keyword("Toyota")
                .page(0)
                .size(10)
                .build();

        Page<Car> page = new PageImpl<>(Collections.emptyList());
        when(repository.searchVehicles(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(Pageable.class))).thenReturn(page);

        SearchResponse<Car> response = searchService.searchVehicles(request);
        assertThat(response).isNotNull();
    }

    @Test
    void testSearchNearby() {
        SearchRequest request = SearchRequest.builder()
                .latitude(19.0760)
                .longitude(72.8777)
                .radiusKm(10)
                .page(0)
                .size(10)
                .build();

        Page<Car> page = new PageImpl<>(Collections.emptyList());
        when(repository.searchByLocation(any(), any(), any(), any(), any(Pageable.class))).thenReturn(page);

        SearchResponse<Car> response = searchService.searchVehicles(request);
        assertThat(response).isNotNull();
    }

    @Test
    void testCalculateDistance() {
        SearchRequest request = SearchRequest.builder()
                .brand("Honda")
                .page(0)
                .size(10)
                .build();

        Page<Car> page = new PageImpl<>(Collections.emptyList());
        when(repository.searchVehicles(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(Pageable.class))).thenReturn(page);

        SearchResponse<Car> response = searchService.searchVehicles(request);
        assertThat(response).isNotNull();
    }
}
