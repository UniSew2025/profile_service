package com.unisew.profile_service.controllers;

import com.unisew.profile_service.requests.CreatePackageRequest;
import com.unisew.profile_service.requests.CreateServiceRequest;
import com.unisew.profile_service.requests.UpdatePackageRequest;
import com.unisew.profile_service.requests.UpdateServiceRequest;
import com.unisew.profile_service.responses.ResponseObject;
import com.unisew.profile_service.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/designer/list")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SCHOOL')" )
    public ResponseEntity<ResponseObject> getAllDesignerProfile() {
        return profileService.getAllDesignerProfile();
    }

    @GetMapping("/garment/list")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SCHOOL')" )
    public ResponseEntity<ResponseObject> getAllGarmentProfile() {
        return profileService.getAllGarmentProfile();
    }

    @GetMapping("/service")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SCHOOL') or hasRole('DESIGNER')" )
    public ResponseEntity<ResponseObject> getAllService() {
        return profileService.getAllService();
    }

    @PostMapping("/service")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> createService(@RequestBody CreateServiceRequest request) {
        return profileService.createService(request);
    }

    @PutMapping("/service")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> updateService(@RequestBody UpdateServiceRequest request) {
        return profileService.updateService(request);
    }

    @GetMapping("/package/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SCHOOL') or hasRole('DESIGNER')")
    public ResponseEntity<ResponseObject> getPackageInfo(@PathVariable("id") int id) {
        return profileService.getPackageInfo(id);
    }

    @PostMapping("/package")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> createPackage(@RequestBody CreatePackageRequest request) {
        return profileService.createPackage(request);
    }

    @PutMapping("/package")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DESIGNER')")
    public ResponseEntity<ResponseObject> updatePackage(@RequestBody UpdatePackageRequest request) {
        return profileService.updatePackage(request);
    }

    @DeleteMapping("/package/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DESIGNER')")
    public ResponseEntity<ResponseObject> disablePackage(@PathVariable("id") int id) {
        return profileService.disablePackage(id);
    }
}
