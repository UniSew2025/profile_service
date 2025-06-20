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

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`designer`")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Designer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "`thumbnail_img`")
    String thumbnail_img;

    @Column(name = "`short_preview`")
    String shortPreview;

    String bio;

    @OneToOne
    @JoinColumn(name = "`profile_id`")
    Profile profile;

    @OneToMany(mappedBy = "designer")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Package> packages;
}
