package com.education.service.article;

import com.education.common.Result;
import com.education.dto.article.MessageDto;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-08-17-07
 */
public interface MessageService {
    Result queryMessageCount();

    Result queryMessagePage(MessageDto messageDto);
}
