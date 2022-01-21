package com.education.service.course;

import com.baomidou.mybatisplus.extension.service.IService;
import com.education.common.Result;
import com.education.entity.course.VideoEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author jubai
 * @since 2021-08-11
 */
public interface VideoService extends IService<VideoEntity> {

    void addVideo(VideoEntity videoEntity);

    /**
     * @param file
     * @return com.education.common.Result
     * @description 上传视频到阿里云
     * @author 橘白
     * @date 2022/1/21 16:15
     */

    Result uploadVideoAly(MultipartFile file);

    /**
     * @param videoIdList
     * @description 批量删除阿里云视频
     * @author 橘白
     * @date 2021/8/23 9:55
     */

    void removeMoreAlyVideo(List<String> videoIdList);

    void deleteVideo(VideoEntity videoEntity);

    /**
     * @param id
     * @description 批量删除阿里云视频
     * @author 橘白
     * @date 2022/1/21 18:42
     */

    void removeAlyVideo(String id);

    void updateVideoById(VideoEntity videoEntity);

    Result getVideoById(VideoEntity videoEntity);

    void deleteAliyunvod(VideoEntity videoEntity);
}
