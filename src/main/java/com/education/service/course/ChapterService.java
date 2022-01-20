package com.education.service.course;


import com.baomidou.mybatisplus.extension.service.IService;
import com.education.common.Result;
import com.education.dto.course.ChapterDto;
import com.education.entity.course.ChapterEntity;
import com.education.vo.ChapterVo;

import java.util.List;


/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author jubai
 * @since 2021-08-11
 */
public interface ChapterService extends IService<ChapterEntity> {
    void addChapter(ChapterDto chapterDto);

    /**
     * @param chapterDto
     * @return com.education.common.Result
     * @description 查询课程大纲和章节
     * @author 橘白
     * @date 2022/1/20 19:16
     */

    Result getChapterVideo(ChapterDto chapterDto);

    Result getChapter(ChapterDto chapterDto);

    void updateChapter(ChapterDto chapterDto);
//
//    /**
//     * @param chapterId
//     * @description 根据课程章节id删除
//     * @author 橘白
//     * @date 2021/8/16 13:39
//     */
//
//    void deleteById(String chapterId);
}
