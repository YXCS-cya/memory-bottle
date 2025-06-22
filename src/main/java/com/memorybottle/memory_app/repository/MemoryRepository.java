package com.memorybottle.memory_app.repository;

import com.memorybottle.memory_app.domain.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryRepository extends JpaRepository<Memory, Integer> {}