package com.unisew.profile_service.services.implementors;

import com.unisew.profile_service.enums.Status;
import com.unisew.profile_service.models.Services;
import com.unisew.profile_service.repositories.ServiceRepo;
import com.unisew.profile_service.requests.CreateServiceRequest;
import com.unisew.profile_service.requests.UpdateServiceRequest;
import com.unisew.profile_service.responses.ResponseObject;
import com.unisew.profile_service.services.ServicesService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceServiceImpl implements ServicesService {

    ServiceRepo serviceRepo;

    @Override
    public ResponseEntity<ResponseObject> getAllService() {
        List<Services> services = serviceRepo.findAll();
        List<Map<String, Object>> result = services.stream()
                .map(service -> {
                    Map<String, Object> serviceData = new HashMap<>();
                    serviceData.put("id", service.getId());
                    serviceData.put("rule", service.getRule());
                    serviceData.put("creationDate", service.getCreationDate());
                    serviceData.put("status", service.getStatus());
                    return serviceData;
                })
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseObject.builder()
                        .message("Get all services successfully")
                        .data(result)
                        .build());
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObject> createService(CreateServiceRequest request) {
        Services service = Services.builder()
                .rule(request.getRule())
                .creationDate(LocalDate.now())
                .status(Status.SERVICE_ACTIVE)
                .build();

        serviceRepo.save(service);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseObject.builder()
                        .message("Create service successfully")
                        .build());
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObject> updateService(UpdateServiceRequest request) {
        Services service = serviceRepo.findById(request.getId()).orElse(null);
        if (service == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseObject.builder()
                            .message("Service not found")
                            .data(null)
                            .build());
        }
        service.setRule(request.getRule());
        service.setStatus(Status.valueOf(request.getStatus()));
        serviceRepo.save(service);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseObject.builder()
                        .message("Update service successfully")
                        .build());
    }
}
