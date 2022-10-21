package com.xxx.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserRole implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Date createDate;

    private Date updateDate;
}
