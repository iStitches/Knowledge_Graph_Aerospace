package com.space.demo.exception;

import com.space.demo.common.ResultObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
       public ResultObj bizExceptionHandler(HttpServletRequest req,BizException e){
        logger.error("发生业务异常，原因是:{}",e.getErrorMsg());
        return ResultObj.error(e.getErrorCode(),e.getErrorMsg());
    }


}
