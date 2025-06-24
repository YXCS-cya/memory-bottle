package com.memorybottle.memory_app.converter;

import com.memorybottle.memory_app.domain.MediaFile;
import com.memorybottle.memory_app.domain.MediaType;
import com.memorybottle.memory_app.domain.TimelineEvent;
import com.memorybottle.memory_app.vo.TimelineVO;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class TimelineConverter {

    public static TimelineVO toVO(TimelineEvent event) {
        //基础信息转换
        TimelineVO vo = new TimelineVO();
        vo.setMemoryId(event.getMemory().getId());
        vo.setTitle(event.getMemory().getTitle());
        vo.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-M-d")));

        //封面转换
        List<MediaFile> mediaFiles = event.getMemory().getMediaFiles();
        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            Optional<MediaFile> firstImage = mediaFiles.stream()
                    .filter(m -> m.getMediaType() == MediaType.IMAGE)
                    .findFirst();

            if (firstImage.isPresent()) {
                vo.setCoverUrl(firstImage.get().getFileUrl());
            } else {
                // 没有图片，但有视频，就用第一个视频路径
                Optional<MediaFile> firstVideo = mediaFiles.stream()
                        .filter(m -> m.getMediaType() == MediaType.VIDEO)
                        .findFirst();
                firstVideo.ifPresent(video -> vo.setCoverUrl(video.getFileUrl()));
            }
        }
        return vo;
    }
}
