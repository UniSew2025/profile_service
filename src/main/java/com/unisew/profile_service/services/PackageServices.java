package com.unisew.profile_service.services;

import com.unisew.profile_service.requests.CreatePackageRequest;
import com.unisew.profile_service.requests.UpdatePackageRequest;
import com.unisew.profile_service.responses.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface PackageServices {

    ResponseEntity<ResponseObject> getPackageInfo(int id);

    ResponseEntity<ResponseObject> createPackage(CreatePackageRequest request);

    ResponseEntity<ResponseObject> updatePackage(UpdatePackageRequest request);

    ResponseEntity<ResponseObject> disablePackage(int id);
}
