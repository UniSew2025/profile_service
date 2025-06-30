package com.unisew.profile_service.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`profile`")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "`account_id`")
    int accountId;

    String name;

    String phone;

    String avatar;

    @Column(name = "`start_date`")
    LocalTime startDate;

    @Column(name = "`end_date`")
    LocalTime endDate;

    @Column(name = "`busy`")
    boolean isBusy;

    @OneToOne(mappedBy = "profile", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Partner partner;

    @OneToOne(mappedBy = "profile", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Designer designer;
}
