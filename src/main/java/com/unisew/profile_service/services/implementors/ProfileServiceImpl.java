package com.unisew.profile_service.services.implementors;

import com.unisew.profile_service.enums.Role;
import com.unisew.profile_service.enums.Status;
import com.unisew.profile_service.models.Customer;
import com.unisew.profile_service.models.Partner;
import com.unisew.profile_service.models.Package;
import com.unisew.profile_service.models.ThumbnailImage;
import com.unisew.profile_service.repositories.PartnerRepo;
import com.unisew.profile_service.repositories.PackageRepo;
import com.unisew.profile_service.repositories.CustomerRepo;
import com.unisew.profile_service.requests.CreatePackageRequest;
import com.unisew.profile_service.requests.CreateProfileRequest;
import com.unisew.profile_service.requests.UpdateDesignerProfileRequest;
import com.unisew.profile_service.requests.UpdateGarmentProfileRequest;
import com.unisew.profile_service.requests.UpdatePackageRequest;
import com.unisew.profile_service.requests.UpdateSchoolProfileRequest;
import com.unisew.profile_service.responses.ResponseObject;
import com.unisew.profile_service.services.AccountService;
import com.unisew.profile_service.services.ProfileService;
import com.unisew.profile_service.validations.UpdateDesignerValidation;
import com.unisew.profile_service.validations.UpdateGarmentValidation;
import com.unisew.profile_service.validations.UpdateSchoolValidation;
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
public class ProfileServiceImpl implements ProfileService {

    PartnerRepo partnerRepo;

    CustomerRepo customerRepo;

    PackageRepo packageRepo;

    AccountService accountService;
//    ServiceRepo serviceRepo;
//
//    PackageServiceRepo packageServiceRepo;

    // --------------------------------------------Designer Profile--------------------------------------------
    @Override
    public ResponseEntity<ResponseObject> getAllDesignerProfile() {
        List<Partner> partners = partnerRepo.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .message("Get designer profiles successfully")
                        .data(buildDesigners(partners))
                        .build()
        );
    }


    private List<Map<String, Object>> buildDesigners(List<Partner> partners) {
        return partners.stream()
                .filter(designer ->
                        {
                            Map<String, Object> map = accountService.getAccountById(designer.getCustomer().getAccountId());
                            return map.get("role").equals(Role.DESIGNER.getValue().toLowerCase());
                        }
                )
                .map(designer -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", designer.getId());
                    map.put("outsidePreview", designer.getOutsidePreview());
                    map.put("insidePreview", designer.getInsidePreview());
                    map.put("startTime", designer.getStartTime());
                    map.put("endTime", designer.getEndTime());
                    map.put("rating", designer.getRating());
                    map.put("busy", designer.isBusy());
                    map.put("profile", buildProfile(designer.getCustomer()));
                    map.put("package", buildPackage(designer.getPackages()));
                    map.put("thumbnails", buildThumbnailResponse(designer.getThumbnailImages()));
                    return map;
                })
                .toList();
    }

    private Map<String, Object> buildProfile(Customer customer) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", customer.getId());
        map.put("name", customer.getName());
        map.put("phone", customer.getPhone());
        map.put("avatar", customer.getAvatar());
        return map;
    }

    private List<Map<String, Object>> buildPackage(List<Package> pkgs) {
        return pkgs.stream()
                .map(pkg -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", pkg.getId());
                    map.put("name", pkg.getName());
                    map.put("headerContent", pkg.getHeaderContent());
                    map.put("deliveryDuration", pkg.getDeliveryDuration());
                    map.put("revisionTime", pkg.getRevisionTime());
                    map.put("fee", pkg.getFee());
                    map.put("status", pkg.getStatus());
//                    map.put("services", buildService(pkg.getId()));
                    return map;
                })
                .toList();
    }

