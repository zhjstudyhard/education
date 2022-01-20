package com.education.controller.course;



import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.service.course.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author jubai
 * @since 2021-08-11
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService VideoService;

//    @Autowired
//    private VodClient vodClient;
//
//    @ApiOperation(value = "添加小节")
//    @PostMapping("/addVideo")
//    public Result addVideo(@RequestBody EduVideo eduVideo) {
//        boolean save = eduVideoService.save(eduVideo);
//        if (!save) {
//            throw new GuliException(ResultCode.FAILER_CODE.getCode(), "添加失败");
//        }
//        return Result.success();
//    }
//
//    @ApiOperation(value = "删除小节")
//    @DeleteMapping("{id}")
//    public Result deleteVideo(@PathVariable String id) {
//        EduVideo eduVideo = eduVideoService.getById(id);
//        if (eduVideo == null) {
//            throw new GuliException(ResultCode.FAILER_CODE.getCode(), "小节不存在");
//        }
//        //删除视频
//        if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
//            Result result = vodClient.removeAlyVideo(eduVideo.getVideoSourceId());
//            System.out.println("result: "+result);
//            if (!result.getSuccess()) {
//               throw new GuliException(result.getCode(),result.getMessage());
//            }
//        }
//        boolean flag = eduVideoService.removeById(id);
//        if (!flag) {
//            throw new GuliException(ResultCode.FAILER_CODE.getCode(), "删除失败");
//        }
//        return Result.success();
//    }
//
//    @ApiOperation(value = "根据小节id查询信息")
//    @GetMapping("/getVideo/{videoId}")
//    public Result getVideo(@PathVariable String videoId) {
//        EduVideo eduVideo = eduVideoService.getById(videoId);
//        return Result.success().data("eduVideo", eduVideo);
//    }
//
//    @ApiOperation(value = "小节更改")
//    @PutMapping("/updateVideo")
//    public Result updateVideo(@RequestBody EduVideo eduVideo) {
//        System.out.println("更改：" + eduVideo);
//        boolean flag = eduVideoService.updateById(eduVideo);
//        if (flag) {
//            return Result.success();
//        } else {
//            throw new GuliException(ResultCode.FAILER_CODE.getCode(), "小节更改失败");
//        }
//    }

}

