package com.education.service.serviceImpl.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.constant.Constant;
import com.education.dto.base.ResponsePageDto;
import com.education.dto.system.DictionaryDto;
import com.education.entity.system.DictionaryEntity;
import com.education.exceptionhandler.EducationException;
import com.education.mapper.system.DictionaryMapper;
import com.education.service.system.DictionaryService;
import com.education.util.EntityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-22-13-55
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public Result queryDictionaryAllPage(DictionaryDto dictionaryDto) {
        //使用pageHelper分页
        PageHelper.startPage(dictionaryDto.getCurrentPage(), dictionaryDto.getPageSize());
        PageInfo pageInfo = new PageInfo<>(dictionaryMapper.queryDictionaryAllPage(dictionaryDto));
        ResponsePageDto responsePageDto = new ResponsePageDto(pageInfo.getList(), pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getPageNum());
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", responsePageDto);
        return Result.success().data(map);
    }

    @Override
    public void deleteDictionary(DictionaryDto dictionaryDto) {
      if (StringUtils.isBlank(dictionaryDto.getId())){
          throw new EducationException(ResultCode.FAILER_CODE.getCode(),"主键不能为空");
      }
        DictionaryEntity dictionaryEntity = dictionaryMapper.selectById(dictionaryDto.getId());
      if (dictionaryEntity == null) {
          throw new EducationException(ResultCode.FAILER_CODE.getCode(),"数据不存在");
      }
      dictionaryEntity.setIsDeleted(1);
        //更新数据人信息
      EntityUtil.addModifyInfo(dictionaryEntity);
      dictionaryMapper.updateById(dictionaryEntity);
    }

    @Override
    public void editDictionary(DictionaryDto dictionaryDto) {
        if (StringUtils.isBlank(dictionaryDto.getId())){
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"主键不能为空");
        }
        //校验字典编码是否重复
        QueryWrapper<DictionaryEntity> dictionaryEntityQueryWrapper = new QueryWrapper<>();
        dictionaryEntityQueryWrapper.eq("is_deleted",Constant.ISDELETED_FALSE)
                .eq("dictionary_code",dictionaryDto.getDictionaryCode());
        DictionaryEntity dictionaryEntity = dictionaryMapper.selectOne(dictionaryEntityQueryWrapper);
        if (dictionaryEntity != null) {
            if (!dictionaryEntity.getId().equals(dictionaryDto.getId())){
                throw new EducationException(ResultCode.FAILER_CODE.getCode(),"字典编码重复");
            }
        }
        DictionaryEntity dictionaryEntityLocal = dictionaryMapper.selectById(dictionaryDto.getId());
        //更新字典
        dictionaryEntityLocal.setDictionaryCode(dictionaryDto.getDictionaryCode());
        dictionaryEntityLocal.setDictionaryType(dictionaryDto.getDictionaryType());
        dictionaryEntityLocal.setDictionaryValue(dictionaryDto.getDictionaryValue());
        //更新数据人信息
        EntityUtil.addModifyInfo(dictionaryEntityLocal);
        dictionaryMapper.updateById(dictionaryEntityLocal);
    }

    @Override
    public void addDictionary(DictionaryDto dictionaryDto) {
        //校验字典编码是否重复
        QueryWrapper<DictionaryEntity> dictionaryEntityQueryWrapper = new QueryWrapper<>();
        dictionaryEntityQueryWrapper.eq("is_deleted",Constant.ISDELETED_FALSE)
                .eq("dictionary_code",dictionaryDto.getDictionaryCode());
        DictionaryEntity dictionaryEntityLocal = dictionaryMapper.selectOne(dictionaryEntityQueryWrapper);
        if (dictionaryEntityLocal != null) {
            throw new EducationException(ResultCode.FAILER_CODE.getCode(),"字典编码重复");
        }
        //copy数据
        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        BeanUtils.copyProperties(dictionaryDto, dictionaryEntity);
        //创建人信息
        EntityUtil.addCreateInfo(dictionaryEntity);
        dictionaryMapper.insert(dictionaryEntity);
    }

    @Override
    public Result queryDictionaryByType(DictionaryDto dictionaryDto) {
        QueryWrapper<DictionaryEntity> dictionaryEntityQueryWrapper = new QueryWrapper<>();
        dictionaryEntityQueryWrapper.eq("is_deleted",Constant.ISDELETED_FALSE)
                .eq("dictionary_type",dictionaryDto.getDictionaryType());
        //查询字典数据
        List<DictionaryEntity> dictionaryEntityList = dictionaryMapper.selectList(dictionaryEntityQueryWrapper);
        return Result.success().data("data",dictionaryEntityList);
    }
}