//    private List<Map<String, Object>> buildService(int pkgId) {
//        List<Services> services = serviceRepo.findAllByPackageServices_Pkg_Id(pkgId);
//        return services.stream()
//                .map(sv -> {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("id", sv.getId());
//                    map.put("rule", sv.getRule());
//                    map.put("creation_date", sv.getCreationDate());
//                    map.put("status", sv.getStatus());
//                    return map;
//                })
//                .toList();
//    }

    //--------------------------------------------Garment Profile--------------------------------------------

    @Override
    public ResponseEntity<ResponseObject> getAllGarmentProfile() {
        List<Partner> partners = partnerRepo.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .message("Get garment profiles successfully")
                        .data(buildGarments(partners))
                        .build()
        );
    }

    private List<Map<String, Object>> buildGarments(List<Partner> partners) {
        return partners.stream()
                .filter(garment ->
                        {
                            Map<String, Object> map = accountService.getAccountById(garment.getCustomer().getAccountId());
                            return map.get("role").equals(Role.GARMENT_FACTORY.getValue().toLowerCase());
                        }
                )
                .map(garment -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", garment.getId());
                    map.put("outsidePreview", garment.getOutsidePreview());
                    map.put("insidePreview", garment.getInsidePreview());
                    map.put("startTime", garment.getStartTime());
                    map.put("endTime", garment.getEndTime());
                    map.put("rating", garment.getRating());
                    map.put("busy", garment.isBusy());
                    map.put("profile", buildProfile(garment.getCustomer()));
                    map.put("thumbnails", buildThumbnailResponse(garment.getThumbnailImages()));
                    return map;
                })
                .toList();
    }

    //--------------------------------------------Update Designer Profile--------------------------------------------
    @Override
    public ResponseEntity<ResponseObject> updateDesignerProfile(UpdateDesignerProfileRequest request) {
        String error = UpdateDesignerValidation.validate(request);
        if (!error.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.builder().message(error).build());
        }

        Customer customer = customerRepo.findByAccountId(request.getAccountId()).orElse(null);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseObject.builder().message("Profile not found for this account").build());
        }
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        Partner partner = customer.getPartner();
        if (partner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseObject.builder().message("Designer not found for this account").build());
        }
        List<ThumbnailImage> thumbnailImages = new ArrayList<>();
        partner.setStartTime(request.getStartDate());
        partner.setEndTime(request.getEndDate());
        partner.setThumbnailImages(thumbnailImages);
        partner.setOutsidePreview(request.getOutsidePreview());
        partner.setInsidePreview(request.getInsidePreview());

        customerRepo.save(customer);
        partnerRepo.save(partner);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseObject.builder()
                        .message("Update profile successfully")
                        .build());
    }

    //--------------------------------------------Update School Profile--------------------------------------------
    @Override
    public ResponseEntity<ResponseObject> updateSchoolProfile(UpdateSchoolProfileRequest request) {
        String error = UpdateSchoolValidation.validate(request);
        if (!error.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.builder().message(error).build());
        }
        Customer customer = customerRepo.findByAccountId(request.getAccountId()).orElse(null);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseObject.builder().message("Profile not found for this account").build());
        }
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customerRepo.save(customer);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseObject.builder()
                        .message("Update profile successfully")
                        .build());
    }

    //--------------------------------------------Update Garment Profile--------------------------------------------
    @Override
    public ResponseEntity<ResponseObject> updateGarmentProfile(UpdateGarmentProfileRequest request) {
        String error = UpdateGarmentValidation.validate(request);
        if (!error.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.builder().message(error).build());
        }
        Customer customer = customerRepo.findByAccountId(request.getAccountId()).orElse(null);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseObject.builder().message("Profile not found for this account").build());
        }
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        Partner partner = customer.getPartner();
        if (partner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseObject.builder().message("Partner not found for this account").build());
        }
        partner.setInsidePreview(request.getInsidePreview());
        partner.setOutsidePreview(request.getOutsidePreview());
        partner.setStartTime(request.getStartTime());
        partner.setEndTime(request.getEndTime());
        partner.setCustomer(customer);
        customerRepo.save(customer);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseObject.builder()
                        .message("Update profile successfully")
                        .build());
    }


    // --------------------------------------------Internal User Profile--------------------------------------------
    @Override
    @Transactional
    public Map<String, Object> createProfile(CreateProfileRequest request) {
        Customer customer = customerRepo.save(
                Customer.builder()
                        .accountId(request.getAccountId())
                        .avatar(request.getAvatar())
                        .name(request.getName())
                        .phone("N/A")
                        .address("N/A")
                        .build()
        );

        Map<String, Object> profileData = buildProfileResponse(customer);
        if (!request.getRole().equalsIgnoreCase(Role.SCHOOL.getValue())) {
            profileData.put("partner", createPartnerByProfile(customer));
        }
        return profileData;
    }

    private Map<String, Object> createPartnerByProfile(Customer customer) {
        Partner partner = partnerRepo.save(
                Partner.builder()
                        .customer(customer)
                        .outsidePreview("N/A")
                        .insidePreview("N/A")
                        .startTime(null)
                        .endTime(null)
                        .rating(0)
                        .build()
        );

        return buildPartnerResponse(partner);
    }

    @Override
    public Map<String, Object> getProfileInfo(int accountId) {
        Customer customer = customerRepo.findByAccountId(accountId).orElse(null);
        if (customer == null) {
            return null;
        }

        Map<String, Object> profileData = buildProfileResponse(customer);
        if (customer.getPartner() != null) {
            profileData.put("partner", buildPartnerResponse(customer.getPartner()));
        }

        return profileData;
    }

    private Map<String, Object> buildProfileResponse(Customer customer) {
        Map<String, Object> profileData = new HashMap<>();
        profileData.put("id", customer.getId());
        profileData.put("name", customer.getName());
        profileData.put("avatar", customer.getAvatar());
        profileData.put("phone", customer.getPhone());

        return profileData;
    }

    private Map<String, Object> buildPartnerResponse(Partner partner) {
        Map<String, Object> designerData = new HashMap<>();
        designerData.put("id", partner.getId());
        designerData.put("rating", partner.getRating());
        designerData.put("thumbnail", buildThumbnailResponse(partner.getThumbnailImages()));
        designerData.put("startTime", partner.getStartTime());
        designerData.put("endTime", partner.getEndTime());
        designerData.put("outsidePreview", partner.getOutsidePreview());
        designerData.put("insidePreview", partner.getInsidePreview());
        designerData.put("isBusy", partner.isBusy());
        return designerData;
    }

    private List<Map<String, Object>> buildThumbnailResponse(List<ThumbnailImage> thumbnailImages) {
        return thumbnailImages.stream()
                .map(thumbnailImg -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("id", thumbnailImg.getId());
                    data.put("imageUrl", thumbnailImg.getImageUrl());
                    data.put("name", thumbnailImg.getName());
                    return data;
                })
                .toList();
    }

    @Override
    public Map<String, Object> getPackage(int id) {
        Package pkg = packageRepo.findById(id).orElse(null);
        if (pkg == null) {
            return null;
        }
        return buildPackage(pkg);
    }

    //---------------------------------------------Service--------------------------------------------
