package com.education.mapper.teacher;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.dto.teacher.TeacherDto;
import com.education.entity.teacher.TeacherEntity;
import com.education.vo.TeacherVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author juBai
 * @since 2021-07-28
 */
@Mapper
@Repository
public interface TeacherMapper extends BaseMapper<TeacherEntity> {

    List<TeacherVo> pageListTeacher(TeacherDto teacherDto);

    List<TeacherEntity> selectTeachers();
}
