package com.unisew.profile_service.controllers;

import com.unisew.profile_service.requests.CreateServiceRequest;
import com.unisew.profile_service.requests.UpdateServiceRequest;
import com.unisew.profile_service.responses.ResponseObject;
import com.unisew.profile_service.services.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
public class ServiceController {

    private final ServicesService servicesService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllService() {
        return servicesService.getAllService();
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> createService(@RequestBody CreateServiceRequest request) {
        return servicesService.createService(request);
    }

    @PutMapping("")
    public ResponseEntity<ResponseObject> updateService(@RequestBody UpdateServiceRequest request) {
        return servicesService.updateService(request);
    }
}
