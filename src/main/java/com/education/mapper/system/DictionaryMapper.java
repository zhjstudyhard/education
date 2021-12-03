package com.education.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.dto.system.DictionaryDto;
import com.education.entity.system.DictionaryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-22-13-56
 */
@Mapper
@Repository
public interface DictionaryMapper extends BaseMapper<DictionaryEntity> {
    List<DictionaryEntity> queryDictionaryAllPage(DictionaryDto dictionaryDto);
}
