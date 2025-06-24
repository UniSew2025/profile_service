package com.unisew.profile_service.controllers;

import com.unisew.profile_service.responses.ResponseObject;
import com.unisew.profile_service.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/designer/list")
    public ResponseEntity<ResponseObject> getAllDesignerProfile() {
        return profileService.getAllDesignerProfile();
    }

    @GetMapping("/garment/list")
    public ResponseEntity<ResponseObject> getAllGarmentProfile() {
        return profileService.getAllGarmentProfile();
    }

}
