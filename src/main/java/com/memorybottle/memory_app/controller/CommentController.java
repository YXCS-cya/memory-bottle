package com.memorybottle.memory_app.controller;

import com.memorybottle.memory_app.dto.CommentDTO;
import com.memorybottle.memory_app.service.CommentService;
import com.memorybottle.memory_app.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@CrossOrigin
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentDTO dto) {
        try {
            commentService.addComment(dto);
            return ResponseEntity.ok("Comment added.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Add failed: " + e.getMessage());
        }
    }

    @GetMapping("/{memoryId}")
    public ResponseEntity<List<CommentVO>> getComments(@PathVariable Integer memoryId) {
        return ResponseEntity.ok(commentService.getCommentsByMemoryId(memoryId));
    }
}
