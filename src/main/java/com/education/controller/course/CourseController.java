package com.education.controller.course;


import com.education.common.Result;
import com.education.dto.article.ArticleDto;
import com.education.dto.article.CommentDto;
import com.education.dto.course.CourseDto;
import com.education.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author jubai
 * @since 2021-08-11
 */

@RestController
@RequestMapping("/api/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;


    /**
     * @param courseDto
     * @return com.education.common.Result
     * @description 添加课程
     * @author 橘白
     * @date 2022/1/20 17:13
     */

    @PostMapping("/addCourse")
    public Result addCourse(@Validated @RequestBody CourseDto courseDto) {
        return courseService.addCourse(courseDto);
    }


    /**
     * @param courseDto
     * @return com.education.common.Result
     * @description 根据id查询课程信息
     * @author 橘白
     * @date 2022/1/20 17:20
     */

    @PostMapping("/findByCourseId")
    public Result findByCourseId(@RequestBody CourseDto courseDto) {

        return courseService.findByCourseId(courseDto);
    }

    /**
     * @param courseDto
     * @return com.education.common.Result
     * @description 更新课程信息
     * @author 橘白
     * @date 2022/1/20 18:52
     */

    @PostMapping("/updateCourse")
    public Result updateCourse(@Validated @RequestBody CourseDto courseDto) {
        courseService.updateCourse(courseDto);

        return Result.success();
    }

    /**
     * @param courseDto
     * @return com.education.common.Result
     * @description 查询当前发布信息
     * @author 橘白
     * @date 2022/1/21 20:17
     */

    @PostMapping("/getPublishCourseById")
    public Result getPublishCourseById(@RequestBody CourseDto courseDto) {
        return courseService.getPublishCourseById(courseDto);
    }

    /**
     * @param courseDto
     * @return com.education.common.Result
     * @description 发布课程最终状态
     * @author 橘白
     * @date 2022/1/21 20:17
     */

    @PostMapping("publishCourse")
    public Result publishCourse(@RequestBody CourseDto courseDto) {
        courseService.publishCourse(courseDto);
        return Result.success();
    }


    /**
     * @param courseDto
     * @return com.education.common.Result
     * @description 课程分页查询
     * @author 橘白
     * @date 2022/1/21 20:38
     */

    @PostMapping("pageListCourse")
    public Result pageListCourse(@RequestBody CourseDto courseDto) {
        return courseService.pageListCourse(courseDto);
    }

    /**
     * @param courseDto
     * @return com.education.common.Result
     * @description 根据id删除课程
     * @author 橘白
     * @date 2022/1/22 14:54
     */

    @PostMapping("removeCourseById")
    public Result removeCourseById(@RequestBody CourseDto courseDto) {
        courseService.removeCourseById(courseDto);
        return Result.success();
    }

    /**
     * @return com.education.common.Result
     * @description 首页查询数据
     * @author 橘白
     * @date 2022/1/24 17:10
     */

    @PostMapping("indexData")
    public Result indexData() {
        return courseService.indexData();
    }

    /**
     * @param commentDto
     * @return com.education.common.Result
     * @description 分页查询视频评论数据
     * @author 橘白
     * @date 2022/2/5 11:26
     */

    @PostMapping("queryCourseAllComment")
    public Result queryCourseAllComment(@RequestBody CommentDto commentDto) {
        return courseService.queryCourseAllComment(commentDto);
    }

    /**
     * @param courseDto
     * @return com.education.common.Result
     * @description 查询所有课程
     * @author 橘白
     * @date 2022/2/5 11:43
     */

    @PostMapping("getAllCourse")
    public Result getAllCourse(@RequestBody CourseDto courseDto) {
        return courseService.getAllCourse(courseDto);
    }
}

