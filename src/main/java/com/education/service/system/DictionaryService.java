package com.education.service.system;

import com.education.common.Result;
import com.education.dto.base.ResponsePageDto;
import com.education.dto.system.DictionaryDto;
import com.education.entity.system.DictionaryEntity;

import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-22-13-55
 */
public interface DictionaryService {
    /**
     * @description 数据字典分页查询
     * @param dictionaryDto
     * @author 橘白
     * @date 2021/11/22 14:03
     */
    Result queryDictionaryAllPage(DictionaryDto dictionaryDto);

    void deleteDictionary(DictionaryDto dictionaryDto);

    void editDictionary(DictionaryDto dictionaryDto);

    void addDictionary(DictionaryDto dictionaryDto);

    Result queryDictionaryByType(DictionaryDto dictionaryDto);
}
