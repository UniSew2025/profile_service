package com.unisew.contract_service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    ACCOUNT_ACTIVE("active");

    private final String value;
}
