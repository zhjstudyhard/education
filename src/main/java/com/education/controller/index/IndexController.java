package com.education.controller.index;

import com.education.common.Result;
import com.education.dto.teacher.TeacherDto;
import com.education.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-01-25-16-32
 */
@RequestMapping("/api/index")
@RestController
@CrossOrigin
public class IndexController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/getTeacherInfoIndexById")
    public Result getTeacherInfoIndexById(@RequestBody TeacherDto teacherDto){
        return teacherService.getTeacherInfoIndexById(teacherDto);
    }

}
