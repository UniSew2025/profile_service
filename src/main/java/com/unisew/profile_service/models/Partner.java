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

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`designer`")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "`outside_preview`")
    String outsidePreview;

    @Column(name = "`inside_preview`")
    String insidePreview;

    @Column(name = "`start_time`")
    LocalTime startTime;

    @Column(name = "`end_time`")
    LocalTime endTime;

    int rating;

    @Column(name = "`is_busy`")
    boolean busy;

    @OneToOne
    @JoinColumn(name = "`customer_id`")
    Customer customer;

    @OneToMany(mappedBy = "partner")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Package> packages;

    @OneToMany(mappedBy = "partner")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<ThumbnailImage> thumbnailImages;
}
