package com.memorybottle.memory_app.converter;

import com.memorybottle.memory_app.domain.MediaFile;
import com.memorybottle.memory_app.domain.MediaType;
import com.memorybottle.memory_app.domain.Memory;
import com.memorybottle.memory_app.dto.MemoryDTO;
import com.memorybottle.memory_app.vo.MediaFileVO;
import com.memorybottle.memory_app.vo.MemoryVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryConverter {

    public static Memory toEntity(MemoryDTO dto) {
        Memory memory = new Memory();
        memory.setTitle(dto.getTitle());
        memory.setDescription(dto.getDescription());

        // mediaList 转换
        List<MediaFile> mediaFiles = new ArrayList<>();
        if (dto.getMediaList() != null) {
            for (MemoryDTO.MediaItemDTO itemDTO : dto.getMediaList()) {
                MediaFile file = new MediaFile();
                file.setFileUrl(itemDTO.getFileUrl());
                file.setMediaType(itemDTO.getMediaType());
                file.setMemory(memory); // 设置反向关系
                mediaFiles.add(file);
            }
        }
        memory.setMediaFiles(mediaFiles);
        return memory;
    }

    public static MemoryVO toVO(Memory memory) {
        MemoryVO vo = new MemoryVO();
        vo.setId(memory.getId());
        vo.setTitle(memory.getTitle());
        vo.setDescription(memory.getDescription());
        //vo.setCreatedTime(memory.getCreatedTime());

        if (memory.getMediaFiles() != null) {
            List<MediaFileVO> mediaList = memory.getMediaFiles().stream()
                    .map(media -> {
                        MediaFileVO mf = new MediaFileVO();
                        mf.setFileUrl(media.getFileUrl());
                        mf.setMediaType(media.getMediaType().name());
                        return mf;
                    })
                    .collect(Collectors.toList());
            vo.setMediaList(mediaList);

            // ✅ 优先选择图片作为封面
            Optional<MediaFile> imageCover = memory.getMediaFiles().stream()
                    .filter(f -> f.getMediaType() == MediaType.IMAGE)
                    .findFirst();

            // ✅ 如果没有图片，回退使用视频作为封面
            if (imageCover.isPresent()) {
                vo.setCoverUrl(imageCover.get().getFileUrl());
            } else {
                memory.getMediaFiles().stream()
                        .filter(f -> f.getMediaType() == MediaType.VIDEO)
                        .findFirst()
                        .ifPresent(video -> vo.setCoverUrl(video.getFileUrl()));
            }
        }
        if (memory.getTimelineEvents() != null && !memory.getTimelineEvents().isEmpty()) {
            // 示例：选第一个事件的 eventDate，可根据你需求改为最早/最晚
            vo.setEventDate(memory.getTimelineEvents().get(0).getEventDate());
        }

        return vo;
    }


}
