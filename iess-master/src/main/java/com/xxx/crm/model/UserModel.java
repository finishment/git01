package com.xxx.crm.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class UserModel {

    //private Integer userId;
    private String userName;
    private String trueName;

    private String userIdStr; // 加密后的用户id

}
