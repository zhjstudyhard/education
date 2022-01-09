package com.education.mapper.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.dto.article.ArticleDto;
import com.education.dto.article.MessageDto;
import com.education.entity.article.ArticleEntity;
import com.education.entity.article.MessageEntity;
import com.education.vo.ArticleVo;
import com.education.vo.MessageVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-15-10-03
 */
@Mapper
@Repository
public interface MessageMapper extends BaseMapper<MessageEntity> {

    Integer queryMessageCount(String id);

    List<MessageVo> queryMessagePage(MessageDto messageDto);

    @MapKey("id")
    Map<String,MessageVo> queryParentMessage(Set<String> parentCommentIds);
}
