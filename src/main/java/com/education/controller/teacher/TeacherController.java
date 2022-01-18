package com.education.controller.teacher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.common.Result;
import com.education.dto.system.UserDto;
import com.education.dto.teacher.TeacherDto;
import com.education.service.teacher.TeacherService;
import com.education.vo.TeacherVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;


@RestController
@RequestMapping("/api/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


//    /**
//     * @return com.atjubai.commonutils.Result
//     * @description 所有讲师列表
//     * @author 橘白
//     * @date 2021/8/12 17:09
//     */
//    @GetMapping("/findAll")
//    public Result findAllTeacher() {
//        List<EduTeacher> list = teacherService.list(null);
//        return Result.success().data("items", list);
//    }
//

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

