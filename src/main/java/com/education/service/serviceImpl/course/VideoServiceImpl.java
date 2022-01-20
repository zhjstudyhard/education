package com.education.service.serviceImpl.course;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.entity.course.VideoEntity;
import com.education.mapper.course.VideoMapper;
import com.education.service.course.VideoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author jubai
 * @since 2021-08-11
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, VideoEntity> implements VideoService {
}
