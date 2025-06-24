package com.unisew.profile_service.services;

import com.unisew.profile_service.requests.CreateServiceRequest;
import com.unisew.profile_service.requests.UpdateServiceRequest;
import com.unisew.profile_service.responses.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface ServicesService {
    ResponseEntity<ResponseObject> getAllService();

    ResponseEntity<ResponseObject> createService(CreateServiceRequest request);

    ResponseEntity<ResponseObject> updateService(UpdateServiceRequest request);
}
