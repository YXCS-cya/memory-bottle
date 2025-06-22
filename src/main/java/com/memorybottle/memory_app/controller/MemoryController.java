package com.memorybottle.memory_app.controller;

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
            @RequestParam(required = false) Integer userId,
            @RequestParam("files") List<MultipartFile> files
    ) {
        try {
            Memory memory = memoryService.saveMemoryWithFiles(title, description, userId, files);
            return ResponseEntity.ok("Memory uploaded successfully with ID: " + memory.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMemoryById(@PathVariable Integer id) {
        try {
            MemoryDetailVO vo = memoryService.getMemoryDetail(id);
            return ResponseEntity.ok(vo);
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
        return ResponseEntity.ok(voPage);
    }

    /*
    * 为了防止恶意注入，增加一个采用了DTO层的POST方法
    * */
    @PostMapping("/addByJson")
    public ResponseEntity<?> addMemoryByJson(@RequestBody MemoryDTO dto) {
        try {
            Memory memory = memoryService.saveFromDto(dto);
            return ResponseEntity.ok("Memory added from JSON with id: " + memory.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Add failed: " + e.getMessage());
        }
    }

}
