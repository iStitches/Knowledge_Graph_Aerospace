package com.space.demo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.bcel.Const;

import javax.xml.transform.Result;
import java.io.Serializable;

/**
 * 统一结果集封装
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultObj implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public static ResultObj success(Integer code,String msg,Object data){
        return new ResultObj(code,msg,data);
    }
    public static ResultObj success(String msg,Object data){
        return success(CommonEnum.SUCCESS_CODE.getResultCode(),msg,data);
    }
    public static ResultObj success(Object data){
        return success(CommonEnum.SUCCESS_CODE.getResultCode(),"响应成功！",data);
    }

    public static ResultObj error(Integer code,String msg,Object data){
        return new ResultObj(code,msg,data);
    }
    public static ResultObj error(Integer code,String msg){
        return new ResultObj(code,msg,null);
    }
    public static ResultObj error(){
        return new ResultObj(CommonEnum.ERROR_CODE.getResultCode(),"响应失败！",null);
    }
}
