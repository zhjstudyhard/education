package com.education.service.serviceImpl.course;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.constant.Constant;
import com.education.dto.course.ChapterDto;
import com.education.entity.course.ChapterEntity;
import com.education.entity.course.VideoEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.course.ChapterMapper;
import com.education.mapper.course.VideoMapper;
import com.education.service.course.ChapterService;
import com.education.service.course.VideoService;
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

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideoService videoService;

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
        queryWrapper.eq("course_id", chapterDto.getCourseId())
                .eq("is_deleted",Constant.ISDELETED_FALSE);
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("gmt_create");
        List<ChapterEntity> chapters = chapterMapper.selectList(queryWrapper);
        //查询所有章节
        QueryWrapper<VideoEntity> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.orderByAsc("sort");
        videoQueryWrapper.orderByDesc("gmt_create");
        videoQueryWrapper.eq("course_id", chapterDto.getCourseId())
                .eq("is_deleted", Constant.ISDELETED_FALSE);
        List<VideoEntity> eduVideos = videoMapper.selectList(videoQueryWrapper);
        for (ChapterEntity eduChapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            for (VideoEntity eduVideo : eduVideos) {
                if (eduChapter.getId().equals(eduVideo.getChapterId())) {
                    VideoEntity videoEntity = new VideoEntity();
                    BeanUtils.copyProperties(eduVideo, videoEntity);
                    chapterVo.getChildren().add(videoEntity);
                }
            }
            list.add(chapterVo);
        }
        return Result.success().data("data", list);
    }

    @Override
    public Result getChapter(ChapterDto chapterDto) {
        if (StringUtils.isBlank(chapterDto.getId())) {
            throw new EducationException(Result.fail().getCode(), "主键不能为空");
        }
        ChapterEntity chapterEntity = chapterMapper.selectById(chapterDto.getId());
        if (chapterEntity == null) {
            throw new EducationException(Result.fail().getCode(), "数据不存在");
        }
        return Result.success().data("data", chapterEntity);
    }

    @Override
    public void updateChapter(ChapterDto chapterDto) {
        if (StringUtils.isBlank(chapterDto.getId())) {
            throw new EducationException(Result.fail().getCode(), "逐渐不能为空");
        }
        ChapterEntity chapterEntity = chapterMapper.selectById(chapterDto.getId());
        BeanUtils.copyProperties(chapterDto, chapterEntity);

        //更新数据
        chapterMapper.updateById(chapterEntity);

    }


    /**
     * @param chapterDto
     * @description 根据课程章节id删除
     * @author 橘白
     * @date 2022/1/21 19:39
     */

    @Override
    public void deleteChapterById(ChapterDto chapterDto) {
        ChapterEntity chapterEntity = chapterMapper.selectById(chapterDto.getId());
        if (chapterEntity == null) {
            throw new EducationException(Result.fail().getCode(), "数据不存在");
        }
        //判断课程大纲是否包含小节
        QueryWrapper<VideoEntity> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", chapterDto.getId())
                .eq("is_deleted", 0);
        List<VideoEntity> list = videoService.list(videoQueryWrapper);
        if (list.size() > 0) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(), "章节下存在小节,无法删除");
        }
        //删除大纲
        chapterEntity.setIsDeleted(Constant.ISDELETED_TRUE);
        EntityUtil.addModifyInfo(chapterEntity);

        chapterMapper.updateById(chapterEntity);
    }


}
