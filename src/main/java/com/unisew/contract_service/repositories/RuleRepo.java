package com.unisew.contract_service.repositories;

import com.unisew.contract_service.models.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepo extends JpaRepository<Rule, Integer> {
}
