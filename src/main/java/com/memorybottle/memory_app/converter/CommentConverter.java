package com.memorybottle.memory_app.converter;

import com.memorybottle.memory_app.domain.Comment;
import com.memorybottle.memory_app.dto.CommentDTO;
import com.memorybottle.memory_app.vo.CommentVO;

public class CommentConverter {

    public static Comment toEntity(CommentDTO dto) {
        Comment comment = new Comment();
        //comment.setUserName(dto.getUserName());
        comment.setContent(dto.getContent());
        return comment;
    }

    public static CommentVO toVO(Comment comment) {
        CommentVO vo = new CommentVO();
        vo.setUserName(comment.getUserName());
        vo.setContent(comment.getContent());
        vo.setCreatedTime(comment.getCreatedTime());
        return vo;
    }
}
