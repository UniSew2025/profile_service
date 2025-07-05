package com.unisew.profile_service.controllers;

import com.unisew.profile_service.requests.CreateProfileRequest;
import com.unisew.profile_service.responses.ResponseObject;
import com.unisew.profile_service.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v2/profile")
@RequiredArgsConstructor
public class InternalController {

    private final ProfileService profileService;

    @PostMapping("")
    public Map<String, Object> createProfile(@RequestBody CreateProfileRequest request) {
        return profileService.createProfile(request);
    }

    @GetMapping("")
    public Map<String, Object> getProfile(@RequestParam(name = "accountId") int accountId) {
        return profileService.getProfileInfo(accountId);
    }

    @GetMapping("/package")
    public Map<String, Object> getPackage(@RequestParam(name = "packageId") int packageId) {
        return profileService.getPackage(packageId);
    }

}
