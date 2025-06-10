package com.unisew.contract_service.repositories;

import com.unisew.contract_service.models.ContractRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRuleRepo extends JpaRepository<ContractRule, ContractRule.ID> {
}
