package com.unisew.profile_service.repositories;

import com.unisew.profile_service.models.PackageService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageServiceRepo extends JpaRepository<PackageService, PackageService.ID> {
}
