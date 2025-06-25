package com.unisew.profile_service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    ACCOUNT_ACTIVE("active"),
    SERVICE_ACTIVE("active"),
    PACKAGE_ACTIVE("active"),
    PACKAGE_UN_ACTIVE("un-active");

    private final String value;
}
