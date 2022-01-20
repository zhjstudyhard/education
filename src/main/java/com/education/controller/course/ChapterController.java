package com.education.controller.course;


import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.dto.course.ChapterDto;
import com.education.service.course.ChapterService;
import com.education.vo.ChapterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author jubai
 * @since 2021-08-11
 */
@RestController
@RequestMapping("/api/chapter")
@CrossOrigin
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    /**
     * @param chapterDto
     * @return com.education.common.Result
     * @description 查询当前课程的大纲和章节
     * @author 橘白
     * @date 2022/1/20 19:15
     */

    @PostMapping("/getChapterVideo")
    public Result getChapterVideo(@RequestBody ChapterDto chapterDto) {

        return chapterService.getChapterVideo(chapterDto);
    }


    /**
     * @param chapterDto
     * @return com.education.common.Result
     * @description 添加课程大纲
     * @author 橘白
     * @date 2022/1/20 19:06
     */

    @PostMapping("/addChapter")
    public Result addChapter(@Validated @RequestBody ChapterDto chapterDto) {
        chapterService.addChapter(chapterDto);
        return Result.success();
    }

    /**
     * @param chapterDto
     * @return com.education.common.Result
     * @description 根据大纲id查询信息
     * @author 橘白
     * @date 2022/1/20 19:40
     */

    @PostMapping("getChapterInfo")
    public Result getChapter(@RequestBody ChapterDto chapterDto) {
        return chapterService.getChapter(chapterDto);
    }

    /**
     * @param chapterDto
     * @return com.education.common.Result
     * @description 更新大纲
     * @author 橘白
     * @date 2022/1/20 19:43
     */

    @PostMapping("/updateChapter")
    public Result updateChapter(@Validated @RequestBody ChapterDto chapterDto) {
        chapterService.updateChapter(chapterDto);

        return Result.success();
    }
//
//    @ApiOperation(value = "删除大纲")
//    @DeleteMapping("{chapterId}")
//    public Result deleteChapter(@PathVariable String chapterId){
//        educationChapterService.deleteById(chapterId);
//        return Result.success();
//    }
}

