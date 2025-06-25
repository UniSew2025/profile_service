package com.unisew.profile_service.services;

import com.unisew.profile_service.requests.CreatePackageRequest;
import com.unisew.profile_service.requests.CreateProfileRequest;
import com.unisew.profile_service.requests.CreateServiceRequest;
import com.unisew.profile_service.requests.UpdatePackageRequest;
import com.unisew.profile_service.requests.UpdateServiceRequest;
import com.unisew.profile_service.responses.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ProfileService {

    ResponseEntity<ResponseObject> getAllDesignerProfile();

    ResponseEntity<ResponseObject> getAllGarmentProfile();

    Map<String, Object> createProfile(CreateProfileRequest request);

    Map<String, Object> getProfileInfo(int accountId);

    ResponseEntity<ResponseObject> getPackageInfo(int id);

    ResponseEntity<ResponseObject> createPackage(CreatePackageRequest request);

    ResponseEntity<ResponseObject> updatePackage(UpdatePackageRequest request);

    ResponseEntity<ResponseObject> disablePackage(int id);

    ResponseEntity<ResponseObject> getAllService();

    ResponseEntity<ResponseObject> createService(CreateServiceRequest request);

    ResponseEntity<ResponseObject> updateService(UpdateServiceRequest request);

}
