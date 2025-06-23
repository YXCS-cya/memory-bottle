package com.memorybottle.memory_app.repository;

import com.memorybottle.memory_app.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByMemoryIdOrderByCreatedTimeDesc(Integer memoryId);
}
