package com.space.demo.exception;

import lombok.Data;

/**
 * 自定义业务异常类
 */
@Data
public class BizException extends RuntimeException{
    private Integer errorCode;
    private String errorMsg;

    public BizException(BaseErrorInfoInterface errorInfoInterface){
        super(errorInfoInterface.getResultCode().toString());
        this.errorCode=errorInfoInterface.getResultCode();
        this.errorMsg=errorInfoInterface.getResultMsg();
    }

    public BizException(BaseErrorInfoInterface errorInfoInterface,Throwable cause){
        super(errorInfoInterface.getResultCode().toString(),cause);
        this.errorCode=errorInfoInterface.getResultCode();
        this.errorMsg=errorInfoInterface.getResultMsg();
    }

    public BizException(Integer errorCode,String errorMsg){
        super(errorCode.toString());
        this.errorCode=errorCode;
        this.errorMsg=errorMsg;
    }

    public BizException(Integer errorCode,String errorMsg,Throwable cause){
        super(errorCode.toString(),cause);
        this.errorCode=errorCode;
        this.errorMsg=errorMsg;
    }
}
