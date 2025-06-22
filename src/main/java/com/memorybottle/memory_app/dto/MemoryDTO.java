package com.memorybottle.memory_app.dto;


import com.memorybottle.memory_app.domain.MediaType;
import lombok.Data;

import java.util.List;

@Data
public class MemoryDTO {
    private String title;
    private String description;
    private Integer userId;

    private List<MediaItemDTO> mediaList;

    @Data
    public static class MediaItemDTO {
        private String fileUrl;
        private MediaType mediaType;
    }
}

