package com.xxx.crm.base;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseQuery {

    private Integer page=1;
    private Integer limit=10;
}
