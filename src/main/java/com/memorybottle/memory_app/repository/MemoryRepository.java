package com.memorybottle.memory_app.repository;

import com.memorybottle.memory_app.domain.Memory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface MemoryRepository extends JpaRepository<Memory, Integer> {

    @Query("SELECT DISTINCT m FROM Memory m " +
            "LEFT JOIN TimelineEvent t ON t.memory = m " +
            "WHERE (:keyword IS NULL OR m.title LIKE %:keyword%) " +
            "AND (:startDate IS NULL OR t.eventDate >= :startDate) " +
            "AND (:endDate IS NULL OR t.eventDate <= :endDate)")
    Page<Memory> searchMemories(@Param("keyword") String keyword,
                                @Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate,
                                Pageable pageable);

}