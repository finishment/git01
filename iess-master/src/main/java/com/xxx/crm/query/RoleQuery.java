package com.xxx.crm.query;

import com.xxx.crm.base.BaseQuery;
import lombok.Data;

import java.io.Serializable;


@Data
public class RoleQuery extends BaseQuery  implements Serializable {
    private String roleName;
}
