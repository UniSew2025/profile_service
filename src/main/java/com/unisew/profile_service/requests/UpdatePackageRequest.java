package com.unisew.profile_service.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePackageRequest {
    Integer id;
    String name;
    String headerContent;
    int deliveryDuration;
    int revisionTime;
    long fee;
    Integer designerId;
    List<Integer> serviceIds;
}
