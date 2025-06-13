package com.unisew.profile_service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RuleSubject {

    SCHOOL("school"),
    GARMENT("garment");

    private final String subject;
}
