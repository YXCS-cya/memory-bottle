package com.memorybottle.memory_app.service;

import com.memorybottle.memory_app.converter.CommentConverter;
import com.memorybottle.memory_app.domain.Comment;
import com.memorybottle.memory_app.domain.Memory;
import com.memorybottle.memory_app.dto.CommentDTO;
import com.memorybottle.memory_app.repository.CommentRepository;
import com.memorybottle.memory_app.repository.MemoryRepository;
import com.memorybottle.memory_app.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemoryRepository memoryRepository;

    public void addComment(CommentDTO dto) {
        Memory memory = memoryRepository.findById(dto.getMemoryId())
                .orElseThrow(() -> new RuntimeException("Memory not found"));

        Comment comment = CommentConverter.toEntity(dto);
        comment.setMemory(memory);  // 设置外键关联
        commentRepository.save(comment);
    }

    public List<CommentVO> getCommentsByMemoryId(Integer memoryId) {
        List<Comment> comments = commentRepository.findByMemoryIdOrderByCreatedTimeDesc(memoryId);
        return comments.stream()
                .map(CommentConverter::toVO)
                .collect(Collectors.toList());
    }
}
