package com.unisew.profile_service.requests;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDesignerProfileRequest {

    int accountId;

//    String thumbnail;

    String bio;

    String shortProfile;

    String name;

    String phone;

    LocalTime startDate;

    LocalTime endDate;

}
