package com.unisew.profile_service.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`profile`")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile {

    @Id
    @Column(name = "`account_id`")
    Integer id;

    String name;

    String phone;

    String avatar;

    @Column(name = "`account_id`")
    int accountId;

    @OneToOne(mappedBy = "profile", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Partner partner;

    @OneToOne(mappedBy = "profile", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Designer designer;
}
