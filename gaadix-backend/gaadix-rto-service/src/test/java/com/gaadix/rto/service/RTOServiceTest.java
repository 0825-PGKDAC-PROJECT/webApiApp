package com.gaadix.rto.service;

import com.gaadix.rto.dto.RTOVerificationRequest;
import com.gaadix.rto.dto.RTOVerificationResponse;
import com.gaadix.rto.entity.RTOVerificationEntity;
import com.gaadix.rto.repository.RTOVerificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RTOServiceTest {

    @Mock
    private RTOVerificationRepository repository;

    @InjectMocks
    private RTOService rtoService;

    @Test
    void testVerifyVehicle() {
        RTOVerificationRequest request = new RTOVerificationRequest();
        request.setRegistrationNumber("MH12AB1234");
        request.setRtoCode("MH12");
        request.setYear(2020);
        request.setManufacturer("Toyota");
        request.setModel("Camry");
        request.setFuelType("Petrol");

        RTOVerificationEntity entity = new RTOVerificationEntity();
        entity.setId(1L);
        entity.setRegistrationNumber("MH12AB1234");
        when(repository.findByRegistrationNumber(any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(entity);

        RTOVerificationResponse response = rtoService.verifyVehicle(request);
        assertThat(response).isNotNull();
    }

    @Test
    void testValidateRegistrationNumber_Valid() {
        RTOVerificationRequest request = new RTOVerificationRequest();
        request.setRegistrationNumber("MH12AB1234");
        request.setRtoCode("MH12");
        request.setYear(2020);
        request.setManufacturer("Honda");
        request.setModel("City");
        request.setFuelType("Diesel");

        RTOVerificationEntity entity = new RTOVerificationEntity();
        when(repository.findByRegistrationNumber(any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(entity);

        RTOVerificationResponse response = rtoService.verifyVehicle(request);
        assertThat(response).isNotNull();
    }

    @Test
    void testValidateRegistrationNumber_Invalid() {
        RTOVerificationRequest request = new RTOVerificationRequest();
        request.setRegistrationNumber("INVALID");
        request.setRtoCode("XX");
        request.setYear(2015);
        request.setManufacturer("Test");
        request.setModel("Test");
        request.setFuelType("Petrol");

        RTOVerificationEntity entity = new RTOVerificationEntity();
        when(repository.findByRegistrationNumber(any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(entity);

        RTOVerificationResponse response = rtoService.verifyVehicle(request);
        assertThat(response).isNotNull();
    }
}
