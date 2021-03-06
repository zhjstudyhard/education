package com.education.controller.teacher;

import com.education.common.Result;
import com.education.dto.teacher.TeacherDto;
import com.education.entity.teacher.TeacherEntity;
import com.education.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    /**
     * @return com.education.common.Result
     * @description 所有讲师列表
     * @author 橘白
     * @date 2022/1/20 15:32
     */

    @PostMapping("/findAll")
    public Result findAllTeacher() {
        List<TeacherEntity> list = teacherService.list(null);
        return Result.success().data("data", list);
    }


    /**
     * @param teacherDto
     * @return com.education.common.Result
     * @description 根据id删除讲师
     * @author 橘白
     * @date 2022/1/18 16:29
     */

    @PostMapping("deleteTeacherById")
    public Result deleteTeacherById(@RequestBody TeacherDto teacherDto) {
        teacherService.deleteTeacherById(teacherDto);
        return Result.success();
    }

    /**
     * @param teacherDto
     * @return com.education.common.Result
     * @description 讲师分页查询
     * @author 橘白
     * @date 2022/1/18 16:34
     */

    @PostMapping("pageListTeacher")
    public Result pageListTeacher(@RequestBody TeacherDto teacherDto) {
        return teacherService.pageListTeacher(teacherDto);
    }

    /**
     * @param teacherDto
     * @return com.education.common.Result
     * @description 新增讲师
     * @author 橘白
     * @date 2022/1/17 19:50
     */

    @PostMapping("/addTeacher")
    public Result addTeacher(@Validated @RequestBody TeacherDto teacherDto) {
        teacherService.addTeacher(teacherDto);
        return Result.success();
    }


    /**
     * @param teacherDto
     * @return com.education.common.Result
     * @description 根据ID查询讲师
     * @author 橘白
     * @date 2022/1/18 16:19
     */

    @PostMapping("getTeacherById")
    public Result getTeacherById(@RequestBody TeacherDto teacherDto) {
        return teacherService.getTeacherById(teacherDto);

    }

    @PostMapping("updateTeacher")
    public Result updateTeacher(@Validated @RequestBody TeacherDto teacherDto) {
        teacherService.updateTeacher(teacherDto);
        return Result.success();
    }

}

