package com.unisew.profile_service.services.implementors;

import com.unisew.profile_service.models.*;
import com.unisew.profile_service.models.Package;
import com.unisew.profile_service.repositories.*;
import com.unisew.profile_service.requests.CreateProfileRequest;
import com.unisew.profile_service.responses.ResponseObject;
import com.unisew.profile_service.services.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileServiceImpl implements ProfileService {

    DesignerRepo designerRepo;

    ProfileRepo profileRepo;

    PackageRepo packageRepo;

    ServiceRepo serviceRepo;

    PackageServiceRepo packageServiceRepo;

    PartnerRepo partnerRepo;

    // --------------------------------------------Designer Profile--------------------------------------------
    @Override
    public ResponseEntity<ResponseObject> getAllDesignerProfile() {
        List<Designer> designers = designerRepo.findAll();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status("200")
                        .message("Get designer profiles successfully")
                        .data(buildDesigners(designers))
                        .build()
        );
    }


    private List<Map<String, Object>> buildDesigners(List<Designer> designers) {
        return designers.stream()
                .map(designer -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", designer.getId());
                    map.put("short_review", designer.getShortPreview());
                    map.put("bio", designer.getBio());
                    map.put("profile", buildProfile(designer.getProfile()));
                    map.put("package", buildPackage(designer.getPackages()));
                    return map;
                })
                .toList();
    }

    private Map<String, Object> buildProfile(Profile profile) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", profile.getId());
        map.put("name", profile.getName());
        map.put("phone", profile.getPhone());
        map.put("avatar", profile.getAvatar());
        return map;
    }

    private List<Map<String, Object>> buildPackage(List<Package> pkgs) {
        return pkgs.stream()
                .map(pkg -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", pkg.getId());
                    map.put("name", pkg.getName());
                    map.put("header_content", pkg.getHeaderContent());
                    map.put("delivery_duration", pkg.getDeliveryDuration());
                    map.put("revision_time", pkg.getRevisionTime());
                    map.put("fee", pkg.getFee());
                    map.put("status", pkg.getStatus());
                    map.put("services", buildService(pkg.getId()));
                    return map;
                })
                .toList();
    }

    private List<Map<String, Object>> buildService(int pkgId) {
        List<Services> services = serviceRepo.findAllByPackageServices_Pkg_Id(pkgId);
        return services.stream()
                .map(sv -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", sv.getId());
                    map.put("rule", sv.getRule());
                    map.put("creation_date", sv.getCreationDate());
                    map.put("status", sv.getStatus());
                    return map;
                })
                .toList();
    }

    //--------------------------------------------Garment Profile--------------------------------------------

    @Override
    public ResponseEntity<ResponseObject> getAllGarmentProfile() {
        List<Partner> garments = partnerRepo.findAll();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status("200")
                        .message("Get garment profiles successfully")
                        .data(buildGarments(garments))
                        .build()
        );
    }

    private List<Map<String, Object>> buildGarments(List<Partner> partners) {
        return partners.stream()
                .map(partner -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", partner.getId());
                    map.put("street", partner.getStreet());
                    map.put("ward", partner.getWard());
                    map.put("district", partner.getDistrict());
                    map.put("province", partner.getProvince());
                    map.put("isBusy", partner.isBusy());
                    map.put("profile", buildProfile(partner.getProfile()));
                    return map;
                })
                .toList();
    }

    // --------------------------------------------User Profile--------------------------------------------
    @Override
    @Transactional
    public ResponseEntity<ResponseObject> createProfile(CreateProfileRequest request) {
        Profile profile = profileRepo.save(
                Profile.builder()
                        .accountId(request.getAccountId())
                        .avatar(request.getAvatar())
                        .name(request.getName())
                        .phone("N/A")
                        .build()
        );

        Map<String, Object> profileData = buildProfileResponse(profile);
        if(request.getRole().equalsIgnoreCase("designer")){
            profileData.put("designer", createDesignerByProfile(profile));
        }else {
            profileData.put("partner", createPartnerByProfile(profile));
        }

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status("201")
                        .message("Profile created successfully")
                        .data(profileData)
                        .build()
        );
    }

    private Map<String, Object> createPartnerByProfile(Profile profile) {
        Partner partner = partnerRepo.save(
                Partner.builder()
                        .profile(profile)
                        .district("N/A")
                        .province("N/A")
                        .street("N/A")
                        .ward("N/A")
                        .build()
        );

        return buildPartnerResponse(partner);
    }

    private Map<String, Object> createDesignerByProfile(Profile profile) {
        Designer designer = designerRepo.save(
                Designer.builder()
                        .profile(profile)
                        .bio("N/A")
                        .shortPreview("N/A")
                        .build()
        );

        return buildDesignerResponse(designer);
    }

    @Override
    public ResponseEntity<ResponseObject> getProfileInfo(int accountId) {
        Profile profile = profileRepo.findByAccountId(accountId).orElse(null);
        if (profile == null) {
            return ResponseEntity.ok().body(null);
        }

        Map<String, Object> profileData = buildProfileResponse(profile);
        if(profile.getDesigner() != null){
            profileData.put("designer", buildDesignerResponse(profile.getDesigner()));
        }else {
            profileData.put("partner", buildPartnerResponse(profile.getPartner()));
        }

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status("200")
                        .message("")
                        .data(profileData)
                        .build()
        );
    }

    private Map<String, Object> buildProfileResponse(Profile profile) {
        Map<String, Object> profileData = new HashMap<>();
        profileData.put("id", profile.getId());
        profileData.put("name", profile.getName());
        profileData.put("avatar", profile.getAvatar());
        profileData.put("phone", profile.getPhone());

        return profileData;
    }

    private Map<String, Object> buildDesignerResponse(Designer designer) {
        Map<String, Object> designerData = new HashMap<>();
        designerData.put("id", designer.getId());
        designerData.put("bio", designer.getBio());
        designerData.put("shortPreview", designer.getShortPreview());
        return designerData;
    }

    private Map<String, Object> buildPartnerResponse(Partner partner) {
        Map<String, Object> partnerData = new HashMap<>();
        partnerData.put("id", partner.getId());
        partnerData.put("street", partner.getStreet());
        partnerData.put("ward", partner.getWard());
        partnerData.put("district", partner.getDistrict());
        partnerData.put("province", partner.getProvince());
        return partnerData;
    }

    // --------------------------------------------Profile--------------------------------------------
}
