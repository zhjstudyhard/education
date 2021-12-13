package com.education.exceptionhandler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.education.common.Result;
import com.education.common.ResultCode;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.SignatureException;


/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-07-29-15-24
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result ExceptionHandler(Exception e){
        e.printStackTrace();
        return Result.fail().message("自定义操作失败");
    }


    @ResponseBody
    @ExceptionHandler(EducationException.class)
    public Result ExceptionHandler(EducationException e){
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }

    //注解检验异常处理
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result ExceptionHandler(MethodArgumentNotValidException e){
        e.printStackTrace();
        return Result.fail().code(ResultCode.FAILER_CODE.getCode()).message(e.getBindingResult().getFieldError().getDefaultMessage());
    }
    //校验token
    @ResponseBody
    @ExceptionHandler(value = TokenExpiredException.class)
    public Result ExceptionHandler(TokenExpiredException e){
        e.printStackTrace();
        return Result.fail().code(ResultCode.FAILER_CODE.getCode()).message(e.getMessage());
    }
}
