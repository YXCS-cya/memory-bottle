package com.memorybottle.memory_app.converter;

import com.memorybottle.memory_app.domain.MediaFile;
import com.memorybottle.memory_app.domain.Memory;
import com.memorybottle.memory_app.dto.MemoryDTO;
import com.memorybottle.memory_app.vo.MediaFileVO;
import com.memorybottle.memory_app.vo.MemoryVO;

import java.util.ArrayList;
import java.util.List;
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
        vo.setCreatedTime(memory.getCreatedTime());

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
        }

        return vo;
    }

}
