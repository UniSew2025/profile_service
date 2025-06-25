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
import org.springframework.data.repository.query.parser.Part;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
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
                // 1. Tạo profile
                Profile profile1 = profileRepo.save(
                        Profile.builder()
                        .accountId(1)
                        .name("Alice Nguyen")
                        .phone("0909000001")
                        .avatar("https://picsum.photos/seed/1/200")
                        .build()
                );

                Profile profile2 = profileRepo.save(
                        Profile.builder()
                                .accountId(2)
                                .name("Vikor")
                                .phone("0911094322")
                                .avatar("https://employer.jobsgo.vn/uploads/media/img/201803/pictures_library_hue-dinh_8457_180316171037_1078.jpg")
                                .build()
                );

                Profile profile3 = profileRepo.save(
                        Profile.builder()
                                .accountId(3)
                                .name("Ken")
                                .phone("0911094322")
                                .avatar("https://employer.jobsgo.vn/uploads/media/img/201803/pictures_library_hue-dinh_8457_180316171037_1078.jpg")
                                .build()
                );

                Profile profile4 = profileRepo.save(
                        Profile.builder()
                                .accountId(4)
                                .name("Garment TQH")
                                .phone("0911094322")
                                .avatar("https://employer.jobsgo.vn/uploads/media/img/201803/pictures_library_hue-dinh_8457_180316171037_1078.jpg")
                                .build()
                );

                //designer
                Designer designer1 = designerRepo.save(
                        Designer.builder()
                        .shortPreview("Creative uniform designer")
                        .bio("Experienced in logo & uniform for schools.")
                                .startTime(LocalTime.of(6, 0))
                                .endTime(LocalTime.of(18, 0))
                        .profile(profile3)
                        .build()
                );

                //partner
                Partner partner1 = partnerRepo.save(
                        Partner.builder()
                                .street("20 Vinh Vien")
                                .ward("Ward 9")
                                .district("District 10")
                                .province("Ho Chi Minh City")
                                .profile(profile4)
                                .build()
                );

                // 2. Tạo services
                Services service1 = serviceRepo.save(
                        Services.builder()
                                .rule("Logo vector design")
                                .creationDate(LocalDate.now())
                                .status(Status.ACCOUNT_ACTIVE)
                                .build()
                );
                Services service2 = serviceRepo.save(
                        Services.builder()
                                .rule("Uniform color consultation")
                                .creationDate(LocalDate.now())
                                .status(Status.ACCOUNT_ACTIVE)
                                .build()
                );

                // 3. Tạo package
                Package pkg1 = packageRepo.save(
                        Package.builder()
                                .name("Basic Design")
                                .headerContent("Design 1 logo, 1 uniform mockup, 2 revisions")
                                .deliveryDuration(5)
                                .revisionTime(2)
                                .fee(1000000L)
                                .status(Status.ACCOUNT_ACTIVE)
                                .designer(designer1)
                                .build()
                );
                Package pkg2 = packageRepo.save(
                        Package.builder()
                                .name("Premium Design")
                                .headerContent("Design 2 logos, 3 uniform mockups, 5 revisions, color consultation")
                                .deliveryDuration(7)
                                .revisionTime(5)
                                .fee(2500000L)
                                .status(Status.ACCOUNT_ACTIVE)
                                .designer(designer1)
                                .build()
                );

                // 4. PackageService
                PackageService.ID pkgServId1 = PackageService.ID.builder()
                        .packageId(pkg1.getId())
                        .serviceId(service1.getId())
                        .build();

                PackageService packageService1 = packageServiceRepo.save(
                        PackageService.builder()
                                .id(pkgServId1)
                                .pkg(pkg1)
                                .service(service1)
                                .build()
                );

                PackageService.ID pkgServId2 = PackageService.ID.builder()
                        .packageId(pkg2.getId())
                        .serviceId(service2.getId())
                        .build();

                PackageService packageService2 = packageServiceRepo.save(
                        PackageService.builder()
                                .id(pkgServId2)
                                .pkg(pkg2)
                                .service(service2)
                                .build()
                );
            }
        };
    }

}
