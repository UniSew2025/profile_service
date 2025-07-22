package com.unisew.profile_service.requests;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateGarmentProfileRequest {

    int accountId;
    String name;
    String phone;
    String address;
    String outsidePreview;
    String insidePreview;
    String bio;
    LocalTime startTime;
    LocalTime endTime;
}
