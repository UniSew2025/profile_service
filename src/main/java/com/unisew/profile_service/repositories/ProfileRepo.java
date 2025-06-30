package com.unisew.profile_service.repositories;

import com.unisew.profile_service.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepo extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByAccountId(int accountId);

    Optional<Profile> findByDesigner_Id(int designerId);
}
