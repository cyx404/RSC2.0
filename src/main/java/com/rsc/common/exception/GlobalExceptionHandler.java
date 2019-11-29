package com.rsc.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ClassName:GlobalExceptionHandler
 * @Description:TODO 全局异常处理
 * @Author:陈一心
 * @Date:Create in  2019/11/21 22:39
 **/

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

        @ExceptionHandler
        @ResponseStatus
        public String runtimeExceptionHandler(Exception e){
            e.printStackTrace();
           return "------------------无法访问！------------------";
    }
}
