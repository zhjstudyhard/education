package com.education.service.subject;

import com.baomidou.mybatisplus.extension.service.IService;
import com.education.common.Result;
import com.education.entity.subject.SubjectEntity;
import com.education.vo.SubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author jubai
 * @since 2021-08-09
 */
public interface SubjectService extends IService<SubjectEntity> {

    /**
     * 添加课程科目
     * @param file
     */
    void addSubject(MultipartFile file) throws Exception;

    /**
     * 查询所有分类(树形)
     * @return
     */
    Result getAllSubject();

    void updateSubject(SubjectEntity subjectEntity);

    void deleteSubjectById(SubjectEntity subjectEntity);
}
