package com.xxx.crm.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义未登录异常
 */
@Setter
@Getter
public class NoLoginException extends RuntimeException {
    private Integer code=300;
    private String msg="用户未登录!";


    public NoLoginException() {
        super("用户未登录!");
    }

    public NoLoginException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public NoLoginException(Integer code) {
        super("用户未登录!");
        this.code = code;
    }

    public NoLoginException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
