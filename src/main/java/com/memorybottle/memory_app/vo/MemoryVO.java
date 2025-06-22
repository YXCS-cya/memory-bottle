package com.memorybottle.memory_app.vo;

import com.memorybottle.memory_app.domain.MediaType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MemoryVO {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createdTime;

    private List<MediaItem> mediaList;

    @Data
    public static class MediaItem {
        private String fileUrl;
        private MediaType mediaType;
    }
}
