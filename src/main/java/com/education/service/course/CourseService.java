package com.education.service.course;


import com.baomidou.mybatisplus.extension.service.IService;
import com.education.common.Result;
import com.education.dto.course.CourseDto;
import com.education.entity.course.CourseEntity;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author jubai
 * @since 2021-08-11
 */
public interface CourseService extends IService<CourseEntity> {
    Result addCourse(CourseDto courseDto);

    /**
     * @param courseDto
     * @return com.education.entity.course.CourseVO
     * @description 根据课程id联表查询课程信息
     * @author 橘白
     * @date 2022/1/20 17:14
     */
    Result findByCourseId(CourseDto courseDto);

    void updateCourse(CourseDto courseDto);
//
//    /**
//     * @param id
//     * @return com.atjubai.eduservice.vo.CoursePublishVo
//     * @description 根据id查询发布课程信息
//     * @author 橘白
//     * @date 2021/8/16 16:13
//     */
//
//    CoursePublishVo getPublishCourseById(String id);
//
//    /**
//     * @param id
//     * @return java.lang.Integer
//     * @description 删除课程
//     * @author 橘白
//     * @date 2021/8/17 9:16
//     */
//
//    Integer removeCourseById(String id);
}
