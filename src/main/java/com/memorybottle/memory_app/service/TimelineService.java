package com.memorybottle.memory_app.service;

import com.memorybottle.memory_app.converter.TimelineConverter;
import com.memorybottle.memory_app.domain.Memory;
import com.memorybottle.memory_app.domain.TimelineEvent;
import com.memorybottle.memory_app.dto.TimelineDTO;
import com.memorybottle.memory_app.repository.MemoryRepository;
import com.memorybottle.memory_app.repository.TimelineRepository;
import com.memorybottle.memory_app.vo.TimelineVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimelineService {

    private final TimelineRepository timelineRepository;
    private final MemoryRepository memoryRepository;

    public void addTimeline(TimelineDTO dto) {
        Memory memory = memoryRepository.findById(dto.getMemoryId())
                .orElseThrow(() -> new RuntimeException("Memory not found"));

        TimelineEvent event = new TimelineEvent();
        event.setMemory(memory);
        event.setEventDate(LocalDate.parse(dto.getEventDate())); // yyyy-MM-dd

        timelineRepository.save(event);
    }

    public List<TimelineVO> getAllEvents() {
        return timelineRepository.findAllByOrderByEventDateAsc()
                .stream()
                .map(TimelineConverter::toVO)
                .collect(Collectors.toList());
    }
}
