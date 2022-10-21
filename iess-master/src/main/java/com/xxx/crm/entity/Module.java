package com.xxx.crm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;



@Data
public class Module implements Serializable {
    private Integer id;

    private String moduleName;

    private String moduleStyle;

    private String url;

    private Integer parentId;

    private String parentOptValue;

    private Integer grade;

    private String optValue;

    private Integer orders;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Byte isValid;

    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date updateDate;
}
