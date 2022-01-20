package com.education.mapper.course;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.entity.course.CourseDescriptionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CourseDescriptionMapper extends BaseMapper<CourseDescriptionEntity> {

}
