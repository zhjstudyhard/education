package com.education.mapper.subject;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.entity.subject.SubjectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author jubai
 * @since 2021-08-09
 */
@Mapper
@Repository
public interface SubjectMapper extends BaseMapper<SubjectEntity> {

    void updateBatchIds(@Param("ids") ArrayList<String> ids);
}
