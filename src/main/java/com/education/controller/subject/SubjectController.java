package com.education.controller.subject;

import com.education.common.Result;
import com.education.entity.subject.SubjectEntity;
import com.education.service.subject.SubjectService;
import com.education.vo.SubjectVo;
import org.apache.shiro.concurrent.SubjectAwareScheduledExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author jubai
 * @since 2021-08-09
 */
@CrossOrigin
@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;


    /**
     * 添加分类
     *
     * @return
     */
    @PostMapping("/addSubject")
    public Result addSubject(MultipartFile file) throws Exception {
        subjectService.addSubject(file);
        return Result.success();
    }

    /**
     * 课程分类列表（树形）
     *
     * @return
     */
    @PostMapping("/getAllSubject")
    public Result getAllSubject() {
        return subjectService.getAllSubject();
    }

    /**
     * 修改课程分类
     *
     * @return
     */
    @PostMapping("/updateSubject")
    public Result updateSubject(@Validated({SubjectEntity.update.class}) @RequestBody SubjectEntity subjectEntity) {
        subjectService.updateSubject(subjectEntity);
        return Result.success();
    }

    /**
     * 删除课程分类
     *
     * @return
     */
    @PostMapping("/deleteSubjectById")
    public Result deleteSubjectById(@RequestBody SubjectEntity subjectEntity) {
        subjectService.deleteSubjectById(subjectEntity);
        return Result.success();
    }

}

