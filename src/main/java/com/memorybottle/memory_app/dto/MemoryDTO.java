package com.memorybottle.memory_app.dto;


import com.memorybottle.memory_app.domain.MediaType;
import lombok.Data;

import java.util.List;

@Data
public class MemoryDTO {
    private String title;
    private String description;
    private Integer userId;
    private String eventDate; // 用户需要显示指定事件发生时间
    // 格式必须是（yyyy-MM-dd）方便Controller层准换

    private List<MediaItemDTO> mediaList;

    @Data
    public static class MediaItemDTO {
        private String fileUrl;
        private MediaType mediaType;
    }
}

