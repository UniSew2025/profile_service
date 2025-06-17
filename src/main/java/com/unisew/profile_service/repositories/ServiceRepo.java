package com.unisew.profile_service.repositories;

import com.unisew.profile_service.models.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepo extends JpaRepository<Services, Integer> {

    List<Services> findAllByPackageServices_Pkg_Id(int packageId);

}
