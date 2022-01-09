package com.education.controller.Article;

import com.education.common.Result;
import com.education.dto.article.MessageDto;
import com.education.service.article.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * @param messageDto
     * @return com.education.common.Result
     * @description 消息通知分页查询
     * @author 橘白
     * @date 2022/1/9 20:04
     */

    @PostMapping("/queryMessagePage")
    public Result queryMessagePage(@RequestBody MessageDto messageDto) {
        return messageService.queryMessagePage(messageDto);
    }


}
