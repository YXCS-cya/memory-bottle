package com.memorybottle.memory_app.controller;

import com.memorybottle.memory_app.common.Result;
import com.memorybottle.memory_app.domain.Memory;
import com.memorybottle.memory_app.dto.MemoryDTO;
import com.memorybottle.memory_app.service.MemoryService;
import com.memorybottle.memory_app.vo.MemoryDetailVO;
import com.memorybottle.memory_app.vo.MemoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam("X-User-Id") Integer userId,
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


    //分页查询接口
    @GetMapping
    public ResponseEntity<?> getAllMemories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MemoryVO> voPage = memoryService.getMemoryPage(page, size);
        return ResponseEntity.ok(Result.success(voPage));
    }

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


}
