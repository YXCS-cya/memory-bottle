package com.memorybottle.memory_app.service;

import com.memorybottle.memory_app.converter.CommentConverter;
import com.memorybottle.memory_app.domain.Comment;
import com.memorybottle.memory_app.domain.Memory;
import com.memorybottle.memory_app.domain.User;
import com.memorybottle.memory_app.dto.CommentDTO;
import com.memorybottle.memory_app.repository.CommentRepository;
import com.memorybottle.memory_app.repository.MemoryRepository;
import com.memorybottle.memory_app.repository.UserRepository;
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
    private final UserRepository userRepository;


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

    public void deleteComment(Integer commentId, Integer userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("评论不存在"));

        checkCommentPermission(comment.getUserName(), userId);

        commentRepository.deleteById(commentId);
    }


    //这里的权限判断逻辑与Memories那里是一样的
    private void checkCommentPermission(String commentUserName, Integer requesterId) {
        if (commentUserName == null || requesterId == null) {
            throw new RuntimeException("无效的权限参数");
        }

        // 查找操作者是否是管理员
        boolean isAdmin = userRepository.findById(requesterId)
                .map(User::getIsAdmin)
                .orElse(false);

        // 查找操作者用户名
        String operatorName = userRepository.findById(requesterId)
                .map(User::getName)
                .orElse(null);

        if (!commentUserName.equals(operatorName) && !isAdmin) {
            throw new RuntimeException("无权限删除该评论！");
        }
    }

}
