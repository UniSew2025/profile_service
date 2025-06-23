package com.unisew.profile_service.repositories;

import com.unisew.profile_service.models.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepo extends JpaRepository<Partner, Integer> {
}
