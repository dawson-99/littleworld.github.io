package com.dawson.exception;

import com.dawson.enums.AppHttpCodeEnum;


/**
 * 把RuntimeException给重写了，统一下格式.以后想抛出异常，就用systemexception写
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
