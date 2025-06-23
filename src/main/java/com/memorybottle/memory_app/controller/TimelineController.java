package com.memorybottle.memory_app.controller;

import com.memorybottle.memory_app.dto.TimelineDTO;
import com.memorybottle.memory_app.service.TimelineService;
import com.memorybottle.memory_app.vo.TimelineVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeline")
@RequiredArgsConstructor
@CrossOrigin
public class TimelineController {

    private final TimelineService timelineService;

    @PostMapping("/add")
    public ResponseEntity<?> addEvent(@RequestBody TimelineDTO dto) {
        try {
            timelineService.addTimeline(dto);
            return ResponseEntity.ok("Timeline event added.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Add failed: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TimelineVO>> getAllEvents() {
        return ResponseEntity.ok(timelineService.getAllEvents());
    }
}
