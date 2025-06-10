package com.unisew.contract_service.repositories;

import com.unisew.contract_service.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepo extends JpaRepository<Contract, Integer> {
}
