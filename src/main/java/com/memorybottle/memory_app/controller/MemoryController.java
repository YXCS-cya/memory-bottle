package com.memorybottle.memory_app.controller;

import com.memorybottle.memory_app.common.Result;
import com.memorybottle.memory_app.domain.Memory;
import com.memorybottle.memory_app.dto.MemoryDTO;
import com.memorybottle.memory_app.service.MemoryService;
import com.memorybottle.memory_app.vo.MemoryDetailVO;
import com.memorybottle.memory_app.vo.MemoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/memories")
@RequiredArgsConstructor
@CrossOrigin
public class MemoryController {

    private final MemoryService memoryService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadMemory(
            @RequestParam String title,
            @RequestParam String description,
            @RequestHeader("X-User-Id") Integer userId, //从请求头里直接获得userId 模仿正式的登陆机制
            @RequestParam("eventDate") String eventDate,
            @RequestParam("files") List<MultipartFile> files
    ) {
        title = title.trim().replaceAll(",$", "");
        description = description.trim().replaceAll(",$", "");
        eventDate = eventDate.trim().replaceAll(",$", "");

        try {
            Memory memory = memoryService.saveMemoryWithFiles(title, description, userId, eventDate, files);
            return ResponseEntity.ok(Result.success("Memory 上传成功 with ID: " + memory.getId()));
        } catch (Exception e) {
            System.out.println(eventDate);
            return ResponseEntity.status(500).body(Result.failure("上传失败：" + e.getMessage()));

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMemoryById(@PathVariable Integer id) {
        try {
            MemoryDetailVO vo = memoryService.getMemoryDetail(id);
            return ResponseEntity.ok(Result.success(vo));

            //return ResponseEntity.ok(vo);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


//    //分页查询接口，重构了一个更好的分页查询接口
//    @GetMapping
//    public ResponseEntity<?> getAllMemories(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        Page<MemoryVO> voPage = memoryService.getMemoryPage(page, size);
//        return ResponseEntity.ok(Result.success(voPage));
//    }

    /*
    * 为了防止恶意注入，增加一个采用了DTO层的POST方法
    * JSon格式没法直接上传文件，拓展功能后暂时停用这个接口
    * */
//    @PostMapping("/addByJson")
//    public ResponseEntity<?> addMemoryByJson(@RequestBody MemoryDTO dto) {
//        try {
//            Memory memory = memoryService.saveFromDto(dto);
//            return ResponseEntity.ok("Memory added from JSON with id: " + memory.getId());
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Add failed: " + e.getMessage());
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemory(@PathVariable Integer id,
                                          @RequestHeader("X-User-Id") Integer userId) {
//        System.out.println("memoriy Id = " + id);
//        System.out.println("user Id = " + userId);
        try {
            memoryService.deleteMemory(id, userId);
            return ResponseEntity.ok(Result.success("删除成功"));
        } catch (Exception e) {
            return ResponseEntity.status(403).body("拒绝访问：" + e.getMessage());
        }
    }
    //查询Memory，支持分页、模糊查询、按日期查询
    @GetMapping
    public Result<?> searchMemories(@RequestParam(required = false) String keyword,
                                    @RequestParam(required = false)
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                    @RequestParam(required = false)
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdTime"));
        Page<MemoryVO> result = memoryService.searchMemories(keyword, startDate, endDate, pageable);
        return Result.success(result);
    }

    //更新接口，只允许作者或管理员更新
    //可修改描述，可上传或替换媒体文件
    @PutMapping("/{id}")
    public Result<?> updateMemory(@PathVariable Integer id,
                                  @RequestParam(required = false) String description,
                                  @RequestParam(required = false) List<MultipartFile> mediaList,
                                  @RequestHeader("X-User-Id") Integer userId) throws IOException {
        memoryService.updateMemory(id, description, mediaList, userId);
        return Result.success("修改Memory成功！");
    }


}
