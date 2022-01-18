package com.education.service.serviceImpl.subject;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.common.Result;
import com.education.constant.Constant;
import com.education.entity.excel.SubjectData;
import com.education.entity.subject.SubjectEntity;
import com.education.entity.system.UserEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.subject.SubjectMapper;
import com.education.service.subject.SubjectService;
import com.education.util.EntityUtil;
import com.education.util.ShiroEntityUtil;
import com.education.vo.SubjectVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author jubai
 * @since 2021-08-09
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, SubjectEntity> implements SubjectService {

    //    @Autowired
//    private EduSubjectService eduSubjectService;
//
    @Autowired
    private SubjectMapper subjectMapper;

    /**
     * 添加课程科目
     *
     * @param file
     */
    @Override
    public void addSubject(MultipartFile file) throws Exception {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class, new AnalysisEventListener<SubjectData>() {
                @Override
                public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
                    SubjectEntity subjectEntity = exitsOneSubjectName(subjectData);
                    if (subjectEntity == null) {
                        subjectEntity = new SubjectEntity();
                        subjectEntity.setTitle(subjectData.getOneSubjectName());
                        subjectEntity.setParentId("0");
                        subjectEntity.setSort(0);
                        EntityUtil.addCreateInfo(subjectEntity);

                        subjectMapper.insert(subjectEntity);
                    }
                    SubjectEntity subjectEntityLocal = exitsTwoSubjectName(subjectData, subjectEntity.getId());
                    if (subjectEntityLocal == null) {
                        subjectEntityLocal = new SubjectEntity();
                        subjectEntityLocal.setTitle(subjectData.getTwoSubjectName());
                        subjectEntityLocal.setParentId(subjectEntity.getId());
                        subjectEntityLocal.setSort(0);
                        EntityUtil.addCreateInfo(subjectEntityLocal);

                        subjectMapper.insert(subjectEntityLocal);
                    }
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }

                /**
                 * 判断二级标题是否重复
                 * @param subjectData
                 */
                private SubjectEntity exitsTwoSubjectName(SubjectData subjectData, String pid) {
                    QueryWrapper<SubjectEntity> eduSubjectQueryWrapper = new QueryWrapper<>();
                    eduSubjectQueryWrapper.eq("title", subjectData.getTwoSubjectName())
                            .eq("parent_id", pid)
                            .eq("is_deleted", Constant.ISDELETED_FALSE);
                    SubjectEntity eduSubject = subjectMapper.selectOne(eduSubjectQueryWrapper);
                    return eduSubject;
                }

                /**
                 * 判断一级标题是否重复
                 * @param subjectData
                 */
                private SubjectEntity exitsOneSubjectName(SubjectData subjectData) {
                    QueryWrapper<SubjectEntity> eduSubjectQueryWrapper = new QueryWrapper<>();
                    eduSubjectQueryWrapper.eq("title", subjectData.getOneSubjectName())
                            .eq("parent_id", "0")
                            .eq("is_deleted", Constant.ISDELETED_FALSE);
                    SubjectEntity eduSubject = subjectMapper.selectOne(eduSubjectQueryWrapper);
                    return eduSubject;
                }
            }).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询所有分类(树形)
     *
     * @return
     */
    @Override
    public Result getAllSubject() {

        ArrayList<SubjectVo> allSubjectList = new ArrayList<>();
        //查询所有一级分类
        QueryWrapper<SubjectEntity> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0")
                .eq("is_deleted", Constant.ISDELETED_FALSE);
        List<SubjectEntity> oneSubjects = subjectMapper.selectList(oneWrapper);
        //查询所有二级分类
        QueryWrapper<SubjectEntity> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", "0")
                .eq("is_deleted", Constant.ISDELETED_FALSE);
        List<SubjectEntity> twoSubjects = subjectMapper.selectList(twoWrapper);

        //处理数据
        for (SubjectEntity oneEduSubject : oneSubjects) {
            SubjectVo subjectVo = new SubjectVo();
            BeanUtils.copyProperties(oneEduSubject, subjectVo);
            allSubjectList.add(subjectVo);

            for (SubjectEntity twoEduSubject : twoSubjects) {
                if (subjectVo.getId().equals(twoEduSubject.getParentId())) {
                    SubjectEntity twoSubject = new SubjectEntity();
                    BeanUtils.copyProperties(twoEduSubject, twoSubject);
                    subjectVo.getChildren().add(twoSubject);
                }
            }
        }
        return Result.success().data("data", allSubjectList);
    }

    @Override
    public void updateSubject(SubjectEntity subjectEntity) {
        if (StringUtils.isBlank(subjectEntity.getId())) {
            throw new EducationException(Result.fail().getCode(), "主键不能为空");
        }
        SubjectEntity subjectEntityLocal = subjectMapper.selectById(subjectEntity.getId());
        if (subjectEntityLocal == null) {
            throw new EducationException(Result.fail().getCode(), "数据不存在");
        }
        if (subjectEntityLocal.getParentId().equals("0")) {
            QueryWrapper<SubjectEntity> eduSubjectQueryWrapper = new QueryWrapper<>();
            eduSubjectQueryWrapper.eq("title", subjectEntity.getTitle())
                    .eq("parent_id", "0")
                    .eq("is_deleted", Constant.ISDELETED_FALSE);
            SubjectEntity eduSubject = subjectMapper.selectOne(eduSubjectQueryWrapper);
            if (eduSubject != null && !eduSubject.getId().equals(subjectEntityLocal.getId())) {
                throw new EducationException(Result.fail().getCode(), "课程名称重复");
            }
        } else {
            QueryWrapper<SubjectEntity> eduSubjectQueryWrapper = new QueryWrapper<>();
            eduSubjectQueryWrapper.eq("title", subjectEntity.getTitle())
                    .eq("parent_id", subjectEntity.getParentId())
                    .eq("is_deleted", Constant.ISDELETED_FALSE);
            SubjectEntity eduSubject = subjectMapper.selectOne(eduSubjectQueryWrapper);
            if (eduSubject != null && !eduSubject.getId().equals(subjectEntityLocal.getId())) {
                throw new EducationException(Result.fail().getCode(), "课程名称重复");
            }
        }
        subjectEntityLocal.setTitle(subjectEntity.getTitle());
        EntityUtil.addModifyInfo(subjectEntityLocal);

        subjectMapper.updateById(subjectEntityLocal);

    }

    @Override
    public void deleteSubjectById(SubjectEntity subjectEntity) {
        if(StringUtils.isBlank(subjectEntity.getId())){
            throw new EducationException(Result.fail().getCode(),"主键不能为空");
        }
        SubjectEntity subjectEntityLocal = subjectMapper.selectById(subjectEntity.getId());
        if (subjectEntityLocal == null) {
            throw new EducationException(Result.fail().getCode(), "数据不存在");
        }
        //删除id集合
        ArrayList<String> ids = new ArrayList<>();
        ids.add(subjectEntityLocal.getId());
        if (subjectEntityLocal.getParentId().equals("0")){
            QueryWrapper<SubjectEntity> subjectEntityQueryWrapper = new QueryWrapper<>();
            subjectEntityQueryWrapper.eq("is_deleted",Constant.ISDELETED_FALSE)
                    .eq("parent_id",subjectEntityLocal.getId());

            List<SubjectEntity> subjectEntities = subjectMapper.selectList(subjectEntityQueryWrapper);
            List<String> idList = subjectEntities.stream().map(o -> o.getId()).collect(Collectors.toList());
            ids.addAll(idList);
        }
        //批量更新
        subjectMapper.updateBatchIds(ids);
    }
}
