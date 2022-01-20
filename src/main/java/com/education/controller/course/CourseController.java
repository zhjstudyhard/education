package com.education.controller.course;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.dto.course.CourseDto;
import com.education.service.course.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
//
//    /**
//     * @return com.atjubai.commonutils.Result
//     * @description 查询当前发布信息
//     * @author 橘白
//     * @date 2021/8/16 16:06
//     */
//
//    @ApiOperation(value = "根据id查询发布课程信息")
//    @GetMapping("/getPublishCourseById/{courseId}")
//    public Result getPublishCourseById(@PathVariable String courseId) {
//        CoursePublishVo coursePublishVo = courseService.getPublishCourseById(courseId);
//        return Result.success().data("publishCourse",coursePublishVo);
//    }
//
//    @ApiOperation(value = "发布课程最终状态")
//    @PostMapping("publishCourse/{id}")
//    public Result getPublishCourse(@PathVariable String id){
//        EduCourse eduCourse = new EduCourse();
//        eduCourse.setId(id);
//        eduCourse.setStatus("Normal");
//        boolean flag = courseService.updateById(eduCourse);
//        if (!flag) {
//            throw new GuliException(ResultCode.FAILER_CODE.getCode(),"发布课程失败");
//        }
//        return Result.success();
//    }
//
//
//    @ApiOperation(value = "课程分页查询")
//    @PostMapping("pageListCourse/{current}/{limit}")
//    public Result pageListCourse(@ApiParam(name = "current", value = "当前页码", required = true) @PathVariable Long current,
//                                 @ApiParam(name = "limit", value = "每页数量", required = true) @PathVariable Long limit,
//                                 @RequestBody QueryCourseDto courseQuery) {
//        System.out.println("queryCourseDto: "+courseQuery);
//        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
//        if (!StringUtils.isEmpty(courseQuery.getTitle())){
//            queryWrapper.like("title",courseQuery.getTitle());
//        }
//        if (!StringUtils.isEmpty(courseQuery.getStatus())){
//            queryWrapper.eq("status", courseQuery.getStatus());
//        }
//        queryWrapper.orderByDesc("gmt_create");
//        Page<EduCourse> page = new Page<>(current, limit);
//        courseService.page(page,queryWrapper);
//        return Result.success().data("pageInfo",page);
//    }
//
//    @ApiOperation(value = "根据id删除课程")
//    @DeleteMapping("{id}")
//    public Result removeCourseById(@PathVariable String id){
//        Integer integer = courseService.removeCourseById(id);
//        if(integer > 0){
//            return Result.success();
//        }
//        return Result.fail();
//    }
}

