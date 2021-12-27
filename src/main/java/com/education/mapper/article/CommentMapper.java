package com.education.mapper.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.dto.article.CommentDto;
import com.education.entity.article.CommentEntity;
import com.education.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-26-19-10
 */
@Mapper
@Repository
public interface CommentMapper extends BaseMapper<CommentEntity> {
    List<CommentVo> queryComment(CommentDto commentDto);
}
