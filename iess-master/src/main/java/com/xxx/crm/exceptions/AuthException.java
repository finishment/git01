package com.xxx.crm.exceptions;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthException extends RuntimeException{

    private Integer code=400;
    private String msg="暂无权限!";


    public AuthException() {
        super("暂无权限!");
    }

    public AuthException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AuthException(Integer code) {
        super("暂无权限!");
        this.code = code;
    }

    public AuthException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
