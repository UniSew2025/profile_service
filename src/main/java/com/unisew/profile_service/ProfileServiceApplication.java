package com.unisew.profile_service;

import com.unisew.profile_service.enums.Status;
import com.unisew.profile_service.models.Customer;
import com.unisew.profile_service.models.Partner;
import com.unisew.profile_service.models.Package;
import com.unisew.profile_service.models.ThumbnailImage;
import com.unisew.profile_service.repositories.PartnerRepo;
import com.unisew.profile_service.repositories.PackageRepo;
import com.unisew.profile_service.repositories.CustomerRepo;
import com.unisew.profile_service.repositories.ThumbnailImageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class ProfileServiceApplication {

    private final CustomerRepo customerRepo;
    private final PartnerRepo partnerRepo;
    private final PackageRepo packageRepo;
//    private final ServiceRepo serviceRepo;
//    private final PackageServiceRepo packageServiceRepo;
    private final ThumbnailImageRepo thumbnailImageRepo;

    public static void main(String[] args) {
        SpringApplication.run(ProfileServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // 1. Tạo profile
                Customer customer1 = customerRepo.save(
                        Customer.builder()
                                .accountId(1)
                                .name("Alice Nguyen")
                                .phone("0909000001")
                                .avatar("https://picsum.photos/seed/1/200")
                                .build()
                );

                Customer customer2 = customerRepo.save(
                        Customer.builder()
                                .accountId(2)
                                .name("Vikor")
                                .phone("0911094322")
                                .avatar("https://employer.jobsgo.vn/uploads/media/img/201803/pictures_library_hue-dinh_8457_180316171037_1078.jpg")
                                .build()
                );

                Customer customer3 = customerRepo.save(
                        Customer.builder()
                                .accountId(3)
                                .name("Ken")
                                .phone("0911094322")
                                .avatar("https://employer.jobsgo.vn/uploads/media/img/201803/pictures_library_hue-dinh_8457_180316171037_1078.jpg")
                                .build()
                );

                Customer customer4 = customerRepo.save(
                        Customer.builder()
                                .accountId(4)
                                .name("Garment TQH")
                                .phone("0911094322")
                                .avatar("https://employer.jobsgo.vn/uploads/media/img/201803/pictures_library_hue-dinh_8457_180316171037_1078.jpg")
                                .build()
                );

                //partner
                Partner partner1 = partnerRepo.save(
                        Partner.builder()
                                .startTime(LocalTime.of(6, 0))
                                .endTime(LocalTime.of(18, 0))
                                .rating(4)
                                .customer(customer3)
                                .build()
                );

                //thumbnail images
                List<String> thumbnailUrls1 = List.of(
                        "https://www.shutterstock.com/image-vector/technical-flat-sketch-girls-school-260nw-2287745045.jpg",
                        "https://thumbs.dreamstime.com/b/baby-girl-s-school-uniform-design-template-vector-design-baby-girl-s-school-uniform-design-button-down-short-sleeves-template-382461332.jpg",
                        "https://static.vecteezy.com/system/resources/previews/033/952/505/non_2x/work-or-school-uniform-front-and-back-view-illustration-vector.jpg",
                        "https://media.istockphoto.com/id/1321431524/vector/vector-sketch-set-of-school-uniform-clothes.jpg?s=1024x1024&w=is&k=20&c=MWasRYc5U_v3Co7Ggp6CNC1DJLoVZbA4KZRw5n1OMWA="
                );
                List<ThumbnailImage> thumbnails = new ArrayList<>();
                for (String url : thumbnailUrls1) {
                    ThumbnailImage thumb = ThumbnailImage.builder()
                            .imageUrl(url)
                            .name("Thumbnail for " + partner1.getCustomer().getName())
                            .partner(partner1)
                            .build();
                    thumbnails.add(thumb);
                }
                thumbnailImageRepo.saveAll(thumbnails);

                // 2. Tạo services
//                Services service1 = serviceRepo.save(
//                        Services.builder()
//                                .rule("Logo vector design")
//                                .creationDate(LocalDate.now())
//                                .status(Status.ACCOUNT_ACTIVE)
//                                .build()
//                );
//                Services service2 = serviceRepo.save(
//                        Services.builder()
//                                .rule("Uniform color consultation")
//                                .creationDate(LocalDate.now())
//                                .status(Status.ACCOUNT_ACTIVE)
//                                .build()
//                );

                // 3. Tạo package
                Package pkg1 = packageRepo.save(
                        Package.builder()
                                .name("Basic Design")
                                .headerContent("Design 1 logo, 1 uniform mockup, 2 revisions")
                                .deliveryDuration(5)
                                .revisionTime(2)
                                .fee(1000000)
                                .status(Status.ACCOUNT_ACTIVE)
                                .partner(partner1)
                                .build()
                );
                Package pkg2 = packageRepo.save(
                        Package.builder()
                                .name("Standard Design")
                                .headerContent("Design 2 logos, 3 uniform mockups, 5 revisions, color consultation")
                                .deliveryDuration(7)
                                .revisionTime(3)
                                .fee(1500000)
                                .status(Status.ACCOUNT_ACTIVE)
                                .partner(partner1)
                                .build()
                );

                Package pkg3 = packageRepo.save(
                        Package.builder()
                                .name("Premium Design")
                                .headerContent("Design 2 logos, 3 uniform mockups, 5 revisions, color consultation")
                                .deliveryDuration(7)
                                .revisionTime(5)
                                .fee(2500000)
                                .status(Status.ACCOUNT_ACTIVE)
                                .partner(partner1)
                                .build()
                );

                // 4. PackageService
//                PackageService.ID pkgServId1 = PackageService.ID.builder()
//                        .packageId(pkg1.getId())
//                        .serviceId(service1.getId())
//                        .build();
//
//                PackageService packageService1 = packageServiceRepo.save(
//                        PackageService.builder()
//                                .id(pkgServId1)
//                                .pkg(pkg1)
//                                .service(service1)
//                                .build()
//                );
//
//                PackageService.ID pkgServId2 = PackageService.ID.builder()
//                        .packageId(pkg2.getId())
//                        .serviceId(service2.getId())
//                        .build();
//
//                PackageService packageService2 = packageServiceRepo.save(
//                        PackageService.builder()
//                                .id(pkgServId2)
//                                .pkg(pkg2)
//                                .service(service2)
//                                .build()
//                );
//
//                PackageService.ID pkgServId3 = PackageService.ID.builder()
//                        .packageId(pkg2.getId())
//                        .serviceId(service2.getId())
//                        .build();
//
//                PackageService packageService3 = packageServiceRepo.save(
//                        PackageService.builder()
//                                .id(pkgServId3)
//                                .pkg(pkg2)
//                                .service(service2)
//                                .build()
//                );
            }
        };
    }

}
