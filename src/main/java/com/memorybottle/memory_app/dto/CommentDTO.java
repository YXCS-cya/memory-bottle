package com.memorybottle.memory_app.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer memoryId;
    private String userName;
    private String content;
}
