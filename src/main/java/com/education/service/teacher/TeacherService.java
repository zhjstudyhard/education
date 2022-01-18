package com.education.service.teacher;


import com.baomidou.mybatisplus.extension.service.IService;
import com.education.common.Result;
import com.education.dto.teacher.TeacherDto;
import com.education.entity.teacher.TeacherEntity;
import com.education.vo.TeacherVo;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author juBai
 * @since 2021-07-28
 */
public interface TeacherService extends IService<TeacherEntity> {

    void addTeacher(TeacherDto teacherDto);

    Result pageListTeacher(TeacherDto teacherDto);

    Result getTeacherById(TeacherDto teacherDto);

    void deleteTeacherById(TeacherDto teacherDto);

    void updateTeacher(TeacherDto teacherDto);
}
