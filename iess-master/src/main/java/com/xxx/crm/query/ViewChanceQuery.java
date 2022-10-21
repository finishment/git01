package com.xxx.crm.query;

import com.xxx.crm.base.BaseQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewChanceQuery extends BaseQuery {

    private String createMan;

    private Integer assignId;

    private String interviewerName;

}
