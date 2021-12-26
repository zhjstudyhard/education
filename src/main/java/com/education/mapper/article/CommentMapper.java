package com.education.mapper.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.entity.article.CommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-26-19-10
 */
@Mapper
@Repository
public interface CommentMapper extends BaseMapper<CommentEntity> {
}