//    @Override
//    public ResponseEntity<ResponseObject> getAllService() {
//        List<Services> services = serviceRepo.findAll();
//        List<Map<String, Object>> result = services.stream()
//                .map(service -> {
//                    Map<String, Object> serviceData = new HashMap<>();
//                    serviceData.put("id", service.getId());
//                    serviceData.put("rule", service.getRule());
//                    serviceData.put("creationDate", service.getCreationDate());
//                    serviceData.put("status", service.getStatus());
//                    return serviceData;
//                })
//                .toList();
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ResponseObject.builder()
//                        .message("Get all services successfully")
//                        .data(result)
//                        .build());
//    }
//
//    @Override
//    @Transactional
//    public ResponseEntity<ResponseObject> createService(CreateServiceRequest request) {
//        Services service = Services.builder()
//                .rule(request.getRule())
//                .creationDate(LocalDate.now())
//                .status(Status.SERVICE_ACTIVE)
//                .build();
//
//        serviceRepo.save(service);
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(ResponseObject.builder()
//                        .message("Create service successfully")
//                        .build());
//    }
//
//    @Override
//    @Transactional
//    public ResponseEntity<ResponseObject> updateService(UpdateServiceRequest request) {
//        Services service = serviceRepo.findById(request.getId()).orElse(null);
//        if (service == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(ResponseObject.builder()
//                            .message("Service not found")
//                            .data(null)
//                            .build());
//        }
//        service.setRule(request.getRule());
//        service.setStatus(Status.valueOf(request.getStatus()));
//        serviceRepo.save(service);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ResponseObject.builder()
//                        .message("Update service successfully")
//                        .build());
//    }

    //---------------------------------------------Package--------------------------------------------
    @Override
    public ResponseEntity<ResponseObject> getAllPackages(int designerId) {

        List<Package> packages = packageRepo.findAllByPartner_Id(designerId);
        if (packages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseObject.builder()
                            .message("No packages found for this designer")
                            .data(new ArrayList<>())
                            .build()
            );
        }
        System.out.println("Packages found: " + packages.size());
        List<Map<String, Object>> data = packages.stream()
                .map(this::buildPackage)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseObject.builder()
                        .message("Get all packages successfully")
                        .data(data)
                        .build());
    }


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
        data.put("id", pkg.getId());
        data.put("pkgName", pkg.getName());
        data.put("headerContent", pkg.getHeaderContent());
        data.put("deliveryDuration", pkg.getDeliveryDuration());
        data.put("revisionTime", pkg.getRevisionTime());
        data.put("fee", pkg.getFee());
        data.put("status", pkg.getStatus());
        data.put("designerInfo", buildDesigner(pkg.getPartner()));
