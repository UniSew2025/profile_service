package com.unisew.profile_service.repositories;

import com.unisew.profile_service.models.Package;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackageRepo extends JpaRepository<Package, Integer> {
    List<Package> findAllByDesigner_Id(int id);
}
