package com.memorybottle.memory_app.vo;

import lombok.Data;

@Data
public class TimelineVO {
    private Integer memoryId;
    private String title;
    private String eventDate; // 日期用字符串去显示
    private String coverUrl; //加个封面，方便前端查看
}