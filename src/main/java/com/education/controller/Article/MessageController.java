package com.education.controller.Article;

import com.education.common.Result;
import com.education.service.article.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-08-17-05
 */
@RequestMapping("/api/message")
@RestController
@CrossOrigin
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * @return com.education.common.Result
     * @description 查询未读消息数量
     * @author 橘白
     * @date 2022/1/8 19:31
     */

    @PostMapping("/queryMessageCount")
    public Result queryMessageCount() {
        return messageService.queryMessageCount();
    }

}
