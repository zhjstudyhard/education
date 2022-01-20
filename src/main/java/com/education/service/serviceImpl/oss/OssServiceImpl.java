package com.education.service.serviceImpl.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.education.common.Result;
import com.education.config.OssConfig;
import com.education.service.oss.OssService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-08-06-15-32
 */
@Service
public class OssServiceImpl implements OssService {
    /**
     * 上传头像至oss
     *
     * @param file
     * @return
     */
    @Override
    public Result uploadFile(MultipartFile file) {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(OssConfig.END_POIND, OssConfig.ACCESS_KEY_ID, OssConfig.ACCESS_KEY_SECRET);

            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            //uuid的生成
            String uuid = UUID.randomUUID().toString().replace("-", "");
            fileName = uuid + fileName;
            //日期分类
            String dateTime = new DateTime().toString("yyyy/MM/dd");
            fileName = dateTime + "/" + fileName;
            // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
            ossClient.putObject("edu-firstly", fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            return Result.success().data("data","https://" + "edu-firstly" + "." + OssConfig.END_POIND.substring(8) + "/" + fileName);
        } catch (Exception e) {
            return null;
        }
    }

}
