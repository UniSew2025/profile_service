package com.unisew.profile_service.services.implementors;

import com.unisew.profile_service.enums.Status;
import com.unisew.profile_service.models.Designer;
import com.unisew.profile_service.models.Package;
import com.unisew.profile_service.models.PackageService;
import com.unisew.profile_service.models.Services;
import com.unisew.profile_service.repositories.DesignerRepo;
import com.unisew.profile_service.repositories.PackageRepo;
import com.unisew.profile_service.repositories.PackageServiceRepo;
import com.unisew.profile_service.repositories.ServiceRepo;
import com.unisew.profile_service.requests.CreatePackageRequest;
import com.unisew.profile_service.requests.UpdatePackageRequest;
import com.unisew.profile_service.responses.ResponseObject;
import com.unisew.profile_service.services.PackageServices;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PackageServicesImpl implements PackageServices {

    PackageRepo packageRepo;

    DesignerRepo designerRepo;

    PackageServiceRepo packageServiceRepo;

    ServiceRepo serviceRepo;

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

    //----------------------------------------Create Package----------------------------------------

    @Override
    @Transactional
    public ResponseEntity<ResponseObject> createPackage(CreatePackageRequest request) {
        Designer designer = designerRepo.findById(request.getDesignerId())
                .orElseThrow(() -> new RuntimeException("Designer not found"));

        Package pkg = Package.builder()
                .name(request.getName())
                .headerContent(request.getHeaderContent())
                .deliveryDuration(request.getDeliveryDuration())
                .revisionTime(request.getRevisionTime())
                .fee(request.getFee())
                .status(Status.PACKAGE_ACTIVE)
                .designer(designer)
                .build();

        Package savedPkg = packageRepo.save(pkg);

        List<PackageService> packageServices = request.getServiceIds().stream()
                .map(serviceId -> {
                    Services service = serviceRepo.findById(serviceId).orElse(null);
                    return PackageService.builder()
                            .id(new PackageService.ID(savedPkg.getId(), serviceId))
                            .pkg(savedPkg)
                            .service(service)
                            .build();
                })
                .toList();

        packageServiceRepo.saveAll(packageServices);
        pkg.setPackageServices(packageServices);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseObject.builder()
                        .message("Create package successfully")
                        .build());
    }


    //----------------------------------------Update Package----------------------------------------

    @Override
    @Transactional
    public ResponseEntity<ResponseObject> updatePackage(UpdatePackageRequest request) {
        Package pkg = packageRepo.findById(request.getId()).orElse(null);
        if (pkg == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseObject.builder()
                            .message("Package not found")
                            .build());
        }
        pkg.setName(request.getName());
        pkg.setHeaderContent(request.getHeaderContent());
        pkg.setDeliveryDuration(request.getDeliveryDuration());
        pkg.setRevisionTime(request.getRevisionTime());
        pkg.setFee(request.getFee());

        if (request.getDesignerId() != null && !request.getDesignerId().equals(pkg.getDesigner().getId())) {
            Designer designer = designerRepo.findById(request.getDesignerId()).orElse(null);
            pkg.setDesigner(designer);
        }

        packageServiceRepo.deleteAll(pkg.getPackageServices());

        List<PackageService> newPackageServices = request.getServiceIds().stream()
                .map(serviceId -> {
                    Services service = serviceRepo.findById(serviceId).orElse(null);
                    return PackageService.builder()
                            .id(new PackageService.ID(pkg.getId(), serviceId))
                            .pkg(pkg)
                            .service(service)
                            .build();
                })
                .toList();

        packageServiceRepo.saveAll(newPackageServices);
        pkg.setPackageServices(new ArrayList<>(newPackageServices));
        packageRepo.save(pkg);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseObject.builder()
                        .message("Update package successfully")
                        .build());
    }

    // ----------------------------------------Disable Package----------------------------------------
    @Override
    public ResponseEntity<ResponseObject> disablePackage(int id) {
        Package pkg = packageRepo.findById(id).orElse(null);
        if (pkg == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseObject.builder()
                            .message("Package not found")
                            .build()
            );
        }
        pkg.setStatus(Status.PACKAGE_UN_ACTIVE);
        packageRepo.save(pkg);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseObject.builder()
                        .message("Package disabled successfully")
                        .build());
    }

}
