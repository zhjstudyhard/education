package com.education.aop;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.common.LogTypeNum;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.constant.Constant;
import com.education.dto.system.UserDto;
import com.education.entity.log.OperateLogEntity;
import com.education.entity.system.UserEntity;
import com.education.mapper.log.OperateLoggerMapper;
import com.education.mapper.system.UserMapper;
import com.education.util.EntityUtil;
import com.education.util.ShiroEntityUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2022-02-04-17-44
 */
@Aspect  //定义切面
@Component
public class LogAop {
    @Autowired
    private OperateLoggerMapper operateLoggerMapper;

    @Autowired
    private UserMapper userMapper;

    //定义切点
    @Pointcut("@annotation(com.education.aop.LogRetention)")
    public void operateLog() {

    }

    //定义环绕通知
    @Around("operateLog()")
    public Result saveLog(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean flag = false;
        OperateLogEntity operateLogEntity = new OperateLogEntity();
        //设置当前操作的用户信息
        //获取注解数据
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method != null) {
            LogRetention annotation = method.getAnnotation(LogRetention.class);
            if (annotation != null) {
                //设置注解属性值
                operateLogEntity.setLogType(annotation.logType().getLogType());
                operateLogEntity.setModel(annotation.model());

                if (LogTypeNum.LOGIN.getLogType().equals(annotation.logType().getLogType())) {
                    flag = true;
                } else {
                    //设置操作人和操作人姓名
                    UserEntity userEntity = ShiroEntityUtil.getShiroEntity();
                    operateLogEntity.setUserId(userEntity.getId());
                    operateLogEntity.setUserName(userEntity.getUsername());
                    //设置基础属性
                    EntityUtil.addCreateInfo(operateLogEntity);
                }
            }
        }

        long startTime = System.currentTimeMillis();
        Result result = (Result) joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        //接口调用时间
        long resultTime = endTime - startTime;
        //设置接口调用时间
        operateLogEntity.setResultTime(resultTime);
        //设置接口调用是否成功
        if (ResultCode.SUCCESS_CODE.getCode().equals(result.getCode())) {
            operateLogEntity.setResult(1);
        } else {
            operateLogEntity.setResult(0);
        }
        //获取请求属性
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        operateLogEntity.setUrl(request.getRequestURL().toString());
        operateLogEntity.setMethod(request.getMethod());

        //获取相关类信息
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        operateLogEntity.setClassName(declaringTypeName);
        //方法名
        String methodName = joinPoint.getSignature().getName();
        operateLogEntity.setMethodName(methodName);
        //获取请求参数
        Object[] args = joinPoint.getArgs();
        List<Object> argList = new ArrayList<>();
        try {
            // 构造参数组集合
            for (Object arg : args) {
                // request/response无法使用toJSON
                if (arg instanceof HttpServletRequest) {
                    argList.add("request");
                } else if (arg instanceof HttpServletResponse) {
                    argList.add("response");
                } else {
                    argList.add(arg);
                    if (flag) {
                        UserDto userDto = (UserDto) arg;
                        operateLogEntity.setUserName(userDto.getUsername());

                        //查询用户信息
                        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
                        userEntityQueryWrapper.eq("is_deleted", Constant.ISDELETED_FALSE)
                                .eq("username", userDto.getUsername());

                        UserEntity userEntity = userMapper.selectOne(userEntityQueryWrapper);
                        operateLogEntity.setUserId(userEntity.getId());
                        //设置基础属性
                        EntityUtil.addCreateInfo(operateLogEntity);
                    }
                }
            }

        } catch (Exception e) {

        }
        operateLogEntity.setParams(JSONObject.toJSONString(argList));
        //添加操作日志
        operateLoggerMapper.insert(operateLogEntity);

        return result;
    }

}
