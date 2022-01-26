package com.education.service.serviceImpl.course;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.config.GlobalData;
import com.education.config.OssConfig;
import com.education.constant.Constant;
import com.education.dto.base.ResponsePageDto;
import com.education.dto.course.ChapterDto;
import com.education.dto.course.CourseDto;
import com.education.entity.course.ChapterEntity;
import com.education.entity.course.CourseDescriptionEntity;
import com.education.entity.course.CourseEntity;
import com.education.entity.course.VideoEntity;
import com.education.entity.teacher.TeacherEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.course.ChapterMapper;
import com.education.mapper.course.CourseDescriptionMapper;
import com.education.mapper.course.CourseMapper;
import com.education.mapper.course.VideoMapper;
import com.education.mapper.teacher.TeacherMapper;
import com.education.service.course.ChapterService;
import com.education.service.course.CourseService;
import com.education.service.course.VideoService;
import com.education.util.EntityUtil;
import com.education.vo.ChapterVo;
import com.education.vo.CourseVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ChapterService chapterService;


    @Override
    public Result addCourse(CourseDto courseDto) {
        //添加至课程表
        CourseEntity courseEntity = new CourseEntity();
        BeanUtils.copyProperties(courseDto, courseEntity);
        courseEntity.setStatus("Draft");
        courseEntity.setViewCount(new Long(0));
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
        //调用其它业务层查询课程章节和小节信息
        ChapterDto chapterDto = new ChapterDto();
        chapterDto.setCourseId(courseDto.getId());

        Result result = chapterService.getChapterVideo(chapterDto);
        courseVO.setChapterVos((List<ChapterVo>) result.getData().get("data"));
        //更新视频浏览量
        if (courseEntity.getViewCount() == null) {
            courseEntity.setViewCount(new Long(1));
        } else {
            courseEntity.setViewCount(courseEntity.getViewCount() + 1);
        }
        courseMapper.updateById(courseEntity);

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
                .eq("course_id", courseEntity.getId());
        CourseDescriptionEntity courseDescriptionEntity = courseDescriptionMapper.selectOne(courseDescriptionEntityQueryWrapper);
        courseDescriptionEntity.setDescription(courseDto.getDescription());
        courseDescriptionMapper.updateById(courseDescriptionEntity);
    }

    @Override
    public Result getPublishCourseById(CourseDto courseDto) {
        if (courseDto.getId() == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "主键不能为空");
        }
        CourseEntity courseEntity = courseMapper.selectById(courseDto.getId());
        if (courseEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "数据不存在");
        }
        CourseVO courseVO = courseMapper.getPublishCourseById(courseDto.getId());
        return Result.success().data("data", courseVO);
    }

    @Override
    public void publishCourse(CourseDto courseDto) {
        if (courseDto.getId() == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "主键不能为空");
        }
        CourseEntity courseEntity = courseMapper.selectById(courseDto.getId());
        if (courseEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "数据不存在");
        }
        courseEntity.setStatus("Normal");
        EntityUtil.addModifyInfo(courseEntity);

        courseMapper.updateById(courseEntity);
    }

    @Override
    public Result pageListCourse(CourseDto courseDto) {
        PageHelper.startPage(courseDto.getCurrentPage(), courseDto.getPageSize());
        if (StringUtils.isNotBlank(courseDto.getSubjectParentId()) && courseDto.getSubjectParentId().equals(Constant.NUMBER_NEGATIVE_ONE)) {
            courseDto.setSubjectParentId("");
        }
        List<CourseVO> list = courseMapper.pageListCourse(courseDto);
        for (CourseVO courseVO : list) {
            courseVO.setStatusName(GlobalData.dictMap.get(courseVO.getStatus()).getDictionaryValue());
        }
        PageInfo<CourseVO> courseVOPageInfo = new PageInfo<>(list);
        return Result.success().data("data", new ResponsePageDto<>(courseVOPageInfo.getList(), courseVOPageInfo.getTotal(), courseVOPageInfo.getPages(), courseVOPageInfo.getPageNum()));
    }

    @Override
    public void removeCourseById(CourseDto courseDto) {
        CourseEntity courseEntity = courseMapper.selectById(courseDto.getId());
        if (courseEntity == null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "课程不存在");
        }
        //查询所有视频源id
        List<String> videoSourceIds = new ArrayList<>();
        List<String> videoIds = new ArrayList<>();
        QueryWrapper<VideoEntity> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", courseDto.getId())
                .eq("is_deleted", Constant.ISDELETED_FALSE);
        List<VideoEntity> eduVideos = videoMapper.selectList(eduVideoQueryWrapper);
        for (VideoEntity videoEntity : eduVideos) {
            if (StringUtils.isNotBlank(videoEntity.getVideoSourceId())) {
                videoSourceIds.add(videoEntity.getVideoSourceId());
            }
            videoIds.add(videoEntity.getId());
        }
        //批量删除阿里云视频
        if (videoSourceIds.size() > 0) {
            videoService.removeMoreAlyVideo(videoSourceIds);
        }
        //删除课程视频小节
        if (videoIds.size() > 0) {
            videoMapper.deleteVideoBatchIds(videoIds);
        }
        //删除课程大纲
        List<String> chapterIds = new ArrayList<>();
        QueryWrapper<ChapterEntity> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", courseDto.getId())
                .eq("is_deleted", Constant.ISDELETED_FALSE);
        List<ChapterEntity> chapterEntities = chapterMapper.selectList(eduChapterQueryWrapper);
        for (ChapterEntity chapterEntity : chapterEntities) {
            chapterIds.add(chapterEntity.getId());
        }
        if (chapterIds.size() > 0) {
            chapterMapper.deleteChapterBatch(chapterIds);
        }
        //删除课程内容
        QueryWrapper<CourseDescriptionEntity> courseDescriptionEntityQueryWrapper = new QueryWrapper<>();
        courseDescriptionEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                .eq("course_id", courseDto.getId());
        CourseDescriptionEntity courseDescriptionEntity = courseDescriptionMapper.selectOne(courseDescriptionEntityQueryWrapper);
        if (courseDescriptionEntity != null) {
            courseDescriptionEntity.setIsDeleted(Constant.ISDELETED_TRUE);
            EntityUtil.addModifyInfo(courseDescriptionEntity);

            courseDescriptionMapper.updateById(courseDescriptionEntity);
        }
        //删除课程
        courseEntity.setIsDeleted(Constant.ISDELETED_TRUE);
        EntityUtil.addModifyInfo(courseEntity);

        courseMapper.updateById(courseEntity);

        //删除oss图片
        String url = courseEntity.getCover();
        if (StringUtils.isNotBlank(url)) {
            this.removeFile(url);
        }

    }

    //删除图片
    public void removeFile(String url) {
        try {
            // Endpoint
            String endpoint = OssConfig.END_POIND;
            // keyId
            String accessKeyId = OssConfig.ACCESS_KEY_ID;
            // KeySecret
            String accessKeySecret = OssConfig.ACCESS_KEY_SECRET;
            // 第一个文件夹
            String bucketName = OssConfig.BUCKET_NAME;
            //文件路径
            String fileNameUrl = endpoint.replace("//", "//" + bucketName + ".");
            fileNameUrl = url.substring(fileNameUrl.length() + 1);
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 判断当前文件url 是否存在
            boolean exist = ossClient.doesObjectExist(bucketName, fileNameUrl);
            if (!exist) {
                throw new EducationException(ResultCode.FAILER_CODE.getCode(), "文件不存在");
            } else {
                // 删除文件。
                ossClient.deleteObject(bucketName, fileNameUrl);
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Result indexData() {
        //查询前八条课程
        List<CourseEntity> courses = courseMapper.selectCourses();
        //查询讲师
        List<TeacherEntity> teachers = teacherMapper.selectTeachers();
        HashMap<String, Object> map = new HashMap<>();
        map.put("eduList", courses);
        map.put("teacherList", teachers);
        return Result.success().data(map);
    }
}
