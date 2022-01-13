package com.education.controller.upload;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-13-16-59
 */
@RestController
@RequestMapping("/api/upload")
@CrossOrigin
public class UploadFileUtil {

    @PostMapping("uploadFile")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("filePath: "+file);
    }

}
