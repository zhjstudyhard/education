package com.education.service.serviceImpl.teacher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.common.Result;
import com.education.config.GlobalData;
import com.education.constant.Constant;
import com.education.dto.base.ResponsePageDto;
import com.education.dto.teacher.TeacherDto;
import com.education.entity.course.CourseEntity;
import com.education.entity.teacher.TeacherEntity;
import com.education.entity.uploadfile.UploadFileEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.course.CourseMapper;
import com.education.mapper.teacher.TeacherMapper;
import com.education.mapper.upload.UploadFileMapper;
import com.education.service.teacher.TeacherService;
import com.education.util.EntityUtil;
import com.education.vo.TeacherVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author jubai
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, TeacherEntity> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private UploadFileMapper uploadFileMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public void addTeacher(TeacherDto teacherDto) {
        //属性赋值
        TeacherEntity teacherEntity = new TeacherEntity();
        BeanUtils.copyProperties(teacherDto, teacherEntity);
        EntityUtil.addCreateInfo(teacherEntity);
        //添加数据
        teacherMapper.insert(teacherEntity);

        //修改关联图片表
        updateFile(teacherDto,teacherEntity);
    }

    @Override
    public Result pageListTeacher(TeacherDto teacherDto) {
        PageHelper.startPage(teacherDto.getCurrentPage(),teacherDto.getPageSize());
        List<TeacherVo> list =  teacherMapper.pageListTeacher(teacherDto);
        //属性赋值
        for (TeacherVo teacherVo : list) {
            if (StringUtils.isNotBlank(teacherVo.getLevel())){
                teacherVo.setLevelName(GlobalData.dictMap.get(teacherVo.getLevel()).getDictionaryValue());
            }
        }
        PageInfo<TeacherVo> teacherVoPageInfo = new PageInfo<>(list);
        ResponsePageDto<TeacherVo> teacherVoResponsePageDto = new ResponsePageDto<>(teacherVoPageInfo.getList(), teacherVoPageInfo.getTotal(), teacherVoPageInfo.getPageSize(), teacherVoPageInfo.getPageNum());
        return Result.success().data("data",teacherVoResponsePageDto);
    }

    @Override
    public Result getTeacherById(TeacherDto teacherDto) {
        if (StringUtils.isBlank(teacherDto.getId())){
            throw new EducationException(Result.fail().getCode(),"主键不能为空");
        }
        TeacherEntity teacherEntity = teacherMapper.selectById(teacherDto.getId());
        if (teacherEntity == null){
            throw new EducationException(Result.fail().getCode(),"数据不存在");
        }
        //属性赋值
        TeacherVo teacherVo = new TeacherVo();
        BeanUtils.copyProperties(teacherEntity, teacherVo);
        return Result.success().data("data",teacherVo);
    }

    @Override
    public void deleteTeacherById(TeacherDto teacherDto) {
        //校验数据
        if (StringUtils.isBlank(teacherDto.getId())){
            throw new EducationException(Result.fail().getCode(),"主键不能为空");
        }
        TeacherEntity teacherEntity = teacherMapper.selectById(teacherDto.getId());
        if (null == teacherEntity){
            throw new EducationException(Result.fail().getCode(),"数据不存在");
        }
        teacherEntity.setIsDeleted(Constant.ISDELETED_TRUE);
        EntityUtil.addModifyInfo(teacherEntity);
        //更新数据
        teacherMapper.updateById(teacherEntity);
        //删除图片
        deleteFile(teacherEntity);

    }

    @Override
    public void updateTeacher(TeacherDto teacherDto) {
        if (StringUtils.isBlank(teacherDto.getId())){
            throw new EducationException(Result.fail().getCode(),"主键不能为空");
        }
        TeacherEntity teacherEntity = teacherMapper.selectById(teacherDto.getId());
        if (null == teacherEntity){
            throw new EducationException(Result.fail().getCode(),"数据不存在");
        }
        deleteFile(teacherEntity);

        BeanUtils.copyProperties(teacherDto,teacherEntity);
        //更新讲师数据
        teacherMapper.updateById(teacherEntity);
        //修改关联图片表
        updateFile(teacherDto,teacherEntity);

    }

    public void deleteFile(TeacherEntity teacherEntity){
        //更新图片关联表数据
        //删除图片
        if (StringUtils.isNotBlank(teacherEntity.getAvatar())){
            QueryWrapper<UploadFileEntity> uploadFileEntityQueryWrapper = new QueryWrapper<>();
            uploadFileEntityQueryWrapper.eq("is_deleted",Constant.ISDELETED_FALSE)
                    .eq("relative_id",teacherEntity.getId());

            UploadFileEntity uploadFileEntity = uploadFileMapper.selectOne(uploadFileEntityQueryWrapper);
            if (null != uploadFileEntity){
                uploadFileEntity.setIsDeleted(Constant.ISDELETED_TRUE);
                EntityUtil.addModifyInfo(uploadFileEntity);

                uploadFileMapper.updateById(uploadFileEntity);
            }
        }
    }
    //更改关联数据
    public void updateFile(TeacherDto teacherDto,TeacherEntity teacherEntity){
        //修改关联图片表
        if (StringUtils.isNotBlank(teacherDto.getFileId())){
            //查询图片信息
            UploadFileEntity uploadFileEntity = uploadFileMapper.selectById(teacherDto.getFileId());
            if (uploadFileEntity == null) {
                throw new EducationException(Result.fail().getCode(),"图片不存在");
            }
            uploadFileEntity.setRelativeId(teacherEntity.getId());
            EntityUtil.addModifyInfo(uploadFileEntity);

            //修改图片关联Id
            uploadFileMapper.updateById(uploadFileEntity);
        }

    }

    @Override
    public Result getTeacherInfoIndexById(TeacherDto teacherDto) {
        TeacherEntity teacherEntity = teacherMapper.selectById(teacherDto.getId());
        if (teacherEntity == null){
            throw new EducationException(Result.fail().getCode(),"数据不存在");
        }
        //属性赋值
        TeacherVo teacherVo = new TeacherVo();
        BeanUtils.copyProperties(teacherEntity,teacherVo);

        if (StringUtils.isNotBlank(teacherVo.getLevel())){
            teacherVo.setLevelName(GlobalData.dictMap.get(teacherVo.getLevel()).getDictionaryValue());
        }
        //查询当前讲师的课程
        QueryWrapper<CourseEntity> courseEntityQueryWrapper = new QueryWrapper<>();
        courseEntityQueryWrapper.eq("is_deleted",Constant.ISDELETED_FALSE)
                .eq("teacher_id",teacherDto.getId());
        courseEntityQueryWrapper.orderByAsc("gmt_create");

        List<CourseEntity> courseEntities = courseMapper.selectList(courseEntityQueryWrapper);
        teacherVo.setCourseList(courseEntities);

        return Result.success().data("data",teacherVo);
    }
}
