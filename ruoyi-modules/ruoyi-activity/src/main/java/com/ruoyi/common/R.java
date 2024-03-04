package com.ruoyi.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class R {
    private Integer code;
    private String msg;
    private Object data;

    public static R success(Object data){
        return new R(200,"操作成功",data);
    }
    public static R success(){
        return new R(200,"操作成功",null);
    }
    public static R fail(String msg){
        return new R(500,msg,null);
    }
}
