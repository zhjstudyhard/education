package com.education.mapper.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.dto.log.OperateLogDto;
import com.education.entity.log.OperateLogEntity;
import com.education.vo.OperateLogVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author 86181
 */
@Mapper
@Repository
public interface OperateLoggerMapper extends BaseMapper<OperateLogEntity> {

    List<OperateLogVo> queryPageLog(OperateLogDto operateLogDto);
}
