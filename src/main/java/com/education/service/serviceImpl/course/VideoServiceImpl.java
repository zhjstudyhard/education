package com.education.service.serviceImpl.course;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.config.OssConfig;
import com.education.constant.Constant;
import com.education.entity.course.VideoEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.course.VideoMapper;
import com.education.service.course.VideoService;
import com.education.util.EntityUtil;
import com.education.util.InitVodCilent;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, VideoEntity> implements VideoService {
    @Autowired
    private VideoMapper videoMapper;

    @Override
    public void addVideo(VideoEntity videoEntity) {
        EntityUtil.addCreateInfo(videoEntity);
        //添加数据
        videoMapper.insert(videoEntity);
    }

    @Override
    public void deleteVideo(VideoEntity videoEntity) {
        if (StringUtils.isBlank(videoEntity.getId())) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "主键不能为空");
        }
        VideoEntity videoEntityLocal = videoMapper.selectById(videoEntity.getId());
        if (videoEntityLocal == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "数据不存在");
        }

        videoEntityLocal.setIsDeleted(Constant.ISDELETED_TRUE);
        EntityUtil.addModifyInfo(videoEntityLocal);
        //删除数据
        videoMapper.updateById(videoEntityLocal);

        //删除阿里云储存视频
        if (StringUtils.isNotBlank(videoEntityLocal.getVideoSourceId())) {
            removeAlyVideo(videoEntityLocal.getVideoSourceId());
        }

    }

    @Override
    public void deleteAliyunvod(VideoEntity videoEntity) {
        //删除阿里云储存视频
        if (StringUtils.isNotBlank(videoEntity.getVideoSourceId())) {
            removeAlyVideo(videoEntity.getVideoSourceId());
        }
    }

    @Override
    public void updateVideoById(VideoEntity videoEntity) {
        VideoEntity videoEntityLocal = videoMapper.selectById(videoEntity.getId());
        if (videoEntityLocal == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "数据不存在");
        }
        BeanUtils.copyProperties(videoEntity, videoEntityLocal);
        EntityUtil.addModifyInfo(videoEntityLocal);

        videoMapper.updateById(videoEntityLocal);
    }

    @Override
    public Result getVideoById(VideoEntity videoEntity) {
        VideoEntity videoEntityLocal = videoMapper.selectById(videoEntity.getId());
        if (videoEntityLocal == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "数据不存在");
        }

        return Result.success().data("data", videoEntityLocal);
    }

    /**
     * @param file
     * @return java.lang.String
     * @description 上传视频到阿里云
     * @author 橘白
     * @date 2021/8/19 11:06
     */
    @Override
    public Result uploadVideoAly(MultipartFile file) {
        try {
            //文件输入流
            InputStream inputStream = file.getInputStream();
            //文件名称
            String originalFilename = file.getOriginalFilename();
            //上传后文件显示名称
            String title = file.getOriginalFilename().substring(0, originalFilename.lastIndexOf("."));
            //阿里云上传文件
            UploadStreamRequest request = new UploadStreamRequest(OssConfig.ACCESS_KEY_ID, OssConfig.ACCESS_KEY_SECRET, title, originalFilename, inputStream);
            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            UploadStreamResponse response = uploadVideo.uploadStream(request);
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                videoId = response.getVideoId();
            }
            return Result.success().data("data", videoId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param videoIdList
     * @description 批量删除阿里云视频
     * @author 橘白
     * @date 2021/8/23 9:55
     */
    @Override
    public void removeMoreAlyVideo(List<String> videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(OssConfig.ACCESS_KEY_ID, OssConfig.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //videoIdList值转换成 1,2,3
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");

            //向request设置视频id
            request.setVideoIds(videoIds);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "删除视频失败");
        }
    }

    /**
     * @param id
     * @description 批量删除阿里云视频
     * @author 橘白
     * @date 2022/1/21 18:42
     */
    @Override
    public void removeAlyVideo(String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(OssConfig.ACCESS_KEY_ID, OssConfig.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
//            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "删除视频失败");
        }
    }
}
