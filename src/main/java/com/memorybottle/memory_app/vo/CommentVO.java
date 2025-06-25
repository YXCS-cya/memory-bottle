package com.memorybottle.memory_app.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentVO {
    private String userName;
    private String content;
    private LocalDateTime createdTime;
    private Integer commentId;
}
