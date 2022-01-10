package com.education.service.article;

import com.education.common.Result;
import com.education.dto.article.MessageDto;

import java.io.IOException;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-08-17-07
 */
public interface MessageService {
    Result queryMessageCount();

    Result queryMessagePage(MessageDto messageDto) throws IOException, Exception;

    void delMessagePage(MessageDto messageDto);
}
