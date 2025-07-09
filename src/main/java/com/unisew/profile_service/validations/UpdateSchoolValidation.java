package com.unisew.profile_service.validations;

import com.unisew.profile_service.requests.UpdateDesignerProfileRequest;
import com.unisew.profile_service.requests.UpdateSchoolProfileRequest;

public class UpdateSchoolValidation {

    public static String validate(UpdateSchoolProfileRequest request) {
        StringBuilder errors = new StringBuilder();

        if (request.getAccountId() <= 0) {
            errors.append("Invalid account ID. ");
        }
        if (request.getName() == null || request.getName().isEmpty()) {
            errors.append("Name cannot be empty. ");
        }
        if (request.getPhone() == null || request.getPhone().isEmpty()) {
            errors.append("Phone cannot be empty. ");
        }

        return errors.toString();
    }
}