//        data.put("services", buildServices(pkg.getPackageServices()));
        return data;
    }

    private Map<String, Object> buildDesigner(Partner partner) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", partner.getId());
        data.put("name", partner.getCustomer().getName());
        data.put("phone", partner.getCustomer().getPhone());
        data.put("avatar", partner.getCustomer().getAvatar());
        data.put("outsidePreview", partner.getOutsidePreview());
        data.put("insidePreview", partner.getInsidePreview());
        data.put("thumbnail", buildThumbnailResponse(partner.getThumbnailImages()));
        data.put("rating", partner.getRating());
        return data;
    }

//    private List<Map<String, Object>> buildServices(List<PackageService> pkgServices) {
//        return pkgServices.stream()
//                .map(packageService -> {
//                    Services service = packageService.getService();
//                    Map<String, Object> serviceData = new HashMap<>();
//                    serviceData.put("id", service.getId());
//                    serviceData.put("rule", service.getRule());
//                    serviceData.put("creationDate", service.getCreationDate());
//                    serviceData.put("status", service.getStatus());
//                    return serviceData;
//                })
//                .toList();
//    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObject> createPackage(CreatePackageRequest request) {
        Partner partner = partnerRepo.findById(request.getDesignerId())
                .orElseThrow(() -> new RuntimeException("Designer not found"));

        Package pkg = Package.builder()
                .name(request.getName())
                .headerContent(request.getHeaderContent())
                .deliveryDuration(request.getDeliveryDuration())
                .revisionTime(request.getRevisionTime())
                .fee(request.getFee())
                .status(Status.PACKAGE_ACTIVE)
                .partner(partner)
                .build();

        Package savedPkg = packageRepo.save(pkg);

//        List<PackageService> packageServices = request.getServiceIds().stream()
//                .map(serviceId -> {
//                    Services service = serviceRepo.findById(serviceId).orElse(null);
//                    return PackageService.builder()
//                            .id(new PackageService.ID(savedPkg.getId(), serviceId))
//                            .pkg(savedPkg)
//                            .service(service)
//                            .build();
//                })
//                .toList();
//
//        packageServiceRepo.saveAll(packageServices);
//        pkg.setPackageServices(packageServices);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseObject.builder()
                        .message("Create package successfully")
                        .build());
    }

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

        if (request.getDesignerId() != null && !request.getDesignerId().equals(pkg.getPartner().getId())) {
            Partner partner = partnerRepo.findById(request.getDesignerId()).orElse(null);
            pkg.setPartner(partner);
        }

//        packageServiceRepo.deleteAll(pkg.getPackageServices());

//        List<PackageService> newPackageServices = request.getServiceIds().stream()
//                .map(serviceId -> {
//                    Services service = serviceRepo.findById(serviceId).orElse(null);
//                    return PackageService.builder()
//                            .id(new PackageService.ID(pkg.getId(), serviceId))
//                            .pkg(pkg)
//                            .service(service)
//                            .build();
//                })
//                .toList();
//
//        packageServiceRepo.saveAll(newPackageServices);
//        pkg.setPackageServices(new ArrayList<>(newPackageServices));
        packageRepo.save(pkg);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseObject.builder()
                        .message("Update package successfully")
                        .build());
    }

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
