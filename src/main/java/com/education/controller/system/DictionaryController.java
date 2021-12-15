package com.education.controller.system;

import com.education.common.Result;
import com.education.dto.system.DictionaryDto;
import com.education.entity.system.DictionaryEntity;
import com.education.service.system.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-22-13-53
 */
@RestController
@RequestMapping("/api/dictionary")
@CrossOrigin
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    /**
     * @param dictionaryDto
     * @return java.lang.Object
     * @description 数据字典分页查询
     * @author 橘白
     * @date 2021/11/22 20:55
     */

    @PostMapping("/queryDictionaryAllPage")
    public Object queryDictionaryAllPage(@RequestBody DictionaryDto dictionaryDto) {
        return dictionaryService.queryDictionaryAllPage(dictionaryDto);
    }

    /**
     * @param dictionaryDto
     * @return java.lang.Object
     * @description 删除数据字典
     * @author 橘白
     * @date 2021/11/22 21:29
     */

    @PostMapping("/deletedDictionary")
    public Object deleteDictionary(@RequestBody DictionaryDto dictionaryDto) {
        dictionaryService.deleteDictionary(dictionaryDto);
        return Result.success();
    }

    /**
     * @param dictionaryDto
     * @return java.lang.Object
     * @description 更新字典
     * @author 橘白
     * @date 2021/11/22 21:47
     */

    @PostMapping("/editDictionary")
    public Object editDictionary(@Validated @RequestBody DictionaryDto dictionaryDto) {
        dictionaryService.editDictionary(dictionaryDto);
        return Result.success();
    }

    /**
     * @param dictionaryDto
     * @return java.lang.Object
     * @description 添加字典
     * @author 橘白
     * @date 2021/11/23 16:21
     */

    @PostMapping("/addDictionary")
    public Object addDictionary(@Validated @RequestBody DictionaryDto dictionaryDto) {
        dictionaryService.addDictionary(dictionaryDto);
        return Result.success();
    }


    /**
     * @param dictionaryDto
     * @return java.lang.Object
     * @description 根据字典类型查询
     * @author 橘白
     * @date 2021/12/15 14:21
     */

    @PostMapping("/queryDictionaryByType")
    public Object queryDictionaryByType(@RequestBody DictionaryDto dictionaryDto) {
        return dictionaryService.queryDictionaryByType(dictionaryDto);
    }

}
