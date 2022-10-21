package com.xxx.crm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class Announcement{
    private Integer id;

    private String announcement;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date createDate;


}
