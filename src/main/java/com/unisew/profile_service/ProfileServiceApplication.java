package com.unisew.profile_service;

import com.unisew.profile_service.enums.Status;
import com.unisew.profile_service.models.Designer;
import com.unisew.profile_service.models.Partner;
import com.unisew.profile_service.models.Profile;
import com.unisew.profile_service.models.Services;
import com.unisew.profile_service.models.Package;
import com.unisew.profile_service.models.PackageService;
import com.unisew.profile_service.repositories.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class ProfileServiceApplication {

    private final ProfileRepo profileRepo;
    private final PartnerRepo partnerRepo;
    private final DesignerRepo designerRepo;
    private final PackageRepo packageRepo;
    private final ServiceRepo serviceRepo;
    private final PackageServiceRepo packageServiceRepo;

    public static void main(String[] args) {
        SpringApplication.run(ProfileServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // 1. Tạo profile, designer
                Profile profile1 = Profile.builder()
                        .accountId(1)
                        .name("Alice Nguyen")
                        .phone("0909000001")
                        .avatar("https://picsum.photos/seed/1/200")
                        .build();
                profile1 = profileRepo.save(profile1);

                Designer designer1 = Designer.builder()
                        .shortPreview("Creative uniform designer")
                        .bio("Experienced in logo & uniform for schools.")
                        .profile(profile1)
                        .build();
                designerRepo.save(designer1);

            // 2. Tạo services
            Services service1 = Services.builder()
                    .rule("Logo vector design")
                    .creationDate(LocalDate.now())
                    .status(Status.ACCOUNT_ACTIVE)
                    .build();
            Services service2 = Services.builder()
                    .rule("Uniform color consultation")
                    .creationDate(LocalDate.now())
                    .status(Status.ACCOUNT_ACTIVE)
                    .build();
            serviceRepo.save(service1);
            serviceRepo.save(service2);

            // 3. Tạo package
            Package pkg1 = Package.builder()
                    .name("Basic Design")
                    .headerContent("Design 1 logo, 1 uniform mockup, 2 revisions")
                    .deliveryDuration(5)
                    .revisionTime(2)
                    .fee(1000000L)
                    .status(Status.ACCOUNT_ACTIVE)
                    .designer(designer1)
                    .build();
            Package pkg2 = Package.builder()
                    .name("Premium Design")
                    .headerContent("Design 2 logos, 3 uniform mockups, 5 revisions, color consultation")
                    .deliveryDuration(7)
                    .revisionTime(5)
                    .fee(2500000L)
                    .status(Status.ACCOUNT_ACTIVE)
                    .designer(designer1)
                    .build();
            packageRepo.save(pkg1);
            packageRepo.save(pkg2);

            // 4. PackageService
            PackageService.ID pkgServId1 = PackageService.ID.builder()
                    .packageId(pkg1.getId())
                    .serviceId(service1.getId())
                    .build();
            PackageService packageService1 = PackageService.builder()
                    .id(pkgServId1)
                    .pkg(pkg1)
                    .service(service1)
                    .build();
            packageServiceRepo.save(packageService1);

            PackageService.ID pkgServId2 = PackageService.ID.builder()
                    .packageId(pkg2.getId())
                    .serviceId(service2.getId())
                    .build();
            PackageService packageService2 = PackageService.builder()
                    .id(pkgServId2)
                    .pkg(pkg2)
                    .service(service2)
                    .build();
            packageServiceRepo.save(packageService2);
            }
        };
    }

}
