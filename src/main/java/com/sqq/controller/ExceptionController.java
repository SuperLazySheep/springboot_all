package com.sqq.controller;

import com.sqq.model.bean.common.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(Exception.class)  // 拦截所有异常
    protected ResultData handleException(Exception e){
        return null;
    }
}
