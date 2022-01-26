package com.education.controller.course;


import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.entity.course.VideoEntity;
import com.education.service.course.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author jubai
 * @since 2021-08-11
 */
@RestController
@RequestMapping("/api/video")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService videoService;


    /**
     * @param videoEntity
     * @return com.education.common.Result
     * @description 添加小节
     * @author 橘白
     * @date 2022/1/21 15:35
     */

    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody VideoEntity videoEntity) {
        videoService.addVideo(videoEntity);
        return Result.success();
    }

    /**
     * @param videoEntity
     * @return com.education.common.Result
     * @description 删除小节
     * @author 橘白
     * @date 2022/1/21 18:47
     */

    @PostMapping("deleteVideoById")
    public Result deleteVideo(@RequestBody VideoEntity videoEntity) {
        videoService.deleteVideo(videoEntity);
        return Result.success();
    }

    /**
     * @param videoEntity
     * @return com.education.common.Result
     * @description 根据小节id查询信息
     * @author 橘白
     * @date 2022/1/21 18:56
     */

    @PostMapping("/getVideoById")
    public Result getVideoById(@RequestBody VideoEntity videoEntity) {
        return videoService.getVideoById(videoEntity);
    }

    /**
     * @param videoEntity
     * @return com.education.common.Result
     * @description 小节更改
     * @author 橘白
     * @date 2022/1/21 18:53
     */

    @PostMapping("/updateVideoById")
    public Result updateVideo(@Validated @RequestBody VideoEntity videoEntity) {
        videoService.updateVideoById(videoEntity);
        return Result.success();
    }

    /**
     * @param file
     * @return com.education.common.Result
     * @description 上传视频储存阿里点播
     * @author 橘白
     * @date 2022/1/21 15:51
     */

    @PostMapping("uploadAlyiVideo")
    public Result uploadVideoAly(MultipartFile file) {
        //返回上传视频id
        return videoService.uploadVideoAly(file);
    }


    /**
     * @param videoEntity
     * @return com.education.common.Result
     * @description 删除阿里云视频
     * @author 橘白
     * @date 2022/1/26 16:35
     */

    @PostMapping("deleteAliyunvod")
    public Result deleteAliyunvod(@RequestBody VideoEntity videoEntity) {
        //返回上传视频id
        videoService.deleteAliyunvod(videoEntity);
        return Result.success();
    }

    /**
     * @param videoEntity
     * @return com.education.common.Result
     * @description 查看视频凭证信息
     * @author 橘白
     * @date 2022/1/26 16:37
     */

    @PostMapping("getVideoPlayAuth")
    public Result getVideoPlayAuth(@RequestBody VideoEntity videoEntity) {
        //返回视频凭证
        return videoService.getVideoPlayAuth(videoEntity);
    }
}

