package com.unisew.profile_service.repositories;

import com.unisew.profile_service.models.Designer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignerRepo extends JpaRepository<Designer, Integer> {
}
