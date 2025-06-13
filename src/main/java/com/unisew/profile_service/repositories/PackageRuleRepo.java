package com.unisew.profile_service.repositories;

import com.unisew.profile_service.models.PackageRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRuleRepo extends JpaRepository<PackageRule, Integer> {
}
