package com.unisew.profile_service.repositories;

import com.unisew.profile_service.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByAccountId(int accountId);

    Optional<Customer> findByPartner_Id(int designerId);
}
