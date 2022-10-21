package com.xxx.crm.query;

import com.xxx.crm.base.BaseQuery;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏记录查询类
 */

@Data
@Setter
@Getter
public class CollectionQuery extends BaseQuery {

    //分页参数


    //条件查询
    private Integer userId;//用户id
    //private Integer type_id;//收藏类型
    private Integer noteId;
    private String title;//文章概述
    private Date createDate;

}
