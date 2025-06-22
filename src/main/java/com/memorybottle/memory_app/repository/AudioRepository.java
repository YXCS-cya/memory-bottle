package com.memorybottle.memory_app.repository;

import com.memorybottle.memory_app.domain.Audio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioRepository extends JpaRepository<Audio, Integer> {}
