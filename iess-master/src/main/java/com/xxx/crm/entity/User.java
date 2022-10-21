package com.xxx.crm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;



@Data
@Accessors(chain = true)
public class User implements Serializable {
    private Integer id;

    private String userName;

    private String userPwd;

    private String trueName;
    private String head;
    private String email;

    private String phone;

    private Integer isValid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date updateDate;

    // 用户对应的角色ID
    private String roleIds;

}
