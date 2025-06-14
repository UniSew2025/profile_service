package com.unisew.profile_service.repositories;

import com.unisew.profile_service.models.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Services, Integer> {
}
