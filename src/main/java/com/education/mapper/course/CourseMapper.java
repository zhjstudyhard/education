package com.education.mapper.course;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.dto.course.CourseDto;
import com.education.entity.course.CourseEntity;
import com.education.vo.CourseVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author jubai
 * @since 2021-08-11
 */
@Mapper
@Repository
public interface CourseMapper extends BaseMapper<CourseEntity> {


    CourseVO findByCourseId(String id);

    CourseVO getPublishCourseById(String id);

    List<CourseVO> pageListCourse(CourseDto courseDto);

    List<CourseEntity> selectCourses();
}
