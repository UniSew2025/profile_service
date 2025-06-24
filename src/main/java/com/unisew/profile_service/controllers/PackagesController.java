package com.unisew.profile_service.controllers;

import com.unisew.profile_service.models.PackageService;
import com.unisew.profile_service.responses.ResponseObject;
import com.unisew.profile_service.services.PackageServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/packages")
@RequiredArgsConstructor
public class PackagesController {

    private final PackageServices packageServices;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getPackageInfo(@PathVariable("id") int id) {
        return packageServices.getPackageInfo(id);
    }
}
