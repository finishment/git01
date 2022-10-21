package com.xxx.crm.base;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ResultInfo {

    private Integer code=200;
    private String msg="success";
    private Object result;

}
