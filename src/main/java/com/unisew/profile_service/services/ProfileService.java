package com.unisew.profile_service.services;

import com.unisew.profile_service.responses.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface ProfileService {

    ResponseEntity<ResponseObject> getAllDesignerProfile();

    ResponseEntity<ResponseObject> getAllGarmentProfile();
}
