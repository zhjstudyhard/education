package com.education.mapper.upload;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.entity.uploadfile.UploadFileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-15-15-45
 */
@Mapper
@Repository
public interface UploadFileMapper extends BaseMapper<UploadFileEntity> {
}
