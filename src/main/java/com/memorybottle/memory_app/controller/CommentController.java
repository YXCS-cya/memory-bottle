package com.memorybottle.memory_app.controller;

import com.memorybottle.memory_app.common.Result;
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
    public ResponseEntity<?> addComment(@RequestBody CommentDTO dto,
                                        @RequestHeader("X-User-Id") Integer userId) {
        try {
            commentService.addComment(dto, userId);
            return ResponseEntity.ok(Result.success("留言添加成功"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Result.failure("添加失败：" + e.getMessage()));
        }
    }

    @GetMapping("/{memoryId}")
    public ResponseEntity<List<CommentVO>> getComments(@PathVariable Integer memoryId) {
        return ResponseEntity.ok(commentService.getCommentsByMemoryId(memoryId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId,
                                           @RequestHeader("X-User-Id") Integer userId) {
        try {
            commentService.deleteComment(commentId, userId);
            return ResponseEntity.ok(Result.success("评论删除成功") );
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Result.failure("权限拒绝：" + e.getMessage()));
        }
    }

}
