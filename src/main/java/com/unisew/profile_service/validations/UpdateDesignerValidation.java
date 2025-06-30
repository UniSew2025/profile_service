package com.unisew.profile_service.validations;

import com.unisew.profile_service.requests.UpdateDesignerProfileRequest;

public class UpdateDesignerValidation {

    public static String validate(UpdateDesignerProfileRequest request) {
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
        if (request.getBio() == null || request.getBio().isEmpty()) {
            errors.append("Bio cannot be empty. ");
        }
        if (request.getShortProfile() == null || request.getShortProfile().isEmpty()) {
            errors.append("Short profile cannot be empty. ");
        }

        return errors.toString();
    }

}
