package com.memorybottle.memory_app.repository;

import com.memorybottle.memory_app.domain.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaFileRepository extends JpaRepository<MediaFile, Integer> {}
