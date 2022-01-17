package com.education.service.teacher;


import com.baomidou.mybatisplus.extension.service.IService;
import com.education.dto.teacher.TeacherDto;
import com.education.entity.teacher.TeacherEntity;

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
}
