package com.education.service.serviceImpl.course;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.dto.course.ChapterDto;
import com.education.entity.course.ChapterEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.course.ChapterMapper;
import com.education.service.course.ChapterService;
import com.education.util.EntityUtil;
import com.education.vo.ChapterVo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, ChapterEntity> implements ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;
//
//    @Autowired
//    private EduVideoMapper eduVideoMapper;
//
//    @Autowired
//    private EduVideoService eduVideoService;

    @Override
    public void addChapter(ChapterDto chapterDto) {
        ChapterEntity chapterEntity = new ChapterEntity();
        BeanUtils.copyProperties(chapterDto, chapterEntity);
        EntityUtil.addCreateInfo(chapterEntity);

        //添加数据
        chapterMapper.insert(chapterEntity);
    }


    /**
     * @param chapterDto
     * @return com.education.common.Result
     * @description 查询课程大纲和章节
     * @author 橘白
     * @date 2022/1/20 19:16
     */

    @Override
    public Result getChapterVideo(ChapterDto chapterDto) {
        ArrayList<ChapterVo> list = new ArrayList<>();
        //查询所有大纲
        QueryWrapper<ChapterEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", chapterDto.getCourseId());
        List<ChapterEntity> chapters = chapterMapper.selectList(queryWrapper);
        //查询所有章节
//        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
//        videoQueryWrapper.eq("course_id", courseId);
//        List<EduVideo> eduVideos = eduVideoMapper.selectList(videoQueryWrapper);
//        for (EduChapter eduChapter: eduChapters){
//            ChapterVo chapterVo = new ChapterVo();
//            BeanUtils.copyProperties(eduChapter, chapterVo);
//            for (EduVideo eduVideo : eduVideos) {
//                if(eduChapter.getId().equals(eduVideo.getChapterId())) {
//                    VideoVo videoVo = new VideoVo();
//                    BeanUtils.copyProperties(eduVideo, videoVo);
//                    chapterVo.getChildren().add(videoVo);
//                }
//            }
//            list.add(chapterVo);
//        }
        return Result.success().data("data",chapters);
    }

    @Override
    public Result getChapter(ChapterDto chapterDto) {
        if (StringUtils.isBlank(chapterDto.getId())){
            throw new EducationException(Result.fail().getCode(),"主键不能为空");
        }
        ChapterEntity chapterEntity = chapterMapper.selectById(chapterDto.getId());
        if (chapterEntity == null){
            throw new EducationException(Result.fail().getCode(),"数据不存在");
        }
        return Result.success().data("data",chapterEntity);
    }

    @Override
    public void updateChapter(ChapterDto chapterDto) {
        if (StringUtils.isBlank(chapterDto.getId())){
            throw new EducationException(Result.fail().getCode(),"逐渐不能为空");
        }
        ChapterEntity chapterEntity = chapterMapper.selectById(chapterDto.getId());
        BeanUtils.copyProperties(chapterDto, chapterEntity);

        //更新数据
        chapterMapper.updateById(chapterEntity);

    }

    //
//    /**
//     * @param chapterId
//     * @description 根据课程章节id删除
//     * @author 橘白
//     * @date 2021/8/16 13:39
//     */
//    @Override
//    public void deleteById(String chapterId) {
//        //判断课程大纲是否包含小节
//        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
//        videoQueryWrapper.eq("chapter_id",chapterId);
//        List<EduVideo> list = eduVideoService.list(videoQueryWrapper);
//        if (list.size() > 0) {
//            throw new GuliException(ResultCode.FAILER_CODE.getCode(),"无法删除");
//        }
//        //删除大纲
//        eduChapterMapper.deleteById(chapterId);
//    }


}
