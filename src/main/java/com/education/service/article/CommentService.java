package com.education.service.article;

import com.education.common.Result;
import com.education.dto.article.CommentDto;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-26-19-09
 */
public interface CommentService {
    void addComment(CommentDto commentDto);

    void delComment(CommentDto commentDto);

    Result queryComment(CommentDto commentDto);
}
