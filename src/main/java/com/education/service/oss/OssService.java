package com.education.service.oss;

import com.education.common.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-08-06-15-30
 */
public interface OssService {
    /**
     * 上传头像至oss
     * @param file
     * @return
     */
    Result uploadFile(MultipartFile file);

}
