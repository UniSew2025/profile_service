package com.unisew.profile_service.controllers;

import com.unisew.profile_service.models.PackageService;
import com.unisew.profile_service.requests.CreatePackageRequest;
import com.unisew.profile_service.requests.UpdatePackageRequest;
import com.unisew.profile_service.responses.ResponseObject;
import com.unisew.profile_service.services.PackageServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/packages")
@RequiredArgsConstructor
public class PackagesController {

    private final PackageServices packageServices;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getPackageInfo(@PathVariable("id") int id) {
        return packageServices.getPackageInfo(id);
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> createPackage(@RequestBody CreatePackageRequest request) {
        return packageServices.createPackage(request);
    }

    @PutMapping("")
    public ResponseEntity<ResponseObject> updatePackage(@RequestBody UpdatePackageRequest request) {
        return packageServices.updatePackage(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> disablePackage(@PathVariable("id") int id) {
        return packageServices.disablePackage(id);
    }
}
