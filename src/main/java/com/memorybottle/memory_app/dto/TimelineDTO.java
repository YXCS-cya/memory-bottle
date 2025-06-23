package com.memorybottle.memory_app.dto;

import lombok.Data;

@Data
public class TimelineDTO {
    private Integer memoryId;
    private String eventDate; // "年-月-日" 格式
}
