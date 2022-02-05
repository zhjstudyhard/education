package com.education.service.log;

import com.education.common.Result;
import com.education.dto.log.OperateLogDto;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-02-05-16-00
 */
public interface OperateLogService {
    Result queryPageLog(OperateLogDto operateLogDto);

    Result queryLogType(OperateLogDto operateLogDto);
}
