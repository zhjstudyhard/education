package com.education.service.article;

import com.education.common.Result;
import com.education.dto.article.CommentDto;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-26-19-09
 */
public interface CommentService {
    void addComment(CommentDto commentDto) throws Exception;

    void delComment(CommentDto commentDto);

    Result queryComment(CommentDto commentDto);

    Result queryAllComment(CommentDto commentDto);

    void addVideoComment(CommentDto commentDto) throws Exception;

    Result queryCourseComment(CommentDto commentDto);
}
