package com.education.service.serviceImpl.log;

import com.education.common.LogTypeNum;
import com.education.common.Result;
import com.education.constant.Constant;
import com.education.dto.base.ResponsePageDto;
import com.education.dto.log.OperateLogDto;
import com.education.mapper.log.OperateLoggerMapper;
import com.education.service.log.OperateLogService;
import com.education.vo.CourseVO;
import com.education.vo.OperateLogVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-02-05-16-01
 */
@Service
public class OperateLogServiceImpl implements OperateLogService {
    @Autowired
    private OperateLoggerMapper operateLoggerMapper;

    @Override
    public Result queryPageLog(OperateLogDto operateLogDto) {
        PageHelper.startPage(operateLogDto.getCurrentPage(), operateLogDto.getPageSize());
        List<OperateLogVo> logs = operateLoggerMapper.queryPageLog(operateLogDto);
        for (OperateLogVo operateLogVo : logs) {
            if (Constant.NUMBER_ZERO.equals(operateLogVo.getResult())){
                operateLogVo.setResultName("失败");
            }else if (Constant.NUMBER_ONE.equals(operateLogVo.getResult())){
                operateLogVo.setResultName("成功");
            }
            operateLogVo.setLogTypeName(LogTypeNum.getLogTypeByKey(operateLogVo.getLogType()).getLogDesc());
        }
        PageInfo<OperateLogVo> operateLogVoPageInfo = new PageInfo<>(logs);
        ResponsePageDto<OperateLogVo> articleVoResponsePageDto = new ResponsePageDto<>(operateLogVoPageInfo.getList(), operateLogVoPageInfo.getTotal(), operateLogVoPageInfo.getPageSize(), operateLogVoPageInfo.getPageNum());
        return Result.success().data("data",articleVoResponsePageDto);
    }

    @Override
    public Result queryLogType(OperateLogDto operateLogDto) {
        HashMap<Integer, String> map = new HashMap<>();
        for (LogTypeNum logTypeNum:LogTypeNum.values()){
            if (operateLogDto.getIsLogin().equals(Constant.NUMBER_ONE) && (logTypeNum.getLogType().equals(8) || logTypeNum.getLogType().equals(9))){
                map.put(logTypeNum.getLogType(),logTypeNum.getLogDesc());
            }else if(operateLogDto.getIsLogin().equals(Constant.NUMBER_ZERO) && !logTypeNum.getLogType().equals(8) && !logTypeNum.getLogType().equals(9)){
                map.put(logTypeNum.getLogType(),logTypeNum.getLogDesc());
            }
        }
        return Result.success().data("data",map);
    }
}
