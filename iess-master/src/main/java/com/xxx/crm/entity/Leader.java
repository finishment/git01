package com.xxx.crm.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Leader {
    private Integer num;
    private Integer noteId;
    private String title;

}
