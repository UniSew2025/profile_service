package com.unisew.profile_service.validations;

import com.unisew.profile_service.requests.UpdateGarmentProfileRequest;
import com.unisew.profile_service.requests.UpdateSchoolProfileRequest;

public class UpdateGarmentValidation {

    public static String validate(UpdateGarmentProfileRequest request) {
        StringBuilder errors = new StringBuilder();

        if (request.getAccountId() <= 0) {
            errors.append("Invalid account ID. ");
        }
        if (request.getName() == null || request.getName().isEmpty()) {
            errors.append("Name cannot be empty. ");
        }

        return errors.toString();
    }
}
