package com.memorybottle.memory_app.common;

import com.memorybottle.memory_app.domain.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileUploadUtil {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/media/";

    public String save(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传文件为空");
        }

        String originalFilename = file.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + originalFilename;
        String filePath = UPLOAD_DIR + fileName;

        File dest = new File(filePath);
        dest.getParentFile().mkdirs();
        file.transferTo(dest);

        // ✅ 返回逻辑路径
        return "uploads/media/" + fileName;
    }

    public MediaType detectType(String filename) {
        String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return ext.matches("mp4|mov|avi") ? MediaType.VIDEO : MediaType.IMAGE;
    }
}


