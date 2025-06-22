package com.memorybottle.memory_app.service;

import com.memorybottle.memory_app.converter.MemoryConverter;
import com.memorybottle.memory_app.domain.MediaFile;
import com.memorybottle.memory_app.domain.MediaType;
import com.memorybottle.memory_app.domain.Memory;
import com.memorybottle.memory_app.dto.MemoryDTO;
import com.memorybottle.memory_app.repository.MediaFileRepository;
import com.memorybottle.memory_app.repository.MemoryRepository;
import com.memorybottle.memory_app.vo.MemoryDetailVO;
import com.memorybottle.memory_app.vo.MemoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoryService {

    private final MemoryRepository memoryRepository;
    private final MediaFileRepository mediaFileRepository;

    //private final String UPLOAD_DIR = "uploads/media/";
    //直接使用上面的方式，会因为TomCat临时路径导致下面代码中创建目录失败。因此改用下面的方式
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/media/";

    public Memory saveMemoryWithFiles(String title, String description, Integer userId, List<MultipartFile> files) throws IOException {
        // 1. 构建 Memory 实体
        Memory memory = new Memory();
        memory.setTitle(title);
        memory.setDescription(description);

        // 用户信息可以先设置为 null 或后期查询（简化处理）
        // memory.setUser(...)

        Memory savedMemory = memoryRepository.save(memory);

        // 2. 保存媒体文件
        List<MediaFile> mediaFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            String originalFilename = file.getOriginalFilename();
            String filePath = UPLOAD_DIR + System.currentTimeMillis() + "_" + originalFilename;

            File dest = new File(filePath);
            dest.getParentFile().mkdirs(); // 创建uploads/media目录
            file.transferTo(dest);

            // 判定媒体类型（后缀）
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            MediaType mediaType = ext.matches("mp4|mov|avi") ? MediaType.VIDEO : MediaType.IMAGE;

            MediaFile mediaFile = new MediaFile();
            mediaFile.setFileUrl("/" + filePath);
            mediaFile.setMediaType(mediaType);
            mediaFile.setMemory(savedMemory);

            mediaFiles.add(mediaFile);
        }

        mediaFileRepository.saveAll(mediaFiles);
        return savedMemory;
    }

    public Memory getMemoryById(Integer id) {
        return memoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Memory not found with id: " + id));
    }

    //分页查询接口 + VO响应封装
    public Page<MemoryVO> getMemoryPage(int page, int size) {
        Page<Memory> memoryPage = memoryRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdTime")));

        return memoryPage.map(memory -> {
            MemoryVO vo = new MemoryVO();
            vo.setId(memory.getId());
            vo.setTitle(memory.getTitle());
            vo.setDescription(memory.getDescription());
            vo.setCreatedTime(memory.getCreatedTime());

            // 只展示一个封面图（首图）
            List<MediaFile> mediaList = memory.getMediaFiles();
            if (mediaList != null && !mediaList.isEmpty()) {
                MemoryVO.MediaItem item = new MemoryVO.MediaItem();
                item.setFileUrl(mediaList.get(0).getFileUrl());
                item.setMediaType(mediaList.get(0).getMediaType());
                vo.setMediaList(List.of(item));
            }
            return vo;
        });
    }

    /*
    * 实际上是用VO层封装getMemoryById接口
    * */
    public MemoryDetailVO getMemoryDetail(Integer id) {
        Memory memory = memoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Memory not found with id: " + id));

        MemoryDetailVO vo = new MemoryDetailVO();
        vo.setId(memory.getId());
        vo.setTitle(memory.getTitle());
        vo.setDescription(memory.getDescription());
        vo.setCreatedTime(memory.getCreatedTime());

        List<MemoryDetailVO.MediaItem> mediaList = new ArrayList<>();
        if (memory.getMediaFiles() != null) {
            for (MediaFile media : memory.getMediaFiles()) {
                MemoryDetailVO.MediaItem item = new MemoryDetailVO.MediaItem();
                item.setFileUrl(media.getFileUrl());
                item.setMediaType(media.getMediaType());
                mediaList.add(item);
            }
        }

        vo.setMediaList(mediaList);
        return vo;
    }

    /*
     * 为了防止恶意注入，增加一个采用了DTO层的服务
     * */
    public Memory saveFromDto(MemoryDTO dto) {
        Memory memory = MemoryConverter.toEntity(dto);
        // 暂不处理 userId，也可加入 User 查找逻辑
        return memoryRepository.save(memory);
    }

}
