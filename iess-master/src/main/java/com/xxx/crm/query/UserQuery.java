package com.xxx.crm.query;

import com.xxx.crm.base.BaseQuery;
import lombok.Data;

import java.io.Serializable;



@Data
public class UserQuery extends BaseQuery implements Serializable {

    private String userName; // 用户名
    private String email; // 电子邮箱
    private String phone; // 联系电话

}
