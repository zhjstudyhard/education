package com.education.mapper.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.dto.article.ArticleDto;
import com.education.entity.article.ArticleEntity;
import com.education.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-15-10-03
 */
@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<ArticleEntity> {
    List<ArticleVo> getArticlePage(ArticleDto articleDto);
}
