package com.unisew.profile_service.controllers;

import com.unisew.profile_service.responses.ResponseObject;
import com.unisew.profile_service.services.DesignerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class DesignerController {

    private final DesignerService designerService;

    @GetMapping("/designer/list")
    public ResponseEntity<ResponseObject> getAllDesignerProfile() {
        return designerService.getAllDesignerProfile();
    }
}
