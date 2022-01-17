package com.education.mapper.teacher;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.entity.teacher.TeacherEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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

}
