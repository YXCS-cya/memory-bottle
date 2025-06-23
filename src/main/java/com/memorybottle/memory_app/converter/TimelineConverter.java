package com.memorybottle.memory_app.converter;

import com.memorybottle.memory_app.domain.TimelineEvent;
import com.memorybottle.memory_app.vo.TimelineVO;

import java.time.format.DateTimeFormatter;

public class TimelineConverter {

    public static TimelineVO toVO(TimelineEvent event) {
        TimelineVO vo = new TimelineVO();
        vo.setMemoryId(event.getMemory().getId());
        vo.setTitle(event.getMemory().getTitle());
        vo.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-M-d")));
        return vo;
    }
}
