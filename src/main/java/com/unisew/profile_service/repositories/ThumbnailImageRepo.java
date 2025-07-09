package com.unisew.profile_service.repositories;

import com.unisew.profile_service.models.ThumbnailImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbnailImageRepo extends JpaRepository<ThumbnailImage, Integer> {
}
