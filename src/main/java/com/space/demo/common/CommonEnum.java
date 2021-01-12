package com.space.demo.common;

import com.space.demo.exception.BaseErrorInfoInterface;

/**
 * 异常枚举
 */

public enum CommonEnum implements BaseErrorInfoInterface {
    SUCCESS_CODE(200,"响应成功"),
    ERROR_CODE(405,"响应失败"),
    BODY_NOT_MATCH(400,"请求数据格式不符"),
    NOT_FOUND(404,"未找到资源"),
    INTERNAL_SERVER_ERROR(500,"服务器内部错误"),
    SERVER_BUSY(503,"服务器正忙，请稍后再试");


    private Integer code;
    private String msg;
    CommonEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
    @Override
    public Integer getResultCode() {
        return code;
    }

    @Override
    public String getResultMsg() {
        return msg;
    }
}
