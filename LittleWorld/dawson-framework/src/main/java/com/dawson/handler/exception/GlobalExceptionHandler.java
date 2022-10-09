package com.dawson.handler.exception;


import com.dawson.domain.ResponseResult;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//这个注解可以返回json格式的数据，并且同时，如果controller出现异常，都回到这里来进行处理
@Slf4j//打印日志用的注解
public class GlobalExceptionHandler {


    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
       log.error("出现了异常!", e);
       return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult ExceptionHandler(Exception e){
        log.error("出现了异常!", e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
