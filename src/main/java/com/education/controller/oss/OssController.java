package com.education.controller.oss;

import com.education.common.Result;
import com.education.service.oss.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/oss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    /**
     * @param file
     * @return com.education.common.Result
     * @description oss文件上传存储
     * @author 橘白
     * @date 2022/1/20 16:39
     */

    @PostMapping("uploadFile")
    public Result uploadFileAvatar(MultipartFile file) {

        return ossService.uploadFile(file);
    }
}
