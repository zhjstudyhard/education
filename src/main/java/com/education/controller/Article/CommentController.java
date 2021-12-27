package com.education.controller.Article;

import com.education.common.Result;
import com.education.dto.article.CommentDto;
import com.education.service.article.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-26-19-04
 */
@RestController
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * @description  添加评论
     * @param commentDto
     * @return com.education.common.Result
     * @author 橘白
     * @date 2021/12/27 20:29
     */

    @PostMapping("addComment")
    public Result addComment(@Validated @RequestBody CommentDto commentDto){
        commentService.addComment(commentDto);
        return Result.success();
    }

    /**
     * @description  删除评论
     * @param commentDto
     * @return com.education.common.Result
     * @author 橘白
     * @date 2021/12/27 20:35
     */

    @PostMapping("delComment")
    public Result delComment(@RequestBody CommentDto commentDto){
        commentService.delComment(commentDto);
        return Result.success();
    }

    /**
     * @description  分页查询评论
     * @param commentDto
     * @return com.education.common.Result
     * @author 橘白
     * @date 2021/12/27 20:35
     */
    @PostMapping("queryComment")
    public Result queryComment(@RequestBody CommentDto commentDto){
        return commentService.queryComment(commentDto);
    }


}
