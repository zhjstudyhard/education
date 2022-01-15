package com.education.controller.upload;

import com.education.common.Result;
import com.education.config.SecurityBean;
import com.education.entity.system.UserEntity;
import com.education.entity.uploadfile.UploadFileEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.upload.UploadFileMapper;
import com.education.util.EntityUtil;
import com.education.util.ShiroEntityUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.IdGenerator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-13-16-59
 */
@RestController
@RequestMapping("/api/upload")
@CrossOrigin
public class UploadFileUtil {
    @Autowired
    private UploadFileMapper uploadFileMapper;

    /**
     * @param file
     * @description 上传文件
     * @author 橘白
     * @date 2022/1/15 14:58
     */

    @PostMapping("uploadFile")
    public Result uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        //使用UUID生成文件随机数名称
        String fileName = UUID.randomUUID().toString().replace("-", "");
        //上传文件名
        String originalFilename = file.getOriginalFilename();
        //截取文件后缀
        String fileSuffix = originalFilename.substring(originalFilename.indexOf(46) + 1, originalFilename.length());
        //创建buff输出流提高效率
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(SecurityBean.filePath + fileName.concat(".") + fileSuffix)));
        bufferedOutputStream.write(file.getBytes());

        bufferedOutputStream.flush();
        bufferedOutputStream.close();

        //添加文件数据
        //文件实体
        UploadFileEntity uploadFileEntity = new UploadFileEntity();
        uploadFileEntity.setFileSuffix(fileSuffix);
        uploadFileEntity.setOriginFileName(originalFilename);
        uploadFileEntity.setFileName(fileName.concat(".").concat(fileSuffix));
        EntityUtil.addCreateInfo(uploadFileEntity);
        //文件添加数据
        uploadFileMapper.insert(uploadFileEntity);
        //更新文件获取路径
        uploadFileEntity.setFilePath(SecurityBean.filePathUrl.concat("/get/").concat(uploadFileEntity.getId()));
        uploadFileMapper.updateById(uploadFileEntity);

        return Result.success().data("data", uploadFileEntity);
    }

    /**
     * @param id
     * @description 获取图片
     * @author 橘白
     * @date 2022/1/15 17:13
     */

    @GetMapping("/get/{id}")
    public void uploadFile(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(id)) {
            throw new EducationException(Result.fail().getCode(), "主键不能为空");
        }
        UploadFileEntity uploadFileEntity = uploadFileMapper.selectById(id);
        if (uploadFileEntity == null) {
            throw new EducationException(Result.fail().getCode(), "文件不存在");
        } else {
            File file = new File(SecurityBean.filePath.concat(uploadFileEntity.getFileName()));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[1024];
            OutputStream outputStream = response.getOutputStream();
            //读取数据
            int len = 0;
            while ((len = bufferedInputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, len);
            }
            outputStream.close();
            bufferedInputStream.close();
        }
    }

}
