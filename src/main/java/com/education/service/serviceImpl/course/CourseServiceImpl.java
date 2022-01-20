package com.education.service.serviceImpl.course;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.constant.Constant;
import com.education.dto.course.CourseDto;
import com.education.entity.course.CourseDescriptionEntity;
import com.education.entity.course.CourseEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.course.CourseDescriptionMapper;
import com.education.mapper.course.CourseMapper;
import com.education.service.course.CourseService;
import com.education.util.EntityUtil;
import com.education.vo.CourseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author jubai
 * @since 2021-08-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseEntity> implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;


    @Override
    public Result addCourse(CourseDto courseDto) {
        //添加至课程表
        CourseEntity courseEntity = new CourseEntity();
        BeanUtils.copyProperties(courseDto, courseEntity);
        courseEntity.setStatus("Draft");
        EntityUtil.addCreateInfo(courseEntity);

        int flag = courseMapper.insert(courseEntity);
        if (flag == 0) {
            //添加失败
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "添加课程失败");
        }
        //添加课程描述
        CourseDescriptionEntity courseDescriptionEntity = new CourseDescriptionEntity();
        courseDescriptionEntity.setDescription(courseDto.getDescription());
        courseDescriptionEntity.setCourseId(courseEntity.getId());
        EntityUtil.addCreateInfo(courseDescriptionEntity);
        courseDescriptionMapper.insert(courseDescriptionEntity);
        return Result.success().data("data", courseEntity.getId());
    }

    /**
     * @param courseDto
     * @return com.education.common.Result
     * @description 根据课程id联表查询课程信息
     * @author 橘白
     * @date 2022/1/20 18:37
     */

    @Override
    public Result findByCourseId(CourseDto courseDto) {
        CourseEntity courseEntity = courseMapper.selectById(courseDto.getId());
        if (courseEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "课程不存在");
        }
        CourseVO courseVO = courseMapper.findByCourseId(courseDto.getId());
        return Result.success().data("data", courseVO);
    }

    @Override
    public void updateCourse(CourseDto courseDto) {
        CourseEntity courseEntity = courseMapper.selectById(courseDto.getId());
        if (courseEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "课程不存在");
        }
        //更新课程信息
        BeanUtils.copyProperties(courseDto, courseEntity);
        courseMapper.updateById(courseEntity);
        //更新课程描述信息
        QueryWrapper<CourseDescriptionEntity> courseDescriptionEntityQueryWrapper = new QueryWrapper<>();
        courseDescriptionEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("course_id",courseEntity.getId());
        CourseDescriptionEntity courseDescriptionEntity = courseDescriptionMapper.selectOne(courseDescriptionEntityQueryWrapper);
        courseDescriptionEntity.setDescription(courseDto.getDescription());
        courseDescriptionMapper.updateById(courseDescriptionEntity);
    }

    //
//    /**
//     * @param id
//     * @return com.atjubai.eduservice.vo.CoursePublishVo
//     * @description 根据id查询发布课程信息
//     * @author 橘白
//     * @date 2021/8/16 16:13
//     */
//    @Override
//    public CoursePublishVo getPublishCourseById(String id) {
//        CoursePublishVo coursePublishVo = courseMapper.getPublishCourseById(id);
//        return coursePublishVo;
//    }
//
//    /**
//     * @param id
//     * @return java.lang.Integer
//     * @description 删除课程
//     * @author 橘白
//     * @date 2021/8/17 9:16
//     */
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public Integer removeCourseById(String id) {
//        EduCourse eduCourse = courseMapper.selectById(id);
//        if (eduCourse == null) {
//            throw new GuliException(ResultCode.FAILER_CODE.getCode(), "课程不存在");
//        }
//        //查询所有视频源id
//        ArrayList<String> list = new ArrayList<>();
//        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
//        eduVideoQueryWrapper.eq("course_id", id);
//        List<EduVideo> eduVideos = eduVideoMapper.selectList(eduVideoQueryWrapper);
//        for (EduVideo eduVideo : eduVideos) {
//            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
//                list.add(eduVideo.getVideoSourceId());
//            }
//        }
//        //批量删除阿里云视频
//        if (list.size() > 0) {
//            Result result = vodClient.deleteBatch(list);
//            System.out.println("result: "+result);
//        }
//        //删除课程视频小节
//        eduVideoService.remove(eduVideoQueryWrapper);
//        //删除课程大纲
//        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
//        eduChapterQueryWrapper.eq("course_id", id);
//        eduChapterService.remove(eduChapterQueryWrapper);
//        //删除课程内容
//        eduCourseDescriptionService.removeById(id);
//        //删除课程
//        int i = courseMapper.deleteById(id);
//
//        //删除oss图片
//        String url = eduCourse.getCover();
//        this.removeFile(url);
//        return i;
//    }
//
//    //删除图片
//    public void removeFile(String url) {
//        try {
//            // Endpoint
//            String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
//            // keyId
//            String accessKeyId = "LTAI5tAUqSobwA3iSJahrvEK";
//            // KeySecret
//            String accessKeySecret = "zul8WPqOuZHXA2U3DMx5zlBs8CtvQ6";
//            // 第一个文件夹
//            String bucketName = "edu-firstly";
//            //文件路径
//            String fileNameUrl = endpoint.replace("//", "//" + bucketName + ".");
//            fileNameUrl = url.substring(fileNameUrl.length() + 1);
//            // 创建OSSClient实例。
//            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//            // 判断当前文件url 是否存在
//            boolean exist = ossClient.doesObjectExist(bucketName, fileNameUrl);
//            if (!exist) {
//                throw new GuliException(ResultCode.FAILER_CODE.getCode(), "文件不存在");
//            } else {
//                // 删除文件。
//                ossClient.deleteObject(bucketName, fileNameUrl);
//                // 关闭OSSClient。
//                ossClient.shutdown();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
