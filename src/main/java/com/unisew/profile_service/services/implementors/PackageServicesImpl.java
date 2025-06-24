package com.unisew.profile_service.services.implementors;

import com.unisew.profile_service.models.Designer;
import com.unisew.profile_service.models.Package;
import com.unisew.profile_service.models.PackageService;
import com.unisew.profile_service.models.Services;
import com.unisew.profile_service.repositories.DesignerRepo;
import com.unisew.profile_service.repositories.PackageRepo;
import com.unisew.profile_service.responses.ResponseObject;
import com.unisew.profile_service.services.PackageServices;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PackageServicesImpl implements PackageServices {

    PackageRepo packageRepo;

    DesignerRepo designerRepo;

    @Override
    public ResponseEntity<ResponseObject> getPackageInfo(int id) {
        Package pkg = packageRepo.findById(id).orElse(null);
        if (pkg == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .message("Get package info successfully")
                        .data(buildPackage(pkg))
                        .build()
        );
    }

    private Map<String, Object> buildPackage(Package pkg) {
        Map<String, Object> data = new HashMap<>();
        data.put("pkgName", pkg.getName());
        data.put("headerContent", pkg.getHeaderContent());
        data.put("deliveryDuration", pkg.getDeliveryDuration());
        data.put("revisionTime", pkg.getRevisionTime());
        data.put("fee", pkg.getFee());
        data.put("status", pkg.getStatus());
        data.put("designerInfo", buildDesigner(pkg.getDesigner()));
        data.put("services", buildServices(pkg.getPackageServices()));
        return data;
    }

    private Map<String, Object> buildDesigner(Designer designer) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", designer.getProfile().getName());
        data.put("phone", designer.getProfile().getPhone());
        data.put("avatar", designer.getProfile().getAvatar());
        data.put("shortPreview", designer.getShortPreview());
        data.put("thumbnail", designer.getThumbnail_img());
        data.put("bio", designer.getBio());
        return data;
    }

    private List<Map<String, Object>> buildServices(List<PackageService> pkgServices) {
        return pkgServices.stream()
                .map(packageService -> {
                    Services service = packageService.getService();
                    Map<String, Object> serviceData = new HashMap<>();
                    serviceData.put("id", service.getId());
                    serviceData.put("rule", service.getRule());
                    serviceData.put("creationDate", service.getCreationDate());
                    serviceData.put("status", service.getStatus());
                    return serviceData;
                })
                .toList();
    }
}
