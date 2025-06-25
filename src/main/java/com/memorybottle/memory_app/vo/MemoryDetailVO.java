package com.memorybottle.memory_app.vo;

import com.memorybottle.memory_app.domain.MediaType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/*
* 服务于接口getMemoryById
* */
@Data
public class MemoryDetailVO {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createdTime;
    private List<MediaItem> mediaList;
    private LocalDate eventDate;


    @Data
    public static class MediaItem {
        private String fileUrl;
        private MediaType mediaType;
    }
}
