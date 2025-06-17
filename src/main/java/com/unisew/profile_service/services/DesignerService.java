package com.unisew.profile_service.services;

import com.unisew.profile_service.models.Services;
import com.unisew.profile_service.responses.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DesignerService {

    ResponseEntity<ResponseObject> getAllDesignerProfile();

}
