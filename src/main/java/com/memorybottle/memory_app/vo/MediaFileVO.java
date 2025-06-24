package com.memorybottle.memory_app.vo;

import lombok.Data;

@Data
public class MediaFileVO {
    private String fileUrl;
    private String mediaType; // IMAGE 或 VIDEO（字符串形式）
}
