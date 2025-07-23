package com.unisew.profile_service.models;

import com.unisew.profile_service.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`package`")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    String note;

    @Column(name = "`header_content`")
    String headerContent;

    @Column(name = "`delivery_duration`")
    int deliveryDuration;

    @Column(name = "`revision_time`")
    int revisionTime;

    long fee;

    @Enumerated(EnumType.STRING)
    Status status;

    @ManyToOne
    @JoinColumn(name = "`designer_id`")
    Partner partner;

//    @OneToMany(mappedBy = "pkg")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    List<PackageService> packageServices;
}
