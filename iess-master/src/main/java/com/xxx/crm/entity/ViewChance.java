package com.xxx.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ViewChance {
    private Integer id;

    private String interviewerName;//面试者

    private String interviewer;//面试官

    private String experience;//面试经验

    private String createMan;

    private String assignId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateDate;

    private Integer isValid;//逻辑删除字段



}