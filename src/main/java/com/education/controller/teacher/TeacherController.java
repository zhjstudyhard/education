package com.education.controller.teacher;

import com.education.common.Result;
import com.education.dto.system.UserDto;
import com.education.dto.teacher.TeacherDto;
import com.education.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
//    /**
//     * @param id
//     * @return com.atjubai.commonutils.Result
//     * @description
//     * @author 橘白
//     * @date 2021/8/12 17:09
//     */
//
//    @DeleteMapping("/delete/{id}")
//    public Result deleteTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
//        boolean b = teacherService.removeById(id);
//        if (b) {
//            return Result.success();
//        } else {
//            return Result.fail();
//        }
//    }

//    /**
//     * 讲师分页查询
//     *
//     * @param current
//     * @param limit
//     * @return
//     */
//    @PostMapping("pageTeacher/{current}/{limit}")
//    public Result pageListTeacher(@ApiParam(name = "current", value = "当前页码", required = true) @PathVariable Long current,
//                                  @ApiParam(name = "limit", value = "每页数量", required = true) @PathVariable Long limit,
//                                  @RequestBody TeacherQueryDto teacherQueryDto) {
//        System.out.println("teacherQueryDto: " + teacherQueryDto);
//        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
//        if (!StringUtils.isEmpty(teacherQueryDto.getName())) {
//            wrapper.like("name", teacherQueryDto.getName());
//        }
//        if (!StringUtils.isEmpty(teacherQueryDto.getLevel())) {
//            wrapper.eq("level", teacherQueryDto.getLevel());
//        }
//        if (!StringUtils.isEmpty(teacherQueryDto.getBegin())) {
//            wrapper.ge("gmt_create", teacherQueryDto.getBegin());
//        }
//        if (!StringUtils.isEmpty(teacherQueryDto.getEnd())) {
//            wrapper.le("gmt_create", teacherQueryDto.getEnd());
//        }
//        wrapper.orderByDesc("gmt_create");
//        Page<EduTeacher> page = new Page<>(current, limit);
//        teacherService.page(page, wrapper);
//        List<EduTeacher> records = page.getRecords();
//        return Result.success().data("pageInfo", page);
//    }
//
//

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

//    /**
//     * @param teacherDto
//     * @return java.lang.Object
//     * @description 修改头像
//     * @author 橘白
//     * @date 2022/1/17 20:34
//     */

//    @PostMapping("/updateAvatar")
//    public Object updateAvatar(@Validated({TeacherDto.updateAvater.class}) @RequestBody TeacherDto teacherDto) throws Exception {
//        userService.updateAvatar(userDto);
//        return Result.success();
//    }


//    @ApiOperation(value = "根据ID查询讲师")
//    @GetMapping("{id}")
//    public Result getById(
//            @ApiParam(name = "id", value = "讲师ID", required = true)
//            @PathVariable String id) {
//        EduTeacher teacher = teacherService.getById(id);
//        return Result.success().data("item", teacher);
//
//    }
//
//    @ApiOperation(value = "修改讲师")
//    @PutMapping("updateTeacher")
//    public Result updateTeacher(
//            @ApiParam(name = "teacher", value = "讲师")
//            @RequestBody EduTeacher teacher) {
//        boolean flag = teacherService.updateById(teacher);
//        if (flag) {
//            return Result.success();
//        } else {
//            return Result.fail();
//        }
//    }

}

