package com.unisew.profile_service.services;

import com.unisew.profile_service.requests.CreatePackageRequest;
import com.unisew.profile_service.requests.CreateProfileRequest;
import com.unisew.profile_service.requests.CreateServiceRequest;
import com.unisew.profile_service.requests.UpdatePackageRequest;
import com.unisew.profile_service.requests.UpdateDesignerProfileRequest;
import com.unisew.profile_service.requests.UpdateServiceRequest;
import com.unisew.profile_service.responses.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ProfileService {

    ResponseEntity<ResponseObject> getAllDesignerProfile();

    ResponseEntity<ResponseObject> getAllGarmentProfile();

    Map<String, Object> createProfile(CreateProfileRequest request);

    Map<String, Object> getProfileInfo(int accountId);

    ResponseEntity<ResponseObject> updateDesignerProfile(UpdateDesignerProfileRequest request);

    ResponseEntity<ResponseObject> getAllPackages(int designerId);

    ResponseEntity<ResponseObject> getPackageInfo(int packageId);

    ResponseEntity<ResponseObject> createPackage(CreatePackageRequest request);

    ResponseEntity<ResponseObject> updatePackage(UpdatePackageRequest request);

    ResponseEntity<ResponseObject> disablePackage(int packageId);

    ResponseEntity<ResponseObject> getAllService();

    ResponseEntity<ResponseObject> createService(CreateServiceRequest request);

    ResponseEntity<ResponseObject> updateService(UpdateServiceRequest request);

}
