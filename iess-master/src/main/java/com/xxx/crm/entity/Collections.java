package com.xxx.crm.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Collections {
    private Integer id;
    private Integer user_id;
    private Integer type_id;
    private Integer note_id;
    private Integer company_id;
    private Date create_date;
    private int is_valid;

    //收藏文章概述
    private String description;

}
