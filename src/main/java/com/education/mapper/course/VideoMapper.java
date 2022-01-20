package com.education.mapper.course;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.entity.course.VideoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author jubai
 * @since 2021-08-11
 */
@Mapper
@Repository
public interface VideoMapper extends BaseMapper<VideoEntity> {

}
