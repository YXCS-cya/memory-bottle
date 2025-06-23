package com.memorybottle.memory_app.repository;

import com.memorybottle.memory_app.domain.TimelineEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimelineRepository extends JpaRepository<TimelineEvent, Integer> {
    List<TimelineEvent> findAllByOrderByEventDateAsc();

}
