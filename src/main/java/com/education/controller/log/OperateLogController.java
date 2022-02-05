package com.education.controller.log;

import com.education.common.Result;
import com.education.dto.log.OperateLogDto;
import com.education.service.log.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-02-05-15-57
 */
@RequestMapping("/api/operateLog")
@RestController
@CrossOrigin
public class OperateLogController {
    @Autowired
    private OperateLogService operateLogService;

    /**
     * @param operateLogDto
     * @return com.education.common.Result
     * @description 分页查询操作日志
     * @author 橘白
     * @date 2022/2/5 17:12
     */

    @PostMapping("/queryPageLog")
    public Result queryPageLog(@RequestBody OperateLogDto operateLogDto) {
        return operateLogService.queryPageLog(operateLogDto);
    }

    /**
     * @return com.education.common.Result
     * @description 查询所有操作类型
     * @author 橘白
     * @date 2022/2/5 17:13
     */

    @PostMapping("/queryLogType")
    public Result queryLogType(@RequestBody OperateLogDto operateLogDto) {
        return operateLogService.queryLogType(operateLogDto);
    }

}
