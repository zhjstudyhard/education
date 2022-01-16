package com.education.mapper.upload;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.entity.uploadfile.UploadFileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-15-15-45
 */
@Mapper
@Repository
public interface UploadFileMapper extends BaseMapper<UploadFileEntity> {
    void updateRelativeId(@Param("filePathList") List<String> filePathList, @Param("id") String id);

    void delRelativeId(@Param("uploadFileList") List<String> uploadFileList, @Param("id") String id);
}
