package com.education.service.serviceImpl.teacher;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.common.Result;
import com.education.dto.teacher.TeacherDto;
import com.education.entity.teacher.TeacherEntity;
import com.education.entity.uploadfile.UploadFileEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.teacher.TeacherMapper;
import com.education.mapper.upload.UploadFileMapper;
import com.education.service.teacher.TeacherService;
import com.education.util.EntityUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author jubai
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, TeacherEntity> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private UploadFileMapper uploadFileMapper;

    @Override
    public void addTeacher(TeacherDto teacherDto) {
        //属性赋值
        TeacherEntity teacherEntity = new TeacherEntity();
        BeanUtils.copyProperties(teacherDto, teacherEntity);
        EntityUtil.addCreateInfo(teacherEntity);
        //添加数据
        teacherMapper.insert(teacherEntity);

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
}
