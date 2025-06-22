package com.memorybottle.memory_app.repository;

import com.memorybottle.memory_app.domain.TimelineEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineRepository extends JpaRepository<TimelineEvent, Integer> {
}
